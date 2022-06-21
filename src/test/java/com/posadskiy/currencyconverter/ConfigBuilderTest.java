package com.posadskiy.currencyconverter;

import com.posadskiy.currencyconverter.config.Config;
import com.posadskiy.currencyconverter.config.ConfigBuilder;
import com.posadskiy.currencyconverter.exception.CurrencyConverterException;
import org.junit.Test;

import static org.junit.Assert.*;

public class ConfigBuilderTest {
	public static final String CURRENCY_CONVERTER_API_API_KEY = "CURRENCY_CONVERTER_API";
	public static final String CURRENCY_LAYER = "CURRENCY_LAYER";
	public static final String OPEN_EXCHANGE_RATES = "OPEN_EXCHANGE_RATES";
	public static final String FIXER = "FIXER";
	public static final String CURRENCY_FREAKS = "CURRENCY_FREAKS";
	
	@Test(expected = CurrencyConverterException.class)
	public void checkBuilderWithoutFilling() {
		new ConfigBuilder()
			.build();
	}
	
	@Test
	public void checkBuilderFullFilling() {
		final Config config = new ConfigBuilder()
			.currencyConverterApiApiKey(CURRENCY_CONVERTER_API_API_KEY)
			.currencyLayerApiKey(CURRENCY_LAYER)
			.openExchangeRatesApiKey(OPEN_EXCHANGE_RATES)
			.fixerApiKey(FIXER)
			.currencyFreaksApiKey(CURRENCY_FREAKS)
			.build();
		
		assertEquals(CURRENCY_CONVERTER_API_API_KEY, config.getCurrencyConverterApiApiKey());
		assertEquals(CURRENCY_LAYER, config.getCurrencyLayerApiKey());
		assertEquals(OPEN_EXCHANGE_RATES, config.getOpenExchangeRatesApiKey());
		assertEquals(FIXER, config.getFixerApiKey());
		assertEquals(CURRENCY_FREAKS, config.getCurrencyFreaksApiKey());
	}

}
