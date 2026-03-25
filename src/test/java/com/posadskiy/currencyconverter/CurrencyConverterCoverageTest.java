package com.posadskiy.currencyconverter;

import com.posadskiy.currencyconverter.config.Config;
import com.posadskiy.currencyconverter.config.ConfigBuilder;
import com.posadskiy.currencyconverter.enums.Currency;
import com.posadskiy.currencyconverter.exception.CurrencyConverterException;
import com.posadskiy.currencyconverter.service.CurrencyConvertService;
import com.sun.net.httpserver.HttpServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.net.InetSocketAddress;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CurrencyConverterCoverageTest {

    private HttpServer server;
    private int port;

    private void startCurrencyLayerStub() throws IOException {
        server = HttpServer.create(new InetSocketAddress("localhost", 0), 0);
        port = server.getAddress().getPort();

        server.createContext("/currency_data/convert", exchange -> {
            byte[] bytes = "{\"success\":true,\"result\":2.0}".getBytes();
            exchange.sendResponseHeaders(200, bytes.length);
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(bytes);
            }
        });

        server.start();
    }

    @AfterEach
    void stop() {
        if (server != null) {
            server.stop(0);
        }
    }

    @Test
    void callAllCurrencyConverterRateMethods() throws Exception {
        startCurrencyLayerStub();

        Config config = new ConfigBuilder()
            .currencyLayerApiKey("dummy")
            .build();

        CurrencyConvertService service = new CurrencyConvertService("http://localhost:" + port);
        CurrencyConverter converter = new CurrencyConverter(config, service);

        for (Method m : CurrencyConverter.class.getDeclaredMethods()) {
            if (!m.getName().startsWith("rate")) continue;

            Object result;
            if (m.getParameterCount() == 0) {
                result = m.invoke(converter);
            } else if (m.getParameterCount() == 1) {
                if (m.getParameterTypes()[0] != Currency.class) continue;
                result = m.invoke(converter, Currency.EUR);
            } else if (m.getParameterCount() == 2) {
                Class<?> p0 = m.getParameterTypes()[0];
                Class<?> p1 = m.getParameterTypes()[1];
                if (p0 == Currency.class && p1 == Currency.class) {
                    result = m.invoke(converter, Currency.USD, Currency.EUR);
                } else if (p0 == String.class && p1 == String.class) {
                    result = m.invoke(converter, "USD", "EUR");
                } else {
                    continue;
                }
            } else {
                continue;
            }

            assertThat(result).isInstanceOf(Double.class);
        }
    }

    @Test
    void rate_nullCurrency_throwsException() {
        Config config = new ConfigBuilder()
            .currencyLayerApiKey("dummy")
            .build();

        CurrencyConverter converter = new CurrencyConverter(config);

        assertThrows(CurrencyConverterException.class, () -> converter.rate((Currency) null, Currency.EUR));
        assertThrows(CurrencyConverterException.class, () -> converter.rate(Currency.USD, (Currency) null));
        assertThrows(CurrencyConverterException.class, () -> converter.rate((String) null, "EUR"));
        assertThrows(CurrencyConverterException.class, () -> converter.rate("USD", (String) null));
    }

    @Test
    void rate_nullConfig_throwsException() {
        assertThrows(CurrencyConverterException.class, () -> new CurrencyConverter(null));
    }

    @Test
    void rate_withCache_returnsCachedValue() throws Exception {
        startCurrencyLayerStub();

        Config config = new ConfigBuilder()
            .currencyLayerApiKey("dummy")
            .cacheTtlSeconds(60)
            .build();

        CurrencyConvertService service = new CurrencyConvertService("http://localhost:" + port);
        CurrencyConverter converter = new CurrencyConverter(config, service);

        Double first = converter.rate(Currency.USD, Currency.EUR);
        server.stop(0);
        server = null;

        Double second = converter.rate(Currency.USD, Currency.EUR);
        assertThat(second).isEqualTo(first);
    }
}
