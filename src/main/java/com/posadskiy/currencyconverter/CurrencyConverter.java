package com.posadskiy.currencyconverter;

import com.posadskiy.currencyconverter.config.Config;
import com.posadskiy.currencyconverter.enums.Currency;
import com.posadskiy.currencyconverter.service.CurrencyConvertService;

/**
 * <h1>Currency Converter</h1>
 * The CurrencyConverter class allow to get exchanging rate for two currencies in real time.
 *
 * @author Dmitriy Posadskiy
 * @version 1.0
 * @since 2019-09-18
 */
public class CurrencyConverter {

	/**
	 * Configuration class for conversion
	 */
	private Config config;

	private CurrencyConvertService currencyConvertService = new CurrencyConvertService();

	private CurrencyConverter() {};

	public CurrencyConverter(Config config) {
		this.config = config;
	}

	/**
	 * Receives exchange rate
	 *
	 * @param from The currency to be exchanged
	 * @param to The currency in which the exchange
	 * @return rate
	 */
	public Double rate(Currency from, Currency to) {
		return currencyConvertService.rate(this.config, from, to);
	}

	/**
	 * Receives exchange rate from USD
	 *
	 * @param to The currency in which the exchange
	 * @return rate
	 */
	public Double rateFromUsd(Currency to) {
		return currencyConvertService.rate(this.config, Currency.USD, to);
	}

	/**
	 * Receives exchange rate from USD to Euro
	 *
	 * @return rate
	 */
	public Double rateFromUsdToEuro() {
		return currencyConvertService.rate(this.config, Currency.USD, Currency.EUR);
	}

	/**
	 * Receives exchange rate from Euro to USD
	 *
	 * @return rate
	 */
	public Double rateFromEuroToUsd() {
		return currencyConvertService.rate(this.config, Currency.EUR, Currency.USD);
	}

	/**
	 * Receives exchange rate to USD
	 *
	 * @param from The currency to be exchanged
	 * @return rate
	 */
	public Double rateToUsd(Currency from) {
		return currencyConvertService.rate(this.config, from, Currency.USD);
	}

	/**
	 * Receives exchange rate from Euro
	 *
	 * @param to The currency in which the exchange
	 * @return rate
	 */
	public Double rateFromEuro(Currency to) {
		return currencyConvertService.rate(this.config, Currency.EUR, to);
	}

	/**
	 * Receives exchange rate to Euro
	 *
	 * @param from The currency to be exchanged
	 * @return rate
	 */
	public Double rateToEuro(Currency from) {
		return currencyConvertService.rate(this.config, from, Currency.EUR);
	}

	/**
	 * Receives exchange rate from UAH
	 *
	 * @param to The currency in which the exchange
	 * @return rate
	 */
	public Double rateFromUah(Currency to) {
		return currencyConvertService.rate(this.config, Currency.UAH, to);
	}

	/**
	 * Receives exchange rate to UAH
	 *
	 * @param from The currency to be exchanged
	 * @return rate
	 */
	public Double rateToUah(Currency from) {
		return currencyConvertService.rate(this.config, from, Currency.UAH);
	}

	/**
	 * Receives exchange rate from Russian Rubble
	 *
	 * @param to The currency in which the exchange
	 * @return rate
	 */
	public Double rateFromRub(Currency to) {
		return currencyConvertService.rate(this.config, Currency.RUB, to);
	}

	/**
	 * Receives exchange rate to Russian Rubble
	 *
	 * @param from The currency to be exchanged
	 * @return rate
	 */
	public Double rateToRub(Currency from) {
		return currencyConvertService.rate(this.config, from, Currency.RUB);
	}

	/**
	 * Receives exchange rate from New Belorussian Rubble (BYN)
	 *
	 * @param to The currency in which the exchange
	 * @return rate
	 */
	public Double rateFromByn(Currency to) {
		return currencyConvertService.rate(this.config, Currency.BYN, to);
	}

	/**
	 * Receives exchange rate to New Belorussian Rubble (BYN)
	 *
	 * @param from The currency to be exchanged
	 * @return rate
	 */
	public Double rateToByn(Currency from) {
		return currencyConvertService.rate(this.config, from, Currency.BYN);
	}

	/**
	 * Receives exchange rate to Kuwaiti Dinar (KWD)
	 *
	 * @param from The currency to be exchanged
	 * @return rate
	 */
	public Double rateToKwd(Currency from) {
		return currencyConvertService.rate(this.config, from, Currency.KWD);
	}

