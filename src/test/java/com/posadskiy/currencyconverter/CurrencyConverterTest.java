package com.posadskiy.currencyconverter;

import com.posadskiy.currencyconverter.config.ConfigBuilder;
import com.posadskiy.currencyconverter.enums.Currency;
import org.assertj.core.data.Percentage;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CurrencyConverterTest {
	public static final String CURRENCY_CONVERTER_API_API_KEY = "YOUR_API_KEY";
	public static final String CURRENCY_LAYER_API_KEY = "YOUR_API_KEY";
	public static final String OPEN_EXCHANGE_RATES_API_KEY = "YOUR_API_KEY";
	public static final double ALLOWED_RATE_DEVIATION_PERCENT = 5;
	public static final Currency FROM_CURRENCY = Currency.RUB;
	public static final Currency TO_CURRENCY = Currency.BYN;

	@Test
	public void rate_currencyConverterApi_working() {
		CurrencyConverter currencyConverter = new CurrencyConverter(
			new ConfigBuilder()
				.currencyConverterApiApiKey(CURRENCY_CONVERTER_API_API_KEY)
				.build()
		);

		final Double rate = currencyConverter.rate(FROM_CURRENCY, TO_CURRENCY);
		
		assertThat(rate).isGreaterThan(0);
	}

	@Test
	public void rate_currencyLayerApi_working() {
		CurrencyConverter currencyConverter = new CurrencyConverter(
			new ConfigBuilder()
				.currencyLayerApiKey(CURRENCY_LAYER_API_KEY)
				.build()
		);

		final Double rate = currencyConverter.rate(FROM_CURRENCY, TO_CURRENCY);
		
		assertThat(rate).isGreaterThan(0);
	}

	@Test
	public void rate_openExchangeRates_working() {
		CurrencyConverter currencyConverter = new CurrencyConverter(
			new ConfigBuilder()
				.openExchangeRatesApiKey(OPEN_EXCHANGE_RATES_API_KEY)
				.build()
		);

		final Double rate = currencyConverter.rate(FROM_CURRENCY, TO_CURRENCY);
		
		assertThat(rate).isGreaterThan(0);
	}

	@Test
	public void rate_allServices_resultsAreClose() {
		CurrencyConverter currencyConverterApi = new CurrencyConverter(
			new ConfigBuilder()
				.currencyConverterApiApiKey(CURRENCY_CONVERTER_API_API_KEY)
				.build()
		);
		final Double currencyConverterApiRate = currencyConverterApi.rate(FROM_CURRENCY, TO_CURRENCY);

		CurrencyConverter currencyLayer = new CurrencyConverter(
			new ConfigBuilder()
				.currencyLayerApiKey(CURRENCY_LAYER_API_KEY)
				.build()
		);
		final Double currencyLayerRate = currencyLayer.rate(FROM_CURRENCY, TO_CURRENCY);

		CurrencyConverter openExchangeRates = new CurrencyConverter(
			new ConfigBuilder()
				.openExchangeRatesApiKey(OPEN_EXCHANGE_RATES_API_KEY)
				.build()
		);
		final Double openExchangeRatesRate = openExchangeRates.rate(FROM_CURRENCY, TO_CURRENCY);

		assertThat(currencyConverterApiRate).isCloseTo(currencyLayerRate, Percentage.withPercentage(ALLOWED_RATE_DEVIATION_PERCENT));
		assertThat(openExchangeRatesRate).isCloseTo(currencyLayerRate, Percentage.withPercentage(ALLOWED_RATE_DEVIATION_PERCENT));
	}

	@Test
	public void rateFromUsdToEuro() {
	}

}
