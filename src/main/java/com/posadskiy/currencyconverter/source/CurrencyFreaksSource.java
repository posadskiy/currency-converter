package com.posadskiy.currencyconverter.source;

import com.posadskiy.currencyconverter.Messages;
import com.posadskiy.currencyconverter.enums.Currency;
import com.posadskiy.currencyconverter.exception.CurrencyConverterException;
import com.posadskiy.currencyconverter.util.JsonHelper;
import com.posadskiy.currencyconverter.util.NetworkUtils;

import java.io.IOException;
import java.util.Map;

/**
 * Service: CurrencyFreaks.Com
 * Website: https://currencyfreaks.com/
 */
public class CurrencyFreaksSource implements ConverterSource {
    public static final String SERVICE_NAME = "CurrencyFreaks.Com";
    public static final String DEFAULT_BASE_URL = "https://api.currencyfreaks.com";

    private final String baseUrl;

    public CurrencyFreaksSource() {
        this(DEFAULT_BASE_URL);
    }

    public CurrencyFreaksSource(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    @Override
    public Double rate(String apiKey, Currency from, Currency to) throws IOException {
        String url = getUrlString(apiKey);
        String collected = NetworkUtils.getBufferReaderByUrl(url, url.startsWith("https://"));

        try {
            Map<String, Object> json = JsonHelper.parseObject(collected);
            Map<String, Object> rates = JsonHelper.getObject(json, "rates");
            double fromRate = JsonHelper.getDouble(rates, from.name());
            double toRate = JsonHelper.getDouble(rates, to.name());
            return toRate / fromRate;
        } catch (Exception e) {
            throw new CurrencyConverterException(Messages.getServiceUnavailableMessage(SERVICE_NAME));
        }
    }

    private String getUrlString(String apiKey) {
        return baseUrl + "/latest?apikey=" + apiKey;
    }
}
