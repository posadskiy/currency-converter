package com.posadskiy.currencyconverter.source;

import com.posadskiy.currencyconverter.enums.Currency;
import com.posadskiy.currencyconverter.enums.Source;
import com.posadskiy.currencyconverter.util.PropertiesLoader;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertNotEquals;

public class OpenExchangeRatesSourceTest {
    public static final String API_KEY = PropertiesLoader.getApiKey(Source.OPEN_EXCHANGE_RATES);

    @Test
    public void rate() throws IOException {
        OpenExchangeRatesSource openExchangeRatesSource = new OpenExchangeRatesSource();

        final Double rate = openExchangeRatesSource.rate(API_KEY, Currency.BYN, Currency.EUR);

        assertNotEquals(0, rate);
    }
}
