package com.posadskiy.currencyconverter.source;

import com.posadskiy.currencyconverter.enums.Currency;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

public class CurrencyFreaksSourceTest {

    @Test
    public void rate() throws IOException {
        final String apiKey = "YOUR_API_KEY";
        CurrencyFreaksSource currencyFreaksSource = new CurrencyFreaksSource();

        final Double rate = currencyFreaksSource.rate(apiKey, Currency.BYN, Currency.EUR);

        assertNotEquals(0, rate);
    }

    @Test
    public void rateBynToUsd() throws IOException {
        final String apiKey = "YOUR_API_KEY";
        CurrencyFreaksSource currencyFreaksSource = new CurrencyFreaksSource();

        final Double rate = currencyFreaksSource.rate(apiKey, Currency.BYN, Currency.USD);

        assertNotEquals(0, rate);
    }

}
