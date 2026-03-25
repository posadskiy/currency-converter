package com.posadskiy.currencyconverter.source;

import com.posadskiy.currencyconverter.enums.Currency;
import com.posadskiy.currencyconverter.enums.Source;
import com.posadskiy.currencyconverter.TestConfig;
import com.posadskiy.currencyconverter.util.PropertiesLoader;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class CurrencyConverterApiSourceTest {
    public static final String API_KEY = PropertiesLoader.getApiKey(Source.CURRENCY_CONVERTER_API);

    @Disabled("CurrencyConverterApi provider is temporarily disabled (free tier not available).")
    @Test
    public void rate() throws IOException {
        Assumptions.assumeTrue(
            TestConfig.runExternalTests() && TestConfig.isConfigured(API_KEY),
            "Skipping external integration tests. Set RUN_EXTERNAL_TESTS=true and provide real API keys."
        );
        CurrencyConverterApiSource currencyConverterApiSource = new CurrencyConverterApiSource();

        final Double rateBynToEur = currencyConverterApiSource.rate(API_KEY, Currency.BYN, Currency.EUR);
        final Double rateBynToUsd = currencyConverterApiSource.rate(API_KEY, Currency.BYN, Currency.USD);

        assertTrue(rateBynToEur < 1, "BYN should be less than EUR");
        assertTrue(rateBynToUsd < 1, "BYN should be less than USD");
    }
}
