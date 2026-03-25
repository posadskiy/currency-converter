package com.posadskiy.currencyconverter.util;

import com.posadskiy.currencyconverter.Messages;
import com.posadskiy.currencyconverter.enums.Source;
import com.posadskiy.currencyconverter.exception.CurrencyConverterException;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Logger;

/**
 * Test-only helper for loading provider API keys from system properties, environment variables,
 * or a classpath {@code application.properties} file.
 *
 * <p>Production code should use {@code ConfigBuilder}/{@code Config} directly.</p>
 */
public class PropertiesLoader {
    private static final Logger log = Logger.getLogger(PropertiesLoader.class.getName());
    private static final String DEFAULT_PROPERTIES_RESOURCE = "application.properties";

    public static Properties loadProperties(String resourceFileName) throws IOException {
        Properties configuration = new Properties();
        try (InputStream inputStream = PropertiesLoader.class
                .getClassLoader()
                .getResourceAsStream(resourceFileName)) {
            if (inputStream == null) {
                throw new CurrencyConverterException("Properties resource not found on classpath: " + resourceFileName);
            }
            configuration.load(inputStream);
        }
        return configuration;
    }

    public static String getFromProperties(String key) {
        try {
            return loadProperties(DEFAULT_PROPERTIES_RESOURCE).getProperty(key);
        } catch (IOException e) {
            log.warning(Messages.getPropertyLoadErrorMessage(key));
            throw new CurrencyConverterException(Messages.getPropertyLoadErrorMessage(key));
        }
    }

    public static String getApiKey(Source source) {
        // Customer-friendly lookup order:
        // 1) -D<KEY>=... (System property)
        // 2) <KEY>=...  (Environment variable)
        // 3) application.properties on the classpath
        String key = source + "_API_KEY";

        String value = System.getProperty(key);
        if (isNonBlank(value)) return value;

        value = System.getenv(key);
        if (isNonBlank(value)) return value;

        return getFromProperties(key);
    }

    private static boolean isNonBlank(String value) {
        return value != null && !value.trim().isEmpty();
    }
}

