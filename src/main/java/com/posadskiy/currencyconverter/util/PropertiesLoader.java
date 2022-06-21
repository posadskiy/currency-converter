package com.posadskiy.currencyconverter.util;

import com.posadskiy.currencyconverter.Messages;
import com.posadskiy.currencyconverter.enums.Source;
import com.posadskiy.currencyconverter.exception.CurrencyConverterException;
import com.posadskiy.currencyconverter.service.CurrencyConvertService;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Logger;

public class PropertiesLoader {
    private static final Logger log = Logger.getLogger(PropertiesLoader.class.getName());

    public static Properties loadProperties(String resourceFileName) throws IOException {
        Properties configuration = new Properties();
        InputStream inputStream = PropertiesLoader.class
            .getClassLoader()
            .getResourceAsStream(resourceFileName);
        configuration.load(inputStream);
        inputStream.close();
        return configuration;
    }
    
    public static String getFromProperties(String key) {
        try {
            return loadProperties("application.properties").getProperty(key);
        } catch (IOException e) {
            log.warning(Messages.getPropertyLoadErrorMessage(key));
            throw new CurrencyConverterException(Messages.getPropertyLoadErrorMessage(key));
        }
    }
    
    public static String getApiKey(Source source) {
        return getFromProperties(source + "_KEY");
    }
    
}
