package com.posadskiy.currencyconverter;

public class Messages {
	public static final String TRY_LATER = "Nothing working services. Please, try later again. If problem stay, create a ticket on github.com/posadskiy/currency-converter";
	public static final String FILL_API_KEY = "Should be filled at least one api key. See getting started on github.com/posadskiy/currency-converter";
	
	public static String getServiceUnavailableMessage(String serviceName) {
		return "Public API " + serviceName + " was changed. Please, create ticket on github.com/posadskiy/currency-converter or send e-mail to admin@posadskiy.com";
	}

	public static String getServiceProblemMessage(String serviceName) {
		return "Service " + serviceName + " has problem currently. Please, try again later. If problem still happens, create ticket on github.com/posadskiy/currency-converter or send e-mail to admin@posadskiy.com";
	}
	
	public static String getServiceNetworkErrorMessage(String serviceResponse) {
		return "Service unavailable. Response: " + serviceResponse;
	}
}
