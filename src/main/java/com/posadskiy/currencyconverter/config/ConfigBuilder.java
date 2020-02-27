package com.posadskiy.currencyconverter.config;

import com.posadskiy.currencyconverter.Messages;
import com.posadskiy.currencyconverter.exception.CurrencyConverterException;

public class ConfigBuilder {
	private Config config;

	public ConfigBuilder() {
		config = new Config();
	}

	public ConfigBuilder currencyConverterApiApiKey(String apiKey) {
		config.setCurrencyConverterApiApiKey(apiKey);

		return this;
	}

	public ConfigBuilder currencyLayerApiKey(String apiKey) {
		config.setCurrencyLayerApiKey(apiKey);

		return this;
	}

	public ConfigBuilder openExchangeRatesApiKey(String apiKey) {
		config.setOpenExchangeRatesApiKey(apiKey);

		return this;
	}

	public Config build() {
		if (config.getCurrencyConverterApiApiKey() == null && config.getCurrencyLayerApiKey() == null && config.getOpenExchangeRatesApiKey() == null) {
			throw new CurrencyConverterException(Messages.FILL_API_KEY);
		}

		return config;
	}
}
