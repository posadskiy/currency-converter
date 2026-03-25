package com.posadskiy.currencyconverter.util;

import com.posadskiy.currencyconverter.exception.CurrencyConverterException;
import com.sun.net.httpserver.HttpServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.assertj.core.api.Assertions.assertThat;

public class NetworkUtilsTest {

    private HttpServer server;
    private int port;

    private void startServer(String contextPath, int statusCode, String body) throws IOException {
        server = HttpServer.create(new InetSocketAddress("localhost", 0), 0);
        port = server.getAddress().getPort();
        server.createContext(contextPath, exchange -> {
            byte[] bytes = body.getBytes();
            exchange.sendResponseHeaders(statusCode, bytes.length);
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(bytes);
            }
        });
        server.start();
    }

    @AfterEach
    void stopServer() {
        if (server != null) {
            server.stop(0);
        }
    }

    @Test
    void getBufferReaderByUrl_returnsBodyOn200() throws Exception {
        startServer("/ok", 200, "hello");

        String result = NetworkUtils.getBufferReaderByUrl("http://localhost:" + port + "/ok", false);
        assertEquals("hello", result);
    }

    @Test
    void getBufferReaderByUrl_throwsOnNon200() throws Exception {
        startServer("/fail", 500, "oops");

        CurrencyConverterException ex = assertThrows(
            CurrencyConverterException.class,
            () -> NetworkUtils.getBufferReaderByUrl("http://localhost:" + port + "/fail", false)
        );
        assertThat(ex.getMessage()).contains("Response: oops");
    }
}

