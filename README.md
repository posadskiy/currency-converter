## Currency Converter

Java library receives current exchange rate
* 165+ currencies
* No dependencies

### Supported sources
* [CurrencyConverterApi](https://www.currencyconverterapi.com/)
* [CurrencyLayer](https://currencylayer.com/)
* [OpenExchangeRates](https://openexchangerates.org/)
* [Fixer](https://fixer.io/)
* [CurrencyFreaks](https://currencyfreaks.com/)

## Documentation

### Download

##### Maven
```xml
<dependency>
  <groupId>com.posadskiy</groupId>
  <artifactId>currency-converter</artifactId>
  <version>1.3.0</version>
</dependency>
```

##### Gradle/Grails

```xml
compile 'com.posadskiy:currency-converter:1.3.0'
```

##### Scala SBT
```scala
libraryDependencies += "com.posadskiy" % "currency-converter" % "1.3.0"
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

Only one service using (recommended using at least two, it helps when one is unavailable)
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
Sign up on websites you are going to use:
 - [Currency Converter Api](https://currencyconverterapi.com)
 - [Currency Layer](https://currencylayer.com/)
 - [Open Exchange Rates](https://openexchangerates.org/)
 - [Fixer](https://fixer.io/)
 - [CurrencyFreaks](https://currencyfreaks.com/)
 
and get key for use this services.

API key looks like symbolic string.

## Android
The working process for Android is different from the usual Java application.
Network requests from the main tread are prohibited for Android, so a possible way to call Currency Converter library is by creating AsyncTask wrapper.
See [Examples](https://github.com/posadskiy/currency-converter/tree/master/examples/android).

Also, it should be noted, Android application should have permissions to interaction with Network.
Add to AndroidManifest.xml:
 ```xml
<uses-permission android:name="android.permission.INTERNET"/>
```

[AsyncTask in Android](https://developer.android.com/reference/android/os/AsyncTask)

## License

Currency Converter is available under the [MIT license](https://opensource.org/licenses/MIT).

## Powered by

![Intellij IDEA](view-dependencies/intellij-idea.png)
