package com.posadskiy.currencyconverter.config;

public class Config {
	private String currencyConverterApiApiKey;
	private String currencyLayerApiKey;
	private String openExchangeRatesApiKey;

	public String getCurrencyConverterApiApiKey() {
		return currencyConverterApiApiKey;
	}

	public void setCurrencyConverterApiApiKey(String currencyConverterApiApiKey) {
		this.currencyConverterApiApiKey = currencyConverterApiApiKey;
	}

	public String getCurrencyLayerApiKey() {
		return currencyLayerApiKey;
	}

	public void setCurrencyLayerApiKey(String currencyLayerApiKey) {
		this.currencyLayerApiKey = currencyLayerApiKey;
	}

	public String getOpenExchangeRatesApiKey() {
		return openExchangeRatesApiKey;
	}

	public void setOpenExchangeRatesApiKey(String openExchangeRatesApiKey) {
		this.openExchangeRatesApiKey = openExchangeRatesApiKey;
	}
}
