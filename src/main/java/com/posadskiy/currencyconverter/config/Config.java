package com.posadskiy.currencyconverter.config;

/**
 * Immutable configuration holding API keys and cache settings.
 * Use {@link ConfigBuilder} to construct instances.
 */
public class Config {

    private final String currencyConverterApiApiKey;
    private final String currencyLayerApiKey;
    private final String openExchangeRatesApiKey;
    private final String fixerApiKey;
    private final String currencyFreaksApiKey;
    private final int cacheTtlSeconds;

    /** Creates an empty configuration with no API keys and caching disabled. */
    public Config() {
        this(null, null, null, null, null, 0);
    }

    Config(String currencyConverterApiApiKey, String currencyLayerApiKey,
           String openExchangeRatesApiKey, String fixerApiKey,
           String currencyFreaksApiKey, int cacheTtlSeconds) {
        this.currencyConverterApiApiKey = currencyConverterApiApiKey;
        this.currencyLayerApiKey = currencyLayerApiKey;
        this.openExchangeRatesApiKey = openExchangeRatesApiKey;
        this.fixerApiKey = fixerApiKey;
        this.currencyFreaksApiKey = currencyFreaksApiKey;
        this.cacheTtlSeconds = cacheTtlSeconds;
    }

    public String getCurrencyConverterApiApiKey() {
        return currencyConverterApiApiKey;
    }

    public String getCurrencyLayerApiKey() {
        return currencyLayerApiKey;
    }

    public String getOpenExchangeRatesApiKey() {
        return openExchangeRatesApiKey;
    }

    public String getFixerApiKey() {
        return fixerApiKey;
    }

    public String getCurrencyFreaksApiKey() {
        return currencyFreaksApiKey;
    }

    /**
     * Returns the rate-cache time-to-live in seconds.
     * {@code 0} (default) means caching is disabled.
     */
    public int getCacheTtlSeconds() {
        return cacheTtlSeconds;
    }
}
