package com.posadskiy.currencyconverter.service;

import com.posadskiy.currencyconverter.Messages;
import com.posadskiy.currencyconverter.config.Config;
import com.posadskiy.currencyconverter.enums.Currency;
import com.posadskiy.currencyconverter.exception.CurrencyConverterException;
import com.posadskiy.currencyconverter.source.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Java 21+ implementation of CurrencyConvertService.
 *
 * <p>All configured sources are queried in parallel on virtual threads.
 * {@code invokeAny} returns the first successful result and cancels
 * remaining tasks, so the response time equals the fastest provider.
 */
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

        List<Callable<Double>> tasks = buildTasks(config, from, to);
        if (tasks.isEmpty()) {
            throw new CurrencyConverterException(Messages.TRY_LATER);
        }

        try (ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor()) {
            try {
                return executor.invokeAny(tasks);
            } catch (ExecutionException e) {
                Throwable cause = e.getCause();
                log.log(Level.WARNING, "All currency sources failed", cause != null ? cause : e);
                throw new CurrencyConverterException(Messages.TRY_LATER);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new CurrencyConverterException(Messages.TRY_LATER);
            }
        }
    }

    private List<Callable<Double>> buildTasks(Config config, Currency from, Currency to) {
        List<Callable<Double>> tasks = new ArrayList<>();
        if (config.getCurrencyConverterApiApiKey() != null) {
            String key = config.getCurrencyConverterApiApiKey();
            ConverterSource src = sourceBaseUrl != null
                ? new CurrencyConverterApiSource(sourceBaseUrl)
                : new CurrencyConverterApiSource();
            tasks.add(logged(CurrencyConverterApiSource.SERVICE_NAME, () -> src.rate(key, from, to)));
        }
        if (config.getCurrencyLayerApiKey() != null) {
            String key = config.getCurrencyLayerApiKey();
            ConverterSource src = sourceBaseUrl != null
                ? new CurrencyLayerSource(sourceBaseUrl)
                : new CurrencyLayerSource();
            tasks.add(logged(CurrencyLayerSource.SERVICE_NAME, () -> src.rate(key, from, to)));
        }
        if (config.getOpenExchangeRatesApiKey() != null) {
            String key = config.getOpenExchangeRatesApiKey();
            ConverterSource src = sourceBaseUrl != null
                ? new OpenExchangeRatesSource(sourceBaseUrl)
                : new OpenExchangeRatesSource();
            tasks.add(logged(OpenExchangeRatesSource.SERVICE_NAME, () -> src.rate(key, from, to)));
        }
        if (config.getFixerApiKey() != null) {
            String key = config.getFixerApiKey();
            ConverterSource src = sourceBaseUrl != null
                ? new FixerSource(sourceBaseUrl)
                : new FixerSource();
            tasks.add(logged(FixerSource.SERVICE_NAME, () -> src.rate(key, from, to)));
        }
        if (config.getCurrencyFreaksApiKey() != null) {
            String key = config.getCurrencyFreaksApiKey();
            ConverterSource src = sourceBaseUrl != null
                ? new CurrencyFreaksSource(sourceBaseUrl)
                : new CurrencyFreaksSource();
            tasks.add(logged(CurrencyFreaksSource.SERVICE_NAME, () -> src.rate(key, from, to)));
        }
        return tasks;
    }

    private Callable<Double> logged(String serviceName, Callable<Double> task) {
        return () -> {
            try {
                return task.call();
            } catch (Exception e) {
                log.log(Level.WARNING, Messages.getServiceProblemMessage(serviceName), e);
                throw e;
            }
        };
    }
}
