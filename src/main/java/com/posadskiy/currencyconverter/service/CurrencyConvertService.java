package com.posadskiy.currencyconverter.service;

import com.posadskiy.currencyconverter.Messages;
import com.posadskiy.currencyconverter.config.Config;
import com.posadskiy.currencyconverter.enums.Currency;
import com.posadskiy.currencyconverter.exception.CurrencyConverterException;
import com.posadskiy.currencyconverter.source.*;

import java.util.logging.Logger;

public class CurrencyConvertService {
	static Logger log = Logger.getLogger(CurrencyConvertService.class.getName());
	public Double rate(Config config, Currency from, Currency to) {
		if (from == to) return 1.0;

		if (config.getCurrencyConverterApiApiKey() != null) {
			CurrencyConverterApiSource currencyConverterApiSource = new CurrencyConverterApiSource();
			try {
				return currencyConverterApiSource.rate(config.getCurrencyConverterApiApiKey(), from, to);
			} catch (CurrencyConverterException e) {
				e.printStackTrace();
			} catch (Exception e) {
				log.warning(Messages.getServiceProblemMessage(CurrencyConverterApiSource.SERVICE_NAME));
				e.printStackTrace();
			}
		}

		if (config.getCurrencyLayerApiKey() != null) {
			CurrencyLayerSource currencyLayerSource = new CurrencyLayerSource();
			try {
				return currencyLayerSource.rate(config.getCurrencyLayerApiKey(), from, to);
			} catch (CurrencyConverterException e) {
				e.printStackTrace();
			} catch (Exception e) {
				log.warning(Messages.getServiceProblemMessage(CurrencyLayerSource.SERVICE_NAME));
				e.printStackTrace();
			}
		}

		if (config.getOpenExchangeRatesApiKey() != null) {
			OpenExchangeRatesSource openExchangeRatesSource = new OpenExchangeRatesSource();
			try {
				return openExchangeRatesSource.rate(config.getOpenExchangeRatesApiKey(), from, to);
			} catch (CurrencyConverterException e) {
				e.printStackTrace();
			} catch (Exception e) {
				log.warning(Messages.getServiceProblemMessage(OpenExchangeRatesSource.SERVICE_NAME));
				e.printStackTrace();
			}
		}

		if (config.getFixerApiKey() != null) {
			FixerSource fixerSource = new FixerSource();
			try {
				return fixerSource.rate(config.getFixerApiKey(), from, to);
			} catch (CurrencyConverterException e) {
				e.printStackTrace();
			} catch (Exception e) {
				log.warning(Messages.getServiceProblemMessage(FixerSource.SERVICE_NAME));
				e.printStackTrace();
			}
		}

		if (config.getCurrencyFreaksApiKey() != null) {
			CurrencyFreaksSource currencyFreaksSource = new CurrencyFreaksSource();
			try {
				return currencyFreaksSource.rate(config.getCurrencyFreaksApiKey(), from, to);
			} catch (CurrencyConverterException e) {
				e.printStackTrace();
			} catch (Exception e) {
				log.warning(Messages.getServiceProblemMessage(CurrencyFreaksSource.SERVICE_NAME));
				e.printStackTrace();
			}
		}

		throw new CurrencyConverterException(Messages.TRY_LATER);
	}

}
