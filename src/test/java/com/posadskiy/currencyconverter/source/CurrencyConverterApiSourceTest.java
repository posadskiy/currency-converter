package com.posadskiy.currencyconverter.source;

import com.posadskiy.currencyconverter.enums.Currency;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

public class CurrencyConverterApiSourceTest {

	@Test
	public void rate() throws IOException {
		final String apiKey = "YOUR API KEY";
		CurrencyConverterApiSource currencyConverterApiSource = new CurrencyConverterApiSource();

		final Double rateBynToEur = currencyConverterApiSource.rate(apiKey, Currency.BYN, Currency.EUR);
		final Double rateBynToUsd = currencyConverterApiSource.rate(apiKey, Currency.BYN, Currency.USD);

		assertTrue("BYN should be less than EUR", rateBynToEur < 1);
		assertTrue("BYN should be less than USD", rateBynToUsd < 1);
	}
}
