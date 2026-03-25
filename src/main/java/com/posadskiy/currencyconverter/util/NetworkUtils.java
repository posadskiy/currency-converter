package com.posadskiy.currencyconverter.util;

import com.posadskiy.currencyconverter.Messages;
import com.posadskiy.currencyconverter.exception.CurrencyConverterException;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;
import java.util.stream.Collectors;

public class NetworkUtils {

    private static final int TIMEOUT_MS = 10_000;

    public static String getBufferReaderByUrl(String urlString, boolean isHttps) throws IOException {
        URL url = new URL(urlString);
        HttpURLConnection conn = isHttps
            ? (HttpsURLConnection) url.openConnection()
            : (HttpURLConnection) url.openConnection();

        conn.setRequestMethod("GET");
        conn.setRequestProperty("Accept", "application/json");
        conn.setRequestProperty("User-Agent", "Mozilla/5.0");
        conn.setRequestProperty("Cache-Control", "no-cache");
        conn.setConnectTimeout(TIMEOUT_MS);
        conn.setReadTimeout(TIMEOUT_MS);

        try {
            if (conn.getResponseCode() != 200) {
                String errorBody = "";
                try (InputStream errStream = conn.getErrorStream()) {
                    if (errStream != null) {
                        try (Scanner s = new Scanner(errStream, "UTF-8").useDelimiter("\\A")) {
                            errorBody = s.hasNext() ? s.next() : "";
                        }
                    }
                }
                throw new CurrencyConverterException(Messages.getServiceNetworkErrorMessage(errorBody));
            }

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
                return reader.lines().collect(Collectors.joining());
            }
        } finally {
            conn.disconnect();
        }
    }
}
