package com.posadskiy.currencyconverter.service;

import com.posadskiy.currencyconverter.Messages;
import com.posadskiy.currencyconverter.config.Config;
import com.posadskiy.currencyconverter.enums.Currency;
import com.posadskiy.currencyconverter.exception.CurrencyConverterException;
import com.posadskiy.currencyconverter.source.CurrencyConverterApiSource;
import com.posadskiy.currencyconverter.source.CurrencyLayerSource;
import com.posadskiy.currencyconverter.source.OpenExchangeRatesSource;

public class CurrencyConvertService {
	public Double rate(Config config, Currency from, Currency to) {
		if (config.getCurrencyConverterApiApiKey() != null) {
			CurrencyConverterApiSource currencyConverterApiSource = new CurrencyConverterApiSource();
			try {
				return currencyConverterApiSource.rate(config.getCurrencyConverterApiApiKey(), from, to);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		if (config.getCurrencyLayerApiKey() != null) {
			CurrencyLayerSource currencyLayerSource = new CurrencyLayerSource();
			try {
				return currencyLayerSource.rate(config.getCurrencyLayerApiKey(), from, to);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		if (config.getOpenExchangeRatesApiKey() != null) {
			OpenExchangeRatesSource openExchangeRatesSource = new OpenExchangeRatesSource();
			try {
				return openExchangeRatesSource.rate(config.getOpenExchangeRatesApiKey(), from, to);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		throw new CurrencyConverterException(Messages.TRY_LATER);
	}

}
