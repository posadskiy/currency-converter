<p align="center">
    <h2>Currency Converter</h2>
Java library recieves current exchange rate
<li>165+ currencies</li>
<li>No dependencies</li>
<li>Only 3 small classes - Currencies Enum, Request Service and API</li>
</p>

## Documentation

### Download

##### Maven
```xml
<dependency>
  <groupId>com.posadskiy</groupId>
  <artifactId>currency-converter</artifactId>
  <version>1.0.0</version>
</dependency>
```

##### Gradle/Grails

```xml
compile 'com.posadskiy:currency-converter:1.0.0'
```

##### Scala SBT
```scala
libraryDependencies += "com.posadskiy" % "currency-converter" % "1.0.0"
```

### Initialization

[How to get API Key?](#ApiKey)
```java
// Init converter with your API key
CurrencyConverter converter = new CurrencyConverter(apiKey);
```

### Working
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

##### ApiKey
Sing up on website [Currency Converter Api](https://currencyconverterapi.com) and get key for use this service.
API key looks like *114ccb23bhhewr732sdc*

## License

Currency Converter is available under the [MIT license](https://opensource.org/licenses/MIT).
