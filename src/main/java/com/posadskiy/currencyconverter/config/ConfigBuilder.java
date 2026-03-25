package com.posadskiy.currencyconverter.config;

import com.posadskiy.currencyconverter.Messages;
import com.posadskiy.currencyconverter.exception.CurrencyConverterException;

public class ConfigBuilder {

    private String currencyConverterApiApiKey;
    private String currencyLayerApiKey;
    private String openExchangeRatesApiKey;
    private String fixerApiKey;
    private String currencyFreaksApiKey;
    private int cacheTtlSeconds;

    public ConfigBuilder currencyConverterApiApiKey(String apiKey) {
        this.currencyConverterApiApiKey = apiKey;
        return this;
    }

    public ConfigBuilder currencyLayerApiKey(String apiKey) {
        this.currencyLayerApiKey = apiKey;
        return this;
    }

    public ConfigBuilder openExchangeRatesApiKey(String apiKey) {
        this.openExchangeRatesApiKey = apiKey;
        return this;
    }

    public ConfigBuilder fixerApiKey(String apiKey) {
        this.fixerApiKey = apiKey;
        return this;
    }

    public ConfigBuilder currencyFreaksApiKey(String apiKey) {
        this.currencyFreaksApiKey = apiKey;
        return this;
    }

    /**
     * Enables in-memory rate caching with the given TTL.
     * Set to {@code 0} (default) to disable caching.
     *
     * @param seconds time-to-live for cached exchange rates
     */
    public ConfigBuilder cacheTtlSeconds(int seconds) {
        this.cacheTtlSeconds = seconds;
        return this;
    }

    public Config build() {
        if (currencyConverterApiApiKey == null
            && currencyLayerApiKey == null
            && openExchangeRatesApiKey == null
            && fixerApiKey == null
            && currencyFreaksApiKey == null) {
            throw new CurrencyConverterException(Messages.FILL_API_KEY);
        }

        return new Config(
            currencyConverterApiApiKey,
            currencyLayerApiKey,
            openExchangeRatesApiKey,
            fixerApiKey,
            currencyFreaksApiKey,
            cacheTtlSeconds
        );
    }
}
