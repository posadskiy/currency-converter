<p align="center">
    <h2>Currency Converter</h2>
Java library receives current exchange rate
<li>165+ currencies</li>
<li>No dependencies</li>
<li>Supports 3 sources - CurrencyConverterApi.Com, CurrencyLayer.Com, OpenExchangeRates.Com</li>
</p>

## Documentation

### Download

##### Maven
```xml
<dependency>
  <groupId>com.posadskiy</groupId>
  <artifactId>currency-converter</artifactId>
  <version>1.1.2</version>
</dependency>
```

##### Gradle/Grails

```xml
compile 'com.posadskiy:currency-converter:1.1.2'
```

##### Scala SBT
```scala
libraryDependencies += "com.posadskiy" % "currency-converter" % "1.1.2"
```

### How to init service

[How to get API Key?](#ApiKey)
```java
/**
 * Define your API keys
 * Use at least one key, but we recommend define three:
 * One or even two services can be off, but not three at the same time
*/
public static final String CURRENCY_CONVERTER_API_API_KEY = "YOUR API KEY FOR CurrencyConverterApi.Com";
public static final String CURRENCY_LAYER = "YOUR API KEY FOR CurrencyLayer.Com";
public static final String OPEN_EXCHANGE_RATES = "YOUR API KEY FOR OpenExchangeRates.Com";

// Init converter with your API key
CurrencyConverter converter = new CurrencyConverter(
    new ConfigBuilder()
        .currencyConverterApiApiKey(CURRENCY_CONVERTER_API_API_KEY)
        .currencyLayerApiKey(CURRENCY_LAYER)
        .openExchangeRatesApiKey(OPEN_EXCHANGE_RATES)
        .build()
);
```

Sure, you are able to use only one service:
```java
public static final String OPEN_EXCHANGE_RATES = "YOUR API KEY FOR OpenExchangeRates.Com";

CurrencyConverter converter = new CurrencyConverter(
    new ConfigBuilder()
        .openExchangeRatesApiKey(OPEN_EXCHANGE_RATES)
        .build()
);
```

### How it works
```java
// Get rate with Currency Enum
Double gbrToChfRate = converter.rate(Currency.GBP, Currency.CHF);

// Get rate with half fixed method
Double usdToEuroRate = converter.rateFromUsd(Currency.EUR);

// Get rate with fixed method
Double usdToEuroRate = converter.rateFromUsdToEuro();

// Get rate with String parameters
Double usdToEuroRate = converter.rate("USD", "EUR");
```

#### ApiKey
Sing up on websites you are going to use:
 - [Currency Converter Api](https://currencyconverterapi.com)
 - [Currency Layer](https://currencylayer.com/)
 - [Open Exchange Rates](https://openexchangerates.org/)
 
and get key for use this services.

API key looks like symbolic string.

## License

Currency Converter is available under the [MIT license](https://opensource.org/licenses/MIT).
