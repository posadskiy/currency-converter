package com.posadskiy.currencyconverter.source;

import com.posadskiy.currencyconverter.Messages;
import com.posadskiy.currencyconverter.enums.Currency;
import com.posadskiy.currencyconverter.exception.CurrencyConverterException;
import com.posadskiy.currencyconverter.util.JsonHelper;
import com.posadskiy.currencyconverter.util.NetworkUtils;

import java.io.IOException;
import java.util.Map;

/**
 * CurrencyConverterApi.Com service
 */
public class CurrencyConverterApiSource implements ConverterSource {
    public static final String SERVICE_NAME = "CurrencyConverterApi.Com";
    public static final String DEFAULT_BASE_URL = "https://free.currconv.com";

    private final String baseUrl;

    public CurrencyConverterApiSource() {
        this(DEFAULT_BASE_URL);
    }

    public CurrencyConverterApiSource(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public Double rate(String apiKey, Currency from, Currency to) throws IOException {
        String url = getUrlString(apiKey, from, to);
        String collected = NetworkUtils.getBufferReaderByUrl(url, url.startsWith("https://"));

        try {
            Map<String, Object> json = JsonHelper.parseObject(collected);
            String key = from.name() + "_" + to.name();
            return JsonHelper.getDouble(json, key);
        } catch (Exception e) {
            throw new CurrencyConverterException(Messages.getServiceUnavailableMessage(SERVICE_NAME));
        }
    }

    private String getUrlString(String apiKey, Currency from, Currency to) {
        return baseUrl + "/api/v7/convert?q=" + from + "_" + to + "&compact=ultra&apiKey=" + apiKey;
    }
}
