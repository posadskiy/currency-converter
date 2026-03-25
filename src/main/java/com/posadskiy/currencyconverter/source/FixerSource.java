package com.posadskiy.currencyconverter.source;

import com.posadskiy.currencyconverter.Messages;
import com.posadskiy.currencyconverter.enums.Currency;
import com.posadskiy.currencyconverter.exception.CurrencyConverterException;
import com.posadskiy.currencyconverter.util.JsonHelper;
import com.posadskiy.currencyconverter.util.NetworkUtils;

import java.io.IOException;
import java.util.Map;

/**
 * Service: Fixer.io
 * Website: https://fixer.io/
 */
public class FixerSource implements ConverterSource {
    public static final String SERVICE_NAME = "Fixer.io";
    public static final String DEFAULT_BASE_URL = "https://api.apilayer.com";

    private final String baseUrl;

    public FixerSource() {
        this(DEFAULT_BASE_URL);
    }

    public FixerSource(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    @Override
    public Double rate(String apiKey, Currency from, Currency to) throws IOException {
        String url = getUrlString(apiKey, from, to);
        String collected = NetworkUtils.getBufferReaderByUrl(url, url.startsWith("https://"));

        try {
            Map<String, Object> json = JsonHelper.parseObject(collected);
            return JsonHelper.getDouble(json, "result");
        } catch (Exception e) {
            throw new CurrencyConverterException(Messages.getServiceUnavailableMessage(SERVICE_NAME));
        }
    }

    private String getUrlString(String apiKey, Currency from, Currency to) {
        return baseUrl + "/fixer/convert?from=" + from + "&to=" + to + "&amount=1&apikey=" + apiKey;
    }
}
