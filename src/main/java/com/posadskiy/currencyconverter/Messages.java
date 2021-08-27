package com.posadskiy.currencyconverter;

public class Messages {
	public static final String TRY_LATER = "No working services at the moment. Try again later. If the problem stays, create a ticket on github.com/posadskiy/currency-converter.";
	public static final String FILL_API_KEY = "Fill in at least one API key. See getting started instruction on github.com/posadskiy/currency-converter.";
	
	public static String getServiceUnavailableMessage(String serviceName) {
		return serviceName + " public API was changed. Please, create ticket on github.com/posadskiy/currency-converter.";
	}

	public static String getServiceProblemMessage(String serviceName) {
		return "Service " + serviceName + " has problem now. Please, try again later. If the problem stays, create ticket on github.com/posadskiy/currency-converter.";
	}
	
	public static String getServiceNetworkErrorMessage(String serviceResponse) {
		return "Service unavailable. Response: " + serviceResponse;
	}
}
