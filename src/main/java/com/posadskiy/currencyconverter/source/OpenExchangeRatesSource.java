package com.posadskiy.currencyconverter.source;

import com.posadskiy.currencyconverter.Messages;
import com.posadskiy.currencyconverter.enums.Currency;
import com.posadskiy.currencyconverter.exception.CurrencyConverterException;
import com.posadskiy.currencyconverter.util.NetworkUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * OpenExchangeRates.Com service
 */
public class OpenExchangeRatesSource implements ConverterSource {
	public static final String SERVICE_NAME = "OpenExchangeRates.Com";
	@Override
	public Double rate(String apiKey, Currency from, Currency to) throws IOException {
		String collected = NetworkUtils.getBufferReaderByUrl(getUrlString(apiKey, from, to), true);

		final String[] splitCurrencyInfo = collected.split("rates\":");
		if (splitCurrencyInfo.length != 2) {
			throw new CurrencyConverterException(Messages.getServiceUnavailableMessage(SERVICE_NAME));
		}

		final String[] split = splitCurrencyInfo[1].split(",");
		if (split.length != 2) {
			throw new CurrencyConverterException(Messages.getServiceUnavailableMessage(SERVICE_NAME));
		}

		Map<String, Double> currencyRates = new HashMap<>();
		try {
			String[] firstCurrencyRate = split[0].replaceAll(" ", "").replaceAll("\"", "").replace("{", "").split(":");
			String[] secondCurrencyRate = split[1].replaceAll(" ", "").replaceAll("\"", "").replaceAll("}", "").split(":");

			currencyRates.put(firstCurrencyRate[0], Double.parseDouble(firstCurrencyRate[1]));
			currencyRates.put(secondCurrencyRate[0], Double.parseDouble(secondCurrencyRate[1]));

			return currencyRates.get(to.name()) / currencyRates.get(from.name());
		} catch (Exception e) {
			throw new CurrencyConverterException(Messages.getServiceUnavailableMessage(SERVICE_NAME));
		}
	}

	private String getUrlString(String apiKey, Currency from, Currency to) {
		return "https://openexchangerates.org/api/latest.json?app_id=" + apiKey + "&symbols=" + from + "," + to;
	}
	
}
