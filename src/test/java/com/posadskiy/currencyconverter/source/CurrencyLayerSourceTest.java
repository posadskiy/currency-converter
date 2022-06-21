package com.posadskiy.currencyconverter.source;

import com.posadskiy.currencyconverter.enums.Currency;
import com.posadskiy.currencyconverter.enums.Source;
import com.posadskiy.currencyconverter.util.PropertiesLoader;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertNotEquals;

public class CurrencyLayerSourceTest {
    public static final String API_KEY = PropertiesLoader.getApiKey(Source.CURRENCY_LAYER);

    @Test
    public void rate() throws IOException {
        CurrencyLayerSource currencyLayerSource = new CurrencyLayerSource();

        final Double rate = currencyLayerSource.rate(API_KEY, Currency.BYN, Currency.EUR);

        assertNotEquals(0, rate);
    }
}