	/**
	 * Receives exchange rate from Kuwaiti Dinar (KWD)
	 *
	 * @param to The currency in which the exchange
	 * @return rate
	 */
	public Double rateFromKuwaitiDinar(Currency to) {
		return currencyConvertService.rate(this.config, Currency.KWD, to);
	}

	/**
	 * Receives exchange rate to Bahraini Dinar (BHD)
	 *
	 * @param from The currency to be exchanged
	 * @return rate
	 */
	public Double rateToKuwaitiDinar(Currency from) {
		return currencyConvertService.rate(this.config, from, Currency.BHD);
	}

	/**
	 * Receives exchange rate from Bahraini Dinar (BHD)
	 *
	 * @param to The currency in which the exchange
	 * @return rate
	 */
	public Double rateFromBahrainiDinar(Currency to) {
		return currencyConvertService.rate(this.config, Currency.BHD, to);
	}

	/**
	 * Receives exchange rate to Oman Rial (OMR)
	 *
	 * @param from The currency to be exchanged
	 * @return rate
	 */
	public Double rateToOmanRial(Currency from) {
		return currencyConvertService.rate(this.config, from, Currency.OMR);
	}

	/**
	 * Receives exchange rate from Oman Rial (OMR)
	 *
	 * @param to The currency in which the exchange
	 * @return rate
	 */
	public Double rateFromOmanRial(Currency to) {
		return currencyConvertService.rate(this.config, Currency.OMR, to);
	}

	/**
	 * Receives exchange rate to Jordan Dinar (JOD)
	 *
	 * @param from The currency to be exchanged
	 * @return rate
	 */
	public Double rateToJordanDinar(Currency from) {
		return currencyConvertService.rate(this.config, from, Currency.JOD);
	}

	/**
	 * Receives exchange rate from Jordan Dinar (JOD)
	 *
	 * @param to The currency in which the exchange
	 * @return rate
	 */
	public Double rateFromJordanDinar(Currency to) {
		return currencyConvertService.rate(this.config, Currency.JOD, to);
	}

	/**
	 * Receives exchange rate to British Pound Sterling (GBP)
	 *
	 * @param from The currency to be exchanged
	 * @return rate
	 */
	public Double rateToBritishPoundSterling(Currency from) {
		return currencyConvertService.rate(this.config, from, Currency.GBP);
	}

	/**
	 * Receives exchange rate from British Pound Sterling (GBP)
	 *
	 * @param to The currency in which the exchange
	 * @return rate
	 */
	public Double rateFromBritishPoundSterling(Currency to) {
		return currencyConvertService.rate(this.config, Currency.GBP, to);
	}

	/**
	 * Receives exchange rate to Cayman Islands Dollar (KYD)
	 *
	 * @param from The currency to be exchanged
	 * @return rate
	 */
	public Double rateToCaymanIslandsDollar(Currency from) {
		return currencyConvertService.rate(this.config, from, Currency.KYD);
	}

	/**
	 * Receives exchange rate from Cayman Islands Dollar (KYD)
	 *
	 * @param to The currency in which the exchange
	 * @return rate
	 */
	public Double rateFromCaymanIslandsDollar(Currency to) {
		return currencyConvertService.rate(this.config, Currency.KYD, to);
	}

	/**
	 * Receives exchange rate to Swiss Franc (CHF)
	 *
	 * @param from The currency to be exchanged
	 * @return rate
	 */
	public Double rateToSwissFranc(Currency from) {
		return currencyConvertService.rate(this.config, from, Currency.CHF);
	}

	/**
	 * Receives exchange rate from Swiss Franc (CHF)
	 *
	 * @param to The currency in which the exchange
	 * @return rate
	 */
	public Double rateFromSwissFranc(Currency to) {
		return currencyConvertService.rate(this.config, Currency.CHF, to);
	}

	/**
	 * Receives exchange rate to Canadian Dollar (CAD)
	 *
	 * @param from The currency to be exchanged
	 * @return rate
	 */
	public Double rateToCanadianDollar(Currency from) {
		return currencyConvertService.rate(this.config, from, Currency.CAD);
	}

	/**
	 * Receives exchange rate from Canadian Dollar (CAD)
	 *
	 * @param to The currency in which the exchange
	 * @return rate
	 */
	public Double rateFromCanadianDollar(Currency to) {
		return currencyConvertService.rate(this.config, Currency.CAD, to);
	}

	/**
	 * Receives exchange rate
	 *
	 * @param from The currency to be exchanged
	 * @param to The currency in which the exchange
	 * @return rate
	 */
	public Double rate(String from, String to) {
		return currencyConvertService.rate(this.config, Currency.valueOf(from), Currency.valueOf(to));
	}
}
