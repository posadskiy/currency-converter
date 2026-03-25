package com.posadskiy.currencyconverter.util;

import com.posadskiy.currencyconverter.Messages;
import com.posadskiy.currencyconverter.exception.CurrencyConverterException;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

/**
 * Java 11+ implementation of NetworkUtils.
 * Uses {@link HttpClient} for connection pooling, persistent connections,
 * HTTP/2 support, and better timeout handling compared to the Java 8 baseline.
 */
public class NetworkUtils {

    private static final HttpClient CLIENT = HttpClient.newBuilder()
        .connectTimeout(Duration.ofSeconds(10))
        .version(HttpClient.Version.HTTP_2)
        .build();

    public static String getBufferReaderByUrl(String urlString, boolean isHttps) throws IOException {
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(urlString))
            .GET()
            .header("Accept", "application/json")
            .header("User-Agent", "Mozilla/5.0")
            .header("Cache-Control", "no-cache")
            .timeout(Duration.ofSeconds(10))
            .build();

        try {
            HttpResponse<String> response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() != 200) {
                throw new CurrencyConverterException(
                    Messages.getServiceNetworkErrorMessage("HTTP " + response.statusCode() + ": " + response.body())
                );
            }
            return response.body();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new IOException("HTTP request interrupted", e);
        }
    }
}
