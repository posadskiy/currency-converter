# Currency Converter for Java

[![Maven Central](https://img.shields.io/maven-central/v/com.posadskiy/currency-converter.svg?label=Maven%20Central)](https://central.sonatype.com/artifact/com.posadskiy/currency-converter)
[![CI](https://github.com/posadskiy/currency-converter/actions/workflows/ci.yml/badge.svg)](https://github.com/posadskiy/currency-converter/actions/workflows/ci.yml)
[![codecov](https://codecov.io/gh/posadskiy/currency-converter/graph/badge.svg)](https://codecov.io/gh/posadskiy/currency-converter)
[![javadoc](https://javadoc.io/badge2/com.posadskiy/currency-converter/javadoc.svg)](https://javadoc.io/doc/com.posadskiy/currency-converter)
[![Java](https://img.shields.io/badge/Java-8%2B-blue.svg)](https://www.java.com)
[![License: MIT](https://img.shields.io/badge/License-MIT-green.svg)](https://opensource.org/licenses/MIT)
[![GitHub Stars](https://img.shields.io/github/stars/posadskiy/currency-converter?style=social)](https://github.com/posadskiy/currency-converter/stargazers)

A **zero-dependency** Java library that fetches real-time currency exchange rates from multiple providers. Works on **Java 8 through Java 25+** with automatic runtime optimisations on newer JVMs.

---

## Features

- **165+ currencies** — full ISO 4217 coverage via the `Currency` enum
- **5 providers** with automatic failover — if one API is down or rate-limited, the next one is tried transparently
- **Zero runtime dependencies** — ships as a plain JAR with no transitive dependencies to manage
- **Multi-Release JAR** — runs on Java 8+; automatically uses `HttpClient` (Java 11+) and virtual threads (Java 21+) when available
- **Parallel queries on Java 21+** — all configured providers are queried simultaneously; you get the fastest response instead of waiting for sequential failures
- **Opt-in rate caching** — built-in thread-safe TTL cache reduces API calls and improves latency
- **Immutable configuration** — `Config` is immutable and thread-safe, built via `ConfigBuilder`
- **Input validation** — null currencies and missing API keys fail fast with clear error messages
- **JPMS-ready** — `Automatic-Module-Name` manifest entry for clean module-path usage
- **Android-compatible** — Java 8 bytecode base works with Android (see [Android section](#android))
- **Type-safe API** — currency codes are an enum, not magic strings

---

## Quick Start

Add the dependency:

**Maven**
```xml
<dependency>
    <groupId>com.posadskiy</groupId>
    <artifactId>currency-converter</artifactId>
    <version>1.4.0</version>
</dependency>
```

**Gradle**
```groovy
implementation 'com.posadskiy:currency-converter:1.4.0'
```

**Scala SBT**
```scala
libraryDependencies += "com.posadskiy" % "currency-converter" % "1.4.0"
```

Initialise and use:

```java
CurrencyConverter converter = new CurrencyConverter(
    new ConfigBuilder()
        .openExchangeRatesApiKey("YOUR_KEY")
        .build()
);

double rate = converter.rate(Currency.USD, Currency.EUR);
System.out.println("USD -> EUR: " + rate);
```

---

## Supported Exchange Rate Providers

| Provider | Website | Free tier |
|----------|---------|-----------|
| CurrencyLayer | [currencylayer.com](https://currencylayer.com/) | Yes       |
| OpenExchangeRates | [openexchangerates.org](https://openexchangerates.org/) | Yes       |
| Fixer | [fixer.io](https://fixer.io/) | Yes       |
| CurrencyFreaks | [currencyfreaks.com](https://currencyfreaks.com/) | Yes       |
| CurrencyConverterApi | [currencyconverterapi.com](https://www.currencyconverterapi.com/) | No        |

All providers offer a free API key — sign up on their websites to obtain one.

**Recommendation:** configure at least two providers. The library automatically falls back to the next one if a provider is unavailable. On Java 21+ all providers are queried in parallel and the first successful response wins.

---

## Usage

### Initialise with multiple providers (recommended)

```java
CurrencyConverter converter = new CurrencyConverter(
    new ConfigBuilder()
        .currencyConverterApiApiKey("KEY_1")
        .currencyLayerApiKey("KEY_2")
        .openExchangeRatesApiKey("KEY_3")
        .fixerApiKey("KEY_4")
        .currencyFreaksApiKey("KEY_5")
        .build()
);
```

### Get exchange rates

```java
// Any two currencies by enum (type-safe, IDE-autocomplete)
double rate = converter.rate(Currency.GBP, Currency.JPY);

// Convenience methods for common pairs
double usdToEur = converter.rateFromUsdToEuro();
double eurToUsd = converter.rateFromEuroToUsd();

// From/to a fixed currency
double usdToAnything = converter.rateFromUsd(Currency.CHF);
double anythingToUsd = converter.rateToUsd(Currency.BRL);

// String-based (for dynamic input)
double dynamic = converter.rate("USD", "EUR");
```

### Enable rate caching

Exchange rates don't change every millisecond — caching avoids redundant API calls and speeds up repeated lookups. Caching is **off by default** and must be enabled explicitly:

```java
CurrencyConverter converter = new CurrencyConverter(
    new ConfigBuilder()
        .openExchangeRatesApiKey("YOUR_KEY")
        .cacheTtlSeconds(300) // cache rates for 5 minutes
        .build()
);
```

The cache is thread-safe (`ConcurrentHashMap`-backed) and entries expire automatically after the configured TTL. Set `cacheTtlSeconds(0)` or omit the call to disable caching.

### All 165+ supported currencies

The `Currency` enum contains every ISO 4217 code: `AED`, `AFN`, `ALL`, `AMD`, `ARS`, `AUD`, `BRL`, `BTC`, `CAD`, `CHF`, `CNY`, `EUR`, `GBP`, `HKD`, `INR`, `JPY`, `KRW`, `MXN`, `NOK`, `NZD`, `PLN`, `RUB`, `SEK`, `SGD`, `THB`, `TRY`, `UAH`, `USD`, `ZAR`, and many more.

---

## Runtime Behaviour by Java Version

This library is packaged as a **Multi-Release JAR**. The JVM picks the best implementation automatically — no code changes needed on your side.

| JVM version | `NetworkUtils` | `CurrencyConvertService` |
|-------------|----------------|--------------------------|
| Java 8-10 | `HttpURLConnection` (10 s timeouts) | Sequential fallback |
| Java 11-20 | `HttpClient` (connection pooling, HTTP/2) | Sequential fallback |
| Java 21+ | `HttpClient` | **Parallel** (virtual threads, `invokeAny`) |

On Java 21+, all configured providers are queried simultaneously on virtual threads. The response time equals the fastest responding provider instead of accumulating the latency of any failed ones.

---

## Getting API Keys

1. Sign up for free on any of the [supported providers](#supported-exchange-rate-providers).
2. Copy the API key from your account dashboard.
3. Pass it to `ConfigBuilder` — you only need one to get started.

---

## Architecture

```
src/
├── main/
│   ├── java/          # Java 8 base layer (runs on all JVMs)
│   │   └── com/posadskiy/currencyconverter/
│   │       ├── CurrencyConverter.java   # public entry point + optional cache
│   │       ├── config/                  # immutable Config + ConfigBuilder
│   │       ├── enums/Currency.java      # ISO 4217 enum
│   │       ├── service/                 # sequential fallback + RateCache
│   │       ├── source/                  # provider adapters (JSON-parsed)
│   │       └── util/                    # NetworkUtils, JsonHelper
│   ├── java11/        # Java 11 override: HttpClient-based NetworkUtils
│   └── java21/        # Java 21 override: virtual-thread CurrencyConvertService
└── test/
    └── java/          # Unit + integration tests (HttpServer stubs)
```

Key design decisions:
- **Zero dependencies** — a lightweight built-in JSON parser (`JsonHelper`) avoids pulling in Jackson/Gson
- **Immutable config** — `Config` has no setters; use `ConfigBuilder` for construction
- **Thread safety** — all shared state is immutable or `ConcurrentHashMap`-backed
- **Fail-fast validation** — null currencies and invalid configs throw immediately

---

## Android

Android prohibits network calls on the main thread. Wrap the `CurrencyConverter` call in a background thread (e.g. `AsyncTask`, coroutine, or `ExecutorService`):

```java
new Thread(() -> {
    double rate = converter.rate(Currency.USD, Currency.EUR);
    runOnUiThread(() -> textView.setText(String.valueOf(rate)));
}).start();
```

Also add the Internet permission to `AndroidManifest.xml`:

```xml
<uses-permission android:name="android.permission.INTERNET"/>
```

---

## Contributing

Contributions are welcome! Here is how to get started:

1. Fork the repository and create a branch: `git checkout -b feat/your-feature`
2. Make your changes and add tests
3. Build and verify: `mvn clean verify`
4. Open a Pull Request — describe what you changed and why

### Adding a new exchange rate provider

1. Implement `ConverterSource` (one method: `Double rate(String apiKey, Currency from, Currency to)`)
2. Register the new source in `CurrencyConvertService.buildEntries()` and the Java 21 `buildTasks()`
3. Add an integration test in `src/test`

### Building locally

```bash
# Requires JDK 21+ to compile all MR-JAR layers
mvn clean package

# Run all tests (deterministic — HTTP stubs, no real API calls)
mvn clean verify

# Run with real API keys (optional)
RUN_EXTERNAL_TESTS=true mvn clean verify -DCURRENCY_LAYER_KEY=your_key ...
```

---

## License

Currency Converter is available under the [MIT License](https://opensource.org/licenses/MIT).
