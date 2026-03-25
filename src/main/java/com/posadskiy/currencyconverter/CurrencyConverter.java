package com.posadskiy.currencyconverter;

import com.posadskiy.currencyconverter.config.Config;
import com.posadskiy.currencyconverter.enums.Currency;
import com.posadskiy.currencyconverter.exception.CurrencyConverterException;
import com.posadskiy.currencyconverter.service.CurrencyConvertService;
import com.posadskiy.currencyconverter.service.RateCache;

/**
 * <h1>Currency Converter</h1>
 * Fetches real-time exchange rates from multiple providers with automatic failover.
 *
 * <p>Optional in-memory rate caching can be enabled via
 * {@link com.posadskiy.currencyconverter.config.ConfigBuilder#cacheTtlSeconds(int)}.
 *
 * @author Dimitri Posadskiy
 * @since 2019-09-18
 */
public class CurrencyConverter {

	private final Config config;
	private final CurrencyConvertService currencyConvertService;
	private final RateCache rateCache;

	public CurrencyConverter(Config config) {
		this(config, new CurrencyConvertService());
	}

	CurrencyConverter(Config config, CurrencyConvertService currencyConvertService) {
		if (config == null) {
			throw new CurrencyConverterException("Config must not be null");
		}
		this.config = config;
		this.currencyConvertService = currencyConvertService;
		this.rateCache = config.getCacheTtlSeconds() > 0
			? new RateCache(config.getCacheTtlSeconds() * 1000L)
			: null;
	}

	/**
	 * Receives exchange rate.
	 *
	 * @param from the currency to be exchanged
	 * @param to the currency in which the exchange
	 * @return rate
	 */
	public Double rate(Currency from, Currency to) {
		if (from == null || to == null) {
			throw new CurrencyConverterException("Currency must not be null");
		}

		if (rateCache != null) {
			Double cached = rateCache.get(from, to);
			if (cached != null) return cached;
		}

		Double result = currencyConvertService.rate(this.config, from, to);

		if (rateCache != null) {
			rateCache.put(from, to, result);
		}

		return result;
	}

	/**
	 * Receives exchange rate (string-based, for dynamic input).
	 *
	 * @param from ISO 4217 currency code
	 * @param to ISO 4217 currency code
	 * @return rate
	 */
	public Double rate(String from, String to) {
		if (from == null || to == null) {
			throw new CurrencyConverterException("Currency code must not be null");
		}
		return rate(Currency.valueOf(from), Currency.valueOf(to));
	}

	public Double rateFromUsd(Currency to) {
		return rate(Currency.USD, to);
	}

	public Double rateFromUsdToEuro() {
		return rate(Currency.USD, Currency.EUR);
	}

	public Double rateFromEuroToUsd() {
		return rate(Currency.EUR, Currency.USD);
	}

	public Double rateToUsd(Currency from) {
		return rate(from, Currency.USD);
	}

	public Double rateFromEuro(Currency to) {
		return rate(Currency.EUR, to);
	}

	public Double rateToEuro(Currency from) {
		return rate(from, Currency.EUR);
	}

	public Double rateFromUah(Currency to) {
		return rate(Currency.UAH, to);
	}

	public Double rateToUah(Currency from) {
		return rate(from, Currency.UAH);
	}

	public Double rateFromRub(Currency to) {
		return rate(Currency.RUB, to);
	}

	public Double rateToRub(Currency from) {
		return rate(from, Currency.RUB);
	}

	public Double rateFromByn(Currency to) {
		return rate(Currency.BYN, to);
	}

	public Double rateToByn(Currency from) {
		return rate(from, Currency.BYN);
	}

	public Double rateToKwd(Currency from) {
		return rate(from, Currency.KWD);
	}

	public Double rateFromKuwaitiDinar(Currency to) {
		return rate(Currency.KWD, to);
	}

	public Double rateToKuwaitiDinar(Currency from) {
		return rate(from, Currency.BHD);
	}

	public Double rateFromBahrainiDinar(Currency to) {
		return rate(Currency.BHD, to);
	}

	public Double rateToOmanRial(Currency from) {
		return rate(from, Currency.OMR);
	}

	public Double rateFromOmanRial(Currency to) {
		return rate(Currency.OMR, to);
	}

	public Double rateToJordanDinar(Currency from) {
		return rate(from, Currency.JOD);
	}

	public Double rateFromJordanDinar(Currency to) {
		return rate(Currency.JOD, to);
	}

	public Double rateToBritishPoundSterling(Currency from) {
		return rate(from, Currency.GBP);
	}

	public Double rateFromBritishPoundSterling(Currency to) {
		return rate(Currency.GBP, to);
	}

	public Double rateToCaymanIslandsDollar(Currency from) {
		return rate(from, Currency.KYD);
	}

	public Double rateFromCaymanIslandsDollar(Currency to) {
		return rate(Currency.KYD, to);
	}

	public Double rateToSwissFranc(Currency from) {
		return rate(from, Currency.CHF);
	}

	public Double rateFromSwissFranc(Currency to) {
		return rate(Currency.CHF, to);
	}

	public Double rateToCanadianDollar(Currency from) {
		return rate(from, Currency.CAD);
	}

	public Double rateFromCanadianDollar(Currency to) {
		return rate(Currency.CAD, to);
	}
}
