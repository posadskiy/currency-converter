package com.posadskiy.currencyconverter.service;

import com.posadskiy.currencyconverter.Messages;
import com.posadskiy.currencyconverter.config.Config;
import com.posadskiy.currencyconverter.enums.Currency;
import com.posadskiy.currencyconverter.exception.CurrencyConverterException;
import com.posadskiy.currencyconverter.source.*;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CurrencyConvertService {

    static final Logger log = Logger.getLogger(CurrencyConvertService.class.getName());

    private final String sourceBaseUrl;

    public CurrencyConvertService() {
        this.sourceBaseUrl = null;
    }

    /**
     * Creates a service that directs all provider requests to the given base URL.
     * Useful for testing or proxying through a custom endpoint.
     */
    public CurrencyConvertService(String sourceBaseUrl) {
        this.sourceBaseUrl = sourceBaseUrl;
    }

    public Double rate(Config config, Currency from, Currency to) {
        if (from == to) return 1.0;

        for (SourceEntry entry : buildEntries(config)) {
            try {
                return entry.source.rate(entry.apiKey, from, to);
            } catch (CurrencyConverterException e) {
                log.warning(e.getMessage());
            } catch (Exception e) {
                log.log(Level.WARNING, Messages.getServiceProblemMessage(entry.name), e);
            }
        }

        throw new CurrencyConverterException(Messages.TRY_LATER);
    }

    List<SourceEntry> buildEntries(Config config) {
        List<SourceEntry> entries = new ArrayList<>();
        if (config.getCurrencyConverterApiApiKey() != null) {
            entries.add(new SourceEntry(CurrencyConverterApiSource.SERVICE_NAME,
                sourceBaseUrl != null ? new CurrencyConverterApiSource(sourceBaseUrl) : new CurrencyConverterApiSource(),
                config.getCurrencyConverterApiApiKey()));
        }
        if (config.getCurrencyLayerApiKey() != null) {
            entries.add(new SourceEntry(CurrencyLayerSource.SERVICE_NAME,
                sourceBaseUrl != null ? new CurrencyLayerSource(sourceBaseUrl) : new CurrencyLayerSource(),
                config.getCurrencyLayerApiKey()));
        }
        if (config.getOpenExchangeRatesApiKey() != null) {
            entries.add(new SourceEntry(OpenExchangeRatesSource.SERVICE_NAME,
                sourceBaseUrl != null ? new OpenExchangeRatesSource(sourceBaseUrl) : new OpenExchangeRatesSource(),
                config.getOpenExchangeRatesApiKey()));
        }
        if (config.getFixerApiKey() != null) {
            entries.add(new SourceEntry(FixerSource.SERVICE_NAME,
                sourceBaseUrl != null ? new FixerSource(sourceBaseUrl) : new FixerSource(),
                config.getFixerApiKey()));
        }
        if (config.getCurrencyFreaksApiKey() != null) {
            entries.add(new SourceEntry(CurrencyFreaksSource.SERVICE_NAME,
                sourceBaseUrl != null ? new CurrencyFreaksSource(sourceBaseUrl) : new CurrencyFreaksSource(),
                config.getCurrencyFreaksApiKey()));
        }
        return entries;
    }

    static final class SourceEntry {
        final String name;
        final ConverterSource source;
        final String apiKey;

        SourceEntry(String name, ConverterSource source, String apiKey) {
            this.name = name;
            this.source = source;
            this.apiKey = apiKey;
        }
    }
}
