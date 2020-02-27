package com.posadskiy.currencyconverter.source;

import com.posadskiy.currencyconverter.Messages;
import com.posadskiy.currencyconverter.enums.Currency;
import com.posadskiy.currencyconverter.exception.CurrencyConverterException;
import com.posadskiy.currencyconverter.util.NetworkUtils;

import java.io.IOException;

/**
 * OpenExchangeRates.Com service
 */
public class OpenExchangeRatesSource implements ConverterSource {
	@Override
	public Double rate(String apiKey, Currency from, Currency to) throws IOException {
		String collected = NetworkUtils.getBufferReaderByUrl(getUrlString(apiKey, from, to), true);

		final String[] splitCurrencyInfo = collected.split("rates\":");
		if (splitCurrencyInfo.length != 2) {
			throw new CurrencyConverterException(Messages.getServiceUnavailableMessage("OpenExchangeRates.Com"));
		}

		final String[] split = splitCurrencyInfo[1].split(",");
		if (split.length != 2) {
			throw new CurrencyConverterException(Messages.getServiceUnavailableMessage("OpenExchangeRates.Com"));
		}

		try {
			final String fromRate = split[0].replaceAll(" ", "").replaceAll("\"", "").replace("{", "").split(":")[1];
			final String toRate = split[1].replaceAll(" ", "").replaceAll("\"", "").replaceAll("}", "").split(":")[1];

			return Double.parseDouble(toRate) / Double.parseDouble(fromRate);
		} catch (Exception e) {
			throw new CurrencyConverterException(Messages.getServiceUnavailableMessage("OpenExchangeRates.Com"));
		}
	}

	private String getUrlString(String apiKey, Currency from, Currency to) {
		return "https://openexchangerates.org/api/latest.json?app_id=" + apiKey + "&symbols=" + from + "," + to;
	}
	
}
