package com.posadskiy.currencyconverter.source;

import com.posadskiy.currencyconverter.enums.Currency;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

public class FixerSourceTest {

    @Test
    public void rate() throws IOException {
        final String apiKey = "YOUR API KEY";
        FixerSource fixerSource = new FixerSource();

        final Double rate = fixerSource.rate(apiKey, Currency.BYN, Currency.EUR);

        assertNotEquals(0, rate);
    }

}
