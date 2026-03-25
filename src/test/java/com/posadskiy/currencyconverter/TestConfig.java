package com.posadskiy.currencyconverter;

import static com.posadskiy.currencyconverter.util.PropertiesLoader.getFromProperties;

public final class TestConfig {
    public static final String RUN_EXTERNAL_TESTS_KEY = "RUN_EXTERNAL_TESTS";
    private TestConfig() {
    }

    public static boolean runExternalTests() {
        String sys = System.getProperty(RUN_EXTERNAL_TESTS_KEY);
        // If the property is explicitly provided, it must override the environment variable.
        // This allows reliable control via:
        //   mvn test -DRUN_EXTERNAL_TESTS=false
        if (sys != null) return isTrue(sys);

        String env = System.getenv(RUN_EXTERNAL_TESTS_KEY);
        if (env != null) return isTrue(env);

        return isTrue(getFromProperties(RUN_EXTERNAL_TESTS_KEY));
    }

    public static boolean isConfigured(String apiKey) {
        if (apiKey == null) return false;
        String normalized = apiKey.trim();
        if (normalized.isEmpty()) return false;
        return !normalized.toUpperCase().contains("YOUR");
    }

    private static boolean isTrue(String value) {
        return value != null && value.trim().equalsIgnoreCase("true");
    }
}

