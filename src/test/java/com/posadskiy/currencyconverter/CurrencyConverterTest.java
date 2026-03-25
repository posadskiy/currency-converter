package com.posadskiy.currencyconverter;

import com.posadskiy.currencyconverter.config.ConfigBuilder;
import com.posadskiy.currencyconverter.enums.Currency;
import com.posadskiy.currencyconverter.enums.Source;
import com.posadskiy.currencyconverter.util.PropertiesLoader;
import org.assertj.core.data.Percentage;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CurrencyConverterTest {
    public static final String CURRENCY_CONVERTER_API_API_KEY = PropertiesLoader.getApiKey(Source.CURRENCY_CONVERTER_API);
    public static final String CURRENCY_LAYER_API_KEY = PropertiesLoader.getApiKey(Source.CURRENCY_LAYER);
    public static final String OPEN_EXCHANGE_RATES_API_KEY = PropertiesLoader.getApiKey(Source.OPEN_EXCHANGE_RATES);
    public static final String FIXER_API_KEY = PropertiesLoader.getApiKey(Source.FIXER);
    public static final String CURRENCY_FREAKS_API_KEY = PropertiesLoader.getApiKey(Source.CURRENCY_FREAKS);

    public static final double ALLOWED_RATE_DEVIATION_PERCENT = 5;
    public static final Currency FROM_CURRENCY = Currency.RUB;
    public static final Currency TO_CURRENCY = Currency.BYN;

    private static void requireConfigured(String apiKey) {
        Assumptions.assumeTrue(
            TestConfig.runExternalTests() && TestConfig.isConfigured(apiKey),
            "Skipping external integration tests. Set RUN_EXTERNAL_TESTS=true and provide real API keys."
        );
    }

    @Test
    @Disabled("CurrencyConverterApi provider is temporarily disabled (free tier not available).")
    public void rate_currencyConverterApi_working() {
        requireConfigured(CURRENCY_CONVERTER_API_API_KEY);
        CurrencyConverter currencyConverter = new CurrencyConverter(
            new ConfigBuilder()
                .currencyConverterApiApiKey(CURRENCY_CONVERTER_API_API_KEY)
                .build()
        );

        final Double rate = currencyConverter.rate(FROM_CURRENCY, TO_CURRENCY);

        assertThat(rate).isGreaterThan(0);
    }

    @Test
    public void rate_currencyLayerApi_working() {
        requireConfigured(CURRENCY_LAYER_API_KEY);
        CurrencyConverter currencyConverter = new CurrencyConverter(
            new ConfigBuilder()
                .currencyLayerApiKey(CURRENCY_LAYER_API_KEY)
                .build()
        );

        final Double rate = currencyConverter.rate(FROM_CURRENCY, TO_CURRENCY);

        assertThat(rate).isGreaterThan(0);
    }

    @Test
    public void rate_openExchangeRates_working() {
        requireConfigured(OPEN_EXCHANGE_RATES_API_KEY);
        CurrencyConverter currencyConverter = new CurrencyConverter(
            new ConfigBuilder()
                .openExchangeRatesApiKey(OPEN_EXCHANGE_RATES_API_KEY)
                .build()
        );

        final Double rate = currencyConverter.rate(FROM_CURRENCY, TO_CURRENCY);

        assertThat(rate).isGreaterThan(0);
    }

    @Test
    public void rate_fixer_working() {
        requireConfigured(FIXER_API_KEY);
        CurrencyConverter currencyConverter = new CurrencyConverter(
            new ConfigBuilder()
                .fixerApiKey(FIXER_API_KEY)
                .build()
        );

        final Double rate = currencyConverter.rate(FROM_CURRENCY, TO_CURRENCY);

        assertThat(rate).isGreaterThan(0);
    }

    @Test
    public void rate_currencyFreaks_working() {
        requireConfigured(CURRENCY_FREAKS_API_KEY);
        CurrencyConverter currencyConverter = new CurrencyConverter(
            new ConfigBuilder()
                .currencyFreaksApiKey(CURRENCY_FREAKS_API_KEY)
                .build()
        );

        final Double rate = currencyConverter.rate(FROM_CURRENCY, TO_CURRENCY);

        assertThat(rate).isGreaterThan(0);
    }

    @Test
    public void rate_allServices_resultsAreClose() {
        requireConfigured(CURRENCY_LAYER_API_KEY);
        requireConfigured(OPEN_EXCHANGE_RATES_API_KEY);
        requireConfigured(FIXER_API_KEY);
        requireConfigured(CURRENCY_FREAKS_API_KEY);

        CurrencyConverter currencyLayer = new CurrencyConverter(
            new ConfigBuilder()
                .currencyLayerApiKey(CURRENCY_LAYER_API_KEY)
                .build()
        );
        final Double currencyLayerRate = currencyLayer.rate(FROM_CURRENCY, TO_CURRENCY);

        CurrencyConverter openExchangeRates = new CurrencyConverter(
            new ConfigBuilder()
                .openExchangeRatesApiKey(OPEN_EXCHANGE_RATES_API_KEY)
                .build()
        );
        final Double openExchangeRatesRate = openExchangeRates.rate(FROM_CURRENCY, TO_CURRENCY);

        CurrencyConverter fixer = new CurrencyConverter(
            new ConfigBuilder()
                .fixerApiKey(FIXER_API_KEY)
                .build()
        );
        final Double fixerRate = fixer.rate(FROM_CURRENCY, TO_CURRENCY);

        CurrencyConverter currencyFreaks = new CurrencyConverter(
            new ConfigBuilder()
                .currencyFreaksApiKey(CURRENCY_FREAKS_API_KEY)
                .build()
        );
        final Double currencyFreaksRate = currencyFreaks.rate(FROM_CURRENCY, TO_CURRENCY);

        assertThat(openExchangeRatesRate).isCloseTo(currencyLayerRate, Percentage.withPercentage(ALLOWED_RATE_DEVIATION_PERCENT));
        assertThat(openExchangeRatesRate).isCloseTo(fixerRate, Percentage.withPercentage(ALLOWED_RATE_DEVIATION_PERCENT));
        assertThat(openExchangeRatesRate).isCloseTo(currencyFreaksRate, Percentage.withPercentage(ALLOWED_RATE_DEVIATION_PERCENT));
    }

}
