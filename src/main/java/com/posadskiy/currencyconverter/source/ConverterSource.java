package com.posadskiy.currencyconverter.source;

import com.posadskiy.currencyconverter.enums.Currency;

import java.io.IOException;

public interface ConverterSource {
	Double rate(String apiKey, Currency from, Currency to) throws IOException;
}
