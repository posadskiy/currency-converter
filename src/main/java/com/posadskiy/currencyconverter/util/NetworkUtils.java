package com.posadskiy.currencyconverter.util;

import com.posadskiy.currencyconverter.Messages;
import com.posadskiy.currencyconverter.exception.CurrencyConverterException;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;
import java.util.stream.Collectors;

public class NetworkUtils {
	public static String getBufferReaderByUrl(String urlString, boolean isHttps) throws IOException {
		URL url = new URL(urlString);
		HttpURLConnection conn = isHttps ? (HttpsURLConnection) url.openConnection() : (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		conn.setRequestProperty("Accept", "application/json");

		if (conn.getResponseCode() != 200) {
			Scanner s = new Scanner(conn.getErrorStream()).useDelimiter("\\A");
			String serviceNetworkErrorMessage = s.hasNext() ? s.next() : "";
			throw new CurrencyConverterException(Messages.getServiceNetworkErrorMessage(serviceNetworkErrorMessage));
		}

		String currency = new BufferedReader(
			new InputStreamReader(
				conn.getInputStream()))
			.lines()
			.collect(Collectors.joining());

		conn.disconnect();

		return currency;
	}
}
