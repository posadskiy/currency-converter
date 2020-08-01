package com.posadskiy.currencyconverter.examples.android;

import android.os.AsyncTask;

import com.posadskiy.currencyconverter.CurrencyConverter;
import com.posadskiy.currencyconverter.config.ConfigBuilder;
import com.posadskiy.currencyconverter.enums.Currency;

class CurrencyConverterAsyncTask extends AsyncTask<String, Void, Double> {
    public static final String CURRENCY_CONVERTER_API_API_KEY = "YOUR_API_KEY";

    protected Double doInBackground(String... urls) {
        CurrencyConverter converter = new CurrencyConverter(
                new ConfigBuilder()
                        .currencyConverterApiApiKey(CURRENCY_CONVERTER_API_API_KEY)
                        .build()
        );

        return converter.rate(Currency.USD, Currency.EUR);
    }

    protected void onPostExecute(Double rate) {
        /*
        * Here you get access to exchanged rate value.
        */
        System.out.println(rate);
    }
}
