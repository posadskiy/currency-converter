package com.posadskiy.currencyconverter.config;

public class Config {
	private String currencyConverterApiApiKey;
	private String currencyLayerApiKey;
	private String openExchangeRatesApiKey;
	private String fixerApiKey;
	private String currencyFreaksApiKey;

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

    public String getFixerApiKey() {
        return fixerApiKey;
    }

    public void setFixerApiKey(String fixerApiKey) {
        this.fixerApiKey = fixerApiKey;
    }

    public String getCurrencyFreaksApiKey() {
        return currencyFreaksApiKey;
    }

    public void setCurrencyFreaksApiKey(String currencyFreaksApiKey) {
        this.currencyFreaksApiKey = currencyFreaksApiKey;
    }
}
