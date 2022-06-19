package com.posadskiy.currencyconverter.source;

import com.posadskiy.currencyconverter.Messages;
import com.posadskiy.currencyconverter.enums.Currency;
import com.posadskiy.currencyconverter.exception.CurrencyConverterException;
import com.posadskiy.currencyconverter.util.NetworkUtils;

import java.io.IOException;

/**
 * Service: Fixer.io
 * Website: https://fixer.io/
 */
public class FixerSource implements ConverterSource {
    public static final String SERVICE_NAME = "Fixer.io";

    @Override
    public Double rate(String apiKey, Currency from, Currency to) throws IOException {
        String collected = NetworkUtils.getBufferReaderByUrl(getUrlString(apiKey, from, to), true);

        final String[] splitCurrencyInfo = collected.split("result\": ");
        if (splitCurrencyInfo.length != 2) {
            throw new CurrencyConverterException(Messages.getServiceUnavailableMessage(SERVICE_NAME));
        }

        final String[] split = splitCurrencyInfo[1].split("}");
        if (split.length != 1) {
            throw new CurrencyConverterException(Messages.getServiceUnavailableMessage(SERVICE_NAME));
        }

        try {
            return Double.parseDouble(split[0]);
        } catch (Exception e) {
            throw new CurrencyConverterException(Messages.getServiceUnavailableMessage(SERVICE_NAME));
        }
    }

    private String getUrlString(String apiKey, Currency from, Currency to) {
        return "https://api.apilayer.com/fixer/convert?from=" + from + "&to=" + to + "&amount=1&apikey=" + apiKey;
    }
}
