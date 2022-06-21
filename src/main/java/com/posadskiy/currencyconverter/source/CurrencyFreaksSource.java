package com.posadskiy.currencyconverter.source;

import com.posadskiy.currencyconverter.Messages;
import com.posadskiy.currencyconverter.enums.Currency;
import com.posadskiy.currencyconverter.exception.CurrencyConverterException;
import com.posadskiy.currencyconverter.util.NetworkUtils;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Service: CurrencyFreaks.Com
 * Website: https://currencyfreaks.com/
 */
public class CurrencyFreaksSource implements ConverterSource {
    public static final String SERVICE_NAME = "CurrencyFreaks.Com";

    @Override
    public Double rate(String apiKey, Currency from, Currency to) throws IOException {
        String collected = NetworkUtils.getBufferReaderByUrl(getUrlString(apiKey), true);

        final String[] splitCurrencyInfo = collected.split("rates\":\\{");
        if (splitCurrencyInfo.length != 2) {
            throw new CurrencyConverterException(Messages.getServiceUnavailableMessage(SERVICE_NAME));
        }

        final String[] split = splitCurrencyInfo[1].replace("}}", "").split(",");
        final List<String> currencies = Arrays.asList(split);

        Map<String, Double> currencyRates = new HashMap<>();
        for (String currency : currencies) {
            if (currency.contains(from.name())) {
                currencyRates.put(from.name(), parseCurrency(currency, from));
            }

            if (currency.contains(to.name())) {
                currencyRates.put(to.name(), parseCurrency(currency, to));
            }

            if (currencyRates.size() == 2) break;
        }

        return currencyRates.get(to.name()) / currencyRates.get(from.name());
    }

    private Double parseCurrency(String source, Currency currency) {
        try {
            return Double.parseDouble(
                source.replace("\"" + currency.name() + "\":\"", "").replace("\"", "")
            );
        } catch (Exception e) {
            throw new CurrencyConverterException(Messages.getServiceUnavailableMessage(SERVICE_NAME));
        }
    }

    private String getUrlString(String apiKey) {
        return "https://api.currencyfreaks.com/latest?apikey=" + apiKey;
    }
}
