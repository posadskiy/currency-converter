package com.posadskiy.currencyconverter.source;

import com.posadskiy.currencyconverter.enums.Currency;
import com.posadskiy.currencyconverter.enums.Source;
import com.posadskiy.currencyconverter.util.PropertiesLoader;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertNotEquals;

public class CurrencyFreaksSourceTest {
    public static final String API_KEY = PropertiesLoader.getApiKey(Source.CURRENCY_FREAKS);

    @Test
    public void rate() throws IOException {
        CurrencyFreaksSource currencyFreaksSource = new CurrencyFreaksSource();

        final Double rate = currencyFreaksSource.rate(API_KEY, Currency.BYN, Currency.EUR);

        assertNotEquals(0, rate);
    }

    @Test
    public void rateBynToUsd() throws IOException {
        CurrencyFreaksSource currencyFreaksSource = new CurrencyFreaksSource();

        final Double rate = currencyFreaksSource.rate(API_KEY, Currency.BYN, Currency.USD);

        assertNotEquals(0, rate);
    }

}
