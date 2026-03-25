package com.posadskiy.currencyconverter.source;

import com.posadskiy.currencyconverter.enums.Currency;
import com.posadskiy.currencyconverter.enums.Source;
import com.posadskiy.currencyconverter.TestConfig;
import com.posadskiy.currencyconverter.util.PropertiesLoader;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class OpenExchangeRatesSourceTest {
    public static final String API_KEY = PropertiesLoader.getApiKey(Source.OPEN_EXCHANGE_RATES);

    @Test
    public void rate() throws IOException {
        Assumptions.assumeTrue(
            TestConfig.runExternalTests() && TestConfig.isConfigured(API_KEY),
            "Skipping external integration tests. Set RUN_EXTERNAL_TESTS=true and provide real API keys."
        );
        OpenExchangeRatesSource openExchangeRatesSource = new OpenExchangeRatesSource();

        final Double rate = openExchangeRatesSource.rate(API_KEY, Currency.BYN, Currency.EUR);

        assertNotEquals(0, rate);
    }
}
