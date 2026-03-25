package com.posadskiy.currencyconverter.source;

import com.posadskiy.currencyconverter.Messages;
import com.posadskiy.currencyconverter.config.Config;
import com.posadskiy.currencyconverter.config.ConfigBuilder;
import com.posadskiy.currencyconverter.enums.Currency;
import com.posadskiy.currencyconverter.service.CurrencyConvertService;
import com.posadskiy.currencyconverter.exception.CurrencyConverterException;
import com.sun.net.httpserver.HttpServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CurrencyConvertServiceTest {

    private HttpServer server;
    private int port;

    private void startServer(Map<String, String> pathToBody, Map<String, Integer> pathToStatus) throws IOException {
        server = HttpServer.create(new InetSocketAddress("localhost", 0), 0);
        port = server.getAddress().getPort();

        for (Map.Entry<String, String> e : pathToBody.entrySet()) {
            String path = e.getKey();
            String body = e.getValue();
            int status = pathToStatus.getOrDefault(path, 200);
            server.createContext(path, exchange -> {
                byte[] bytes = body.getBytes();
                exchange.sendResponseHeaders(status, bytes.length);
                try (OutputStream os = exchange.getResponseBody()) {
                    os.write(bytes);
                }
            });
        }

        server.start();
    }

    @AfterEach
    void stopServer() {
        if (server != null) {
            server.stop(0);
        }
    }

    @Test
    void rate_whenSameCurrency_returnsOne() {
        Config config = new Config();
        CurrencyConvertService service = new CurrencyConvertService();
        Double rate = service.rate(config, Currency.USD, Currency.USD);
        assertThat(rate).isEqualTo(1.0);
    }

    @Test
    void rate_whenNoApiKeys_throwsTryLater() {
        Config config = new Config();
        CurrencyConvertService service = new CurrencyConvertService();

        CurrencyConverterException ex = assertThrows(
            CurrencyConverterException.class,
            () -> service.rate(config, Currency.USD, Currency.EUR)
        );

        assertThat(ex.getMessage()).isEqualTo(Messages.TRY_LATER);
    }

    @Test
    void rate_fallbackToNextProvider_whenFirstProviderFails() throws Exception {
        Map<String, String> bodies = new HashMap<>();
        bodies.put("/api/v7/convert", "bad");
        bodies.put("/currency_data/convert", "{\"success\":true,\"result\":2.0}");

        Map<String, Integer> statuses = new HashMap<>();
        statuses.put("/api/v7/convert", 200);
        statuses.put("/currency_data/convert", 200);

        startServer(bodies, statuses);

        Config config = new ConfigBuilder()
            .currencyConverterApiApiKey("dummy")
            .currencyLayerApiKey("dummy")
            .build();

        CurrencyConvertService service = new CurrencyConvertService("http://localhost:" + port);
        Double rate = service.rate(config, Currency.RUB, Currency.BYN);
        assertThat(rate).isEqualTo(2.0);
    }

    @Test
    void rate_whenAllProvidersFail_throwsTryLater() throws Exception {
        Map<String, String> bodies = new HashMap<>();
        bodies.put("/api/v7/convert", "bad");
        bodies.put("/currency_data/convert", "still-bad");

        Map<String, Integer> statuses = new HashMap<>();
        statuses.put("/api/v7/convert", 200);
        statuses.put("/currency_data/convert", 200);

        startServer(bodies, statuses);

        Config config = new ConfigBuilder()
            .currencyConverterApiApiKey("dummy")
            .currencyLayerApiKey("dummy")
            .build();

        CurrencyConvertService service = new CurrencyConvertService("http://localhost:" + port);
        CurrencyConverterException ex = assertThrows(
            CurrencyConverterException.class,
            () -> service.rate(config, Currency.RUB, Currency.BYN)
        );

        assertThat(ex.getMessage()).isEqualTo(Messages.TRY_LATER);
    }
}
