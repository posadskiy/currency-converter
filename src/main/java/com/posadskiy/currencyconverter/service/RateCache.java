package com.posadskiy.currencyconverter.service;

import com.posadskiy.currencyconverter.enums.Currency;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Thread-safe TTL-based cache for exchange rates.
 * Expired entries are evicted lazily on access — no background threads.
 */
public final class RateCache {

    private final long ttlMillis;
    private final ConcurrentHashMap<String, CachedRate> cache = new ConcurrentHashMap<>();

    public RateCache(long ttlMillis) {
        this.ttlMillis = ttlMillis;
    }

    public Double get(Currency from, Currency to) {
        String k = key(from, to);
        CachedRate entry = cache.get(k);
        if (entry == null) return null;
        if (System.currentTimeMillis() - entry.timestamp > ttlMillis) {
            cache.remove(k);
            return null;
        }
        return entry.rate;
    }

    public void put(Currency from, Currency to, double rate) {
        cache.put(key(from, to), new CachedRate(rate, System.currentTimeMillis()));
    }

    public void clear() {
        cache.clear();
    }

    private static String key(Currency from, Currency to) {
        return from.name() + "_" + to.name();
    }

    private static final class CachedRate {
        final double rate;
        final long timestamp;

        CachedRate(double rate, long timestamp) {
            this.rate = rate;
            this.timestamp = timestamp;
        }
    }
}
