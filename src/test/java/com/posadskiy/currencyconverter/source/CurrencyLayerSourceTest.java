package com.posadskiy.currencyconverter.source;

import com.posadskiy.currencyconverter.enums.Currency;
import com.posadskiy.currencyconverter.enums.Source;
import com.posadskiy.currencyconverter.TestConfig;
import com.posadskiy.currencyconverter.util.PropertiesLoader;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class CurrencyLayerSourceTest {
    public static final String API_KEY = PropertiesLoader.getApiKey(Source.CURRENCY_LAYER);

    @Test
    public void rate() throws IOException {
        Assumptions.assumeTrue(
            TestConfig.runExternalTests() && TestConfig.isConfigured(API_KEY),
            "Skipping external integration tests. Set RUN_EXTERNAL_TESTS=true and provide real API keys."
        );

        CurrencyLayerSource currencyLayerSource = new CurrencyLayerSource();

        final Double rate = currencyLayerSource.rate(API_KEY, Currency.BYN, Currency.EUR);

        assertNotEquals(0, rate);
    }
}
