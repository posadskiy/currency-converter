package com.posadskiy.currencyconverter.enums;

/**
 * Test-only enum mapping provider names to their property/environment-variable key prefixes.
 * Not shipped in the production JAR.
 */
public enum Source {
    CURRENCY_CONVERTER_API,
    CURRENCY_LAYER,
    OPEN_EXCHANGE_RATES,
    FIXER,
    CURRENCY_FREAKS,
}
