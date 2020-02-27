package com.posadskiy.currencyconverter.source;


import com.posadskiy.currencyconverter.Messages;
import com.posadskiy.currencyconverter.enums.Currency;
import com.posadskiy.currencyconverter.util.NetworkUtils;

import java.io.IOException;

/**
 * CurrencyConverterApi.Com service
 */
public class CurrencyConverterApiSource implements ConverterSource {

	public Double rate(String apiKey, Currency from, Currency to) throws IOException {
		String collected = NetworkUtils.getBufferReaderByUrl(getUrlString(apiKey, from, to), true);

		String[] splitCurrencyInfo = collected.split(":");
		if (splitCurrencyInfo.length != 2) {
			throw new RuntimeException(Messages.getServiceUnavailableMessage("CurrencyConverterApi.Com"));
		}

		return Double.parseDouble(splitCurrencyInfo[1].replace("}", ""));
	}

	private String getUrlString(String apiKey, Currency from, Currency to) {
		return "https://free.currconv.com/api/v7/convert?q=" + from + "_" + to + "&compact=ultra&apiKey=" + apiKey;
	}
}
