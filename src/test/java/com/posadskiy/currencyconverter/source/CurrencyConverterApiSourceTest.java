package com.posadskiy.currencyconverter.source;

import com.posadskiy.currencyconverter.enums.Currency;
import com.posadskiy.currencyconverter.enums.Source;
import com.posadskiy.currencyconverter.util.PropertiesLoader;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertTrue;

public class CurrencyConverterApiSourceTest {
    public static final String API_KEY = PropertiesLoader.getApiKey(Source.CURRENCY_CONVERTER_API);

    @Test
    public void rate() throws IOException {
        CurrencyConverterApiSource currencyConverterApiSource = new CurrencyConverterApiSource();

        final Double rateBynToEur = currencyConverterApiSource.rate(API_KEY, Currency.BYN, Currency.EUR);
        final Double rateBynToUsd = currencyConverterApiSource.rate(API_KEY, Currency.BYN, Currency.USD);

        assertTrue("BYN should be less than EUR", rateBynToEur < 1);
        assertTrue("BYN should be less than USD", rateBynToUsd < 1);
    }
}
