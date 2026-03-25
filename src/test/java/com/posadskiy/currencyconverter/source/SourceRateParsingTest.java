package com.posadskiy.currencyconverter.source;

import com.posadskiy.currencyconverter.enums.Currency;
import com.posadskiy.currencyconverter.exception.CurrencyConverterException;
import com.sun.net.httpserver.HttpServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class SourceRateParsingTest {

    private HttpServer server;
    private int port;

    private void startServer(String path, int statusCode, String body) throws IOException {
        server = HttpServer.create(new InetSocketAddress("localhost", 0), 0);
        port = server.getAddress().getPort();
        server.createContext(path, exchange -> {
            byte[] bytes = body.getBytes();
            exchange.sendResponseHeaders(statusCode, bytes.length);
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(bytes);
            }
        });
        server.start();
    }

    private String localBase() {
        return "http://localhost:" + port;
    }

    @AfterEach
    void stopServer() {
        if (server != null) {
            server.stop(0);
        }
    }

    // --- CurrencyConverterApiSource ---

    @Test
    void currencyConverterApiSource_rate_success() throws Exception {
        startServer("/api/v7/convert", 200, "{\"RUB_BYN\":2.5}");

        CurrencyConverterApiSource src = new CurrencyConverterApiSource(localBase());
        Double rate = src.rate("dummy", Currency.RUB, Currency.BYN);
        assertThat(rate).isEqualTo(2.5);
    }

    @Test
    void currencyConverterApiSource_rate_failure() throws Exception {
        startServer("/api/v7/convert", 200, "bad");

        CurrencyConverterApiSource src = new CurrencyConverterApiSource(localBase());
        CurrencyConverterException ex = assertThrows(
            CurrencyConverterException.class,
            () -> src.rate("dummy", Currency.RUB, Currency.BYN)
        );
        assertThat(ex.getMessage()).contains(CurrencyConverterApiSource.SERVICE_NAME);
    }

    // --- CurrencyLayerSource ---

    @Test
    void currencyLayerSource_rate_success() throws Exception {
        startServer("/currency_data/convert", 200, "{\"success\":true,\"result\":2.0}");

        CurrencyLayerSource src = new CurrencyLayerSource(localBase());
        Double rate = src.rate("dummy", Currency.RUB, Currency.BYN);
        assertThat(rate).isEqualTo(2.0);
    }

    @Test
    void currencyLayerSource_rate_failure() throws Exception {
        startServer("/currency_data/convert", 200, "bad");

        CurrencyLayerSource src = new CurrencyLayerSource(localBase());
        CurrencyConverterException ex = assertThrows(
            CurrencyConverterException.class,
            () -> src.rate("dummy", Currency.RUB, Currency.BYN)
        );
        assertThat(ex.getMessage()).contains(CurrencyLayerSource.SERVICE_NAME);
    }

    @Test
    void currencyLayerSource_rate_failure_invalidNumber() throws Exception {
        startServer("/currency_data/convert", 200, "{\"success\":true,\"result\":\"abc\"}");

        CurrencyLayerSource src = new CurrencyLayerSource(localBase());
        CurrencyConverterException ex = assertThrows(
            CurrencyConverterException.class,
            () -> src.rate("dummy", Currency.RUB, Currency.BYN)
        );
        assertThat(ex.getMessage()).contains(CurrencyLayerSource.SERVICE_NAME);
    }

    // --- FixerSource ---

    @Test
    void fixerSource_rate_success() throws Exception {
        startServer("/fixer/convert", 200, "{\"success\":true,\"result\":3.5}");

        FixerSource src = new FixerSource(localBase());
        Double rate = src.rate("dummy", Currency.RUB, Currency.BYN);
        assertThat(rate).isEqualTo(3.5);
    }

    @Test
    void fixerSource_rate_failure() throws Exception {
        startServer("/fixer/convert", 200, "bad");

        FixerSource src = new FixerSource(localBase());
        CurrencyConverterException ex = assertThrows(
            CurrencyConverterException.class,
            () -> src.rate("dummy", Currency.RUB, Currency.BYN)
        );
        assertThat(ex.getMessage()).contains(FixerSource.SERVICE_NAME);
    }

    @Test
    void fixerSource_rate_failure_invalidNumber() throws Exception {
        startServer("/fixer/convert", 200, "{\"success\":true,\"result\":\"abc\"}");

        FixerSource src = new FixerSource(localBase());
        CurrencyConverterException ex = assertThrows(
            CurrencyConverterException.class,
            () -> src.rate("dummy", Currency.RUB, Currency.BYN)
        );
        assertThat(ex.getMessage()).contains(FixerSource.SERVICE_NAME);
    }

    // --- OpenExchangeRatesSource ---

    @Test
    void openExchangeRatesSource_rate_success() throws Exception {
        startServer("/api/latest.json", 200, "{\"rates\":{\"RUB\":2,\"BYN\":4}}");

        OpenExchangeRatesSource src = new OpenExchangeRatesSource(localBase());
        Double rate = src.rate("dummy", Currency.RUB, Currency.BYN);
        assertThat(rate).isEqualTo(2.0);
    }

    @Test
    void openExchangeRatesSource_rate_failure() throws Exception {
        startServer("/api/latest.json", 200, "bad");

        OpenExchangeRatesSource src = new OpenExchangeRatesSource(localBase());
        CurrencyConverterException ex = assertThrows(
            CurrencyConverterException.class,
            () -> src.rate("dummy", Currency.RUB, Currency.BYN)
        );
        assertThat(ex.getMessage()).contains(OpenExchangeRatesSource.SERVICE_NAME);
    }

    @Test
    void openExchangeRatesSource_rate_failure_invalidNumber() throws Exception {
        startServer("/api/latest.json", 200, "{\"rates\":{\"RUB\":\"abc\",\"BYN\":4}}");

        OpenExchangeRatesSource src = new OpenExchangeRatesSource(localBase());
        CurrencyConverterException ex = assertThrows(
            CurrencyConverterException.class,
            () -> src.rate("dummy", Currency.RUB, Currency.BYN)
        );
        assertThat(ex.getMessage()).contains(OpenExchangeRatesSource.SERVICE_NAME);
    }

    // --- CurrencyFreaksSource ---

    @Test
    void currencyFreaksSource_rate_success() throws Exception {
        startServer("/latest", 200, "{\"rates\":{\"RUB\":\"2\",\"BYN\":\"4\"}}");

        CurrencyFreaksSource src = new CurrencyFreaksSource(localBase());
        Double rate = src.rate("dummy", Currency.RUB, Currency.BYN);
        assertThat(rate).isEqualTo(2.0);
    }

    @Test
    void currencyFreaksSource_rate_failure() throws Exception {
        startServer("/latest", 200, "bad");

        CurrencyFreaksSource src = new CurrencyFreaksSource(localBase());
        CurrencyConverterException ex = assertThrows(
            CurrencyConverterException.class,
            () -> src.rate("dummy", Currency.RUB, Currency.BYN)
        );
        assertThat(ex.getMessage()).contains(CurrencyFreaksSource.SERVICE_NAME);
    }

    @Test
    void currencyFreaksSource_rate_failure_invalidNumber() throws Exception {
        startServer("/latest", 200, "{\"rates\":{\"RUB\":\"abc\",\"BYN\":\"4\"}}");

        CurrencyFreaksSource src = new CurrencyFreaksSource(localBase());
        CurrencyConverterException ex = assertThrows(
            CurrencyConverterException.class,
            () -> src.rate("dummy", Currency.RUB, Currency.BYN)
        );
        assertThat(ex.getMessage()).contains(CurrencyFreaksSource.SERVICE_NAME);
    }
}
