package com.posadskiy.currencyconverter.service;

import com.posadskiy.currencyconverter.enums.Currency;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class RateCacheTest {

    @Test
    void get_returnsNull_whenEmpty() {
        RateCache cache = new RateCache(60_000);
        assertThat(cache.get(Currency.USD, Currency.EUR)).isNull();
    }

    @Test
    void put_thenGet_returnsCachedValue() {
        RateCache cache = new RateCache(60_000);
        cache.put(Currency.USD, Currency.EUR, 0.85);
        assertThat(cache.get(Currency.USD, Currency.EUR)).isEqualTo(0.85);
    }

    @Test
    void get_returnsNull_afterTtlExpires() throws InterruptedException {
        RateCache cache = new RateCache(50);
        cache.put(Currency.USD, Currency.EUR, 0.85);
        Thread.sleep(100);
        assertThat(cache.get(Currency.USD, Currency.EUR)).isNull();
    }

    @Test
    void differentPairs_areCachedSeparately() {
        RateCache cache = new RateCache(60_000);
        cache.put(Currency.USD, Currency.EUR, 0.85);
        cache.put(Currency.GBP, Currency.JPY, 190.0);

        assertThat(cache.get(Currency.USD, Currency.EUR)).isEqualTo(0.85);
        assertThat(cache.get(Currency.GBP, Currency.JPY)).isEqualTo(190.0);
    }

    @Test
    void clear_removesAllEntries() {
        RateCache cache = new RateCache(60_000);
        cache.put(Currency.USD, Currency.EUR, 0.85);
        cache.clear();
        assertThat(cache.get(Currency.USD, Currency.EUR)).isNull();
    }
}
