package com.posadskiy.currencyconverter.enums;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CurrencyTest {

    @Test
    void findByCode_returnsPresentWhenFound() {
        assertThat(Currency.findByCode("USD")).isPresent();
    }

    @Test
    void findByCode_returnsEmptyWhenUnknown() {
        assertThat(Currency.findByCode("XXX")).isNotPresent();
    }
}

