package com.posadskiy.currencyconverter.service;


import com.posadskiy.currencyconverter.Currency;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.stream.Collectors;

public class CurrencyConvertService {

	public Double rate(String apiKey, Currency from, Currency to) {
		String collected = getBufferReaderByUrl(apiKey, from, to);

		String[] splitCurrencyInfo = collected.split(":");
		if (splitCurrencyInfo.length != 2) {
			throw new RuntimeException("Public API was changed");
		}

		return Double.parseDouble(splitCurrencyInfo[1].replace("}", ""));
	}

	private String getUrlString(String apiKey, Currency from, Currency to) {
		return "https://free.currconv.com/api/v7/convert?q=" + from + "_" + to + "&compact=ultra&apiKey=" + apiKey;
	}


	private String getBufferReaderByUrl(String apiKey, Currency from, Currency to) {
		try {
			URL url = new URL(getUrlString(apiKey, from, to));
			HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/json");

			if (conn.getResponseCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
			}

			String currency = new BufferedReader(
				new InputStreamReader(
					conn.getInputStream()))
				.lines()
				.collect(Collectors.joining());

			conn.disconnect();

			return currency;
		} catch (IOException e) {
			e.printStackTrace();
		}

		throw new RuntimeException("Get currency request not working. Please, try later");
	}
}
