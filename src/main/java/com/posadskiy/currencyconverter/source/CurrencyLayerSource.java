package com.posadskiy.currencyconverter.source;

import com.posadskiy.currencyconverter.Messages;
import com.posadskiy.currencyconverter.enums.Currency;
import com.posadskiy.currencyconverter.exception.CurrencyConverterException;
import com.posadskiy.currencyconverter.util.NetworkUtils;

import java.io.IOException;

/**
 * CurrencyLayer.Com service
 */
public class CurrencyLayerSource implements ConverterSource {
	public static final String SERVICE_NAME = "CurrencyLayer.Com";

	@Override
	public Double rate(String apiKey, Currency from, Currency to) throws IOException {
		String collected = NetworkUtils.getBufferReaderByUrl(getUrlString(apiKey, from, to), false);

		final String[] splitCurrencyInfo = collected.split("quotes\":");
		if (splitCurrencyInfo.length != 2) {
			throw new CurrencyConverterException(Messages.getServiceUnavailableMessage(SERVICE_NAME));
		}

		final String[] split = splitCurrencyInfo[1].split(",");
		if (split.length != 2) {
			throw new CurrencyConverterException(Messages.getServiceUnavailableMessage(SERVICE_NAME));
		}

		try {
			final String fromRate = split[0].replaceAll("\"", "").replace("{", "").split(":")[1];
			final String toRate = split[1].replaceAll("\"", "").replaceAll("}", "").split(":")[1];

			return Double.parseDouble(toRate) / Double.parseDouble(fromRate);
		} catch (Exception e) {
			throw new CurrencyConverterException(Messages.getServiceUnavailableMessage(SERVICE_NAME));
		}
	}

	private String getUrlString(String apiKey, Currency from, Currency to) {
		return "http://api.currencylayer.com/live?access_key=" + apiKey + "&currencies=" + from + "," + to;
	}

}
