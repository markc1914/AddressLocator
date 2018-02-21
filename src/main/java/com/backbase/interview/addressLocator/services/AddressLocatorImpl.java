package com.backbase.interview.addressLocator.services;

import org.apache.camel.Exchange;
import org.apache.camel.Header;
import org.springframework.stereotype.Service;

@Service("addressLocator")
public class AddressLocatorImpl implements AddressLocator {

	public String locateAddressAndReturnAsJson(String addressString) {
		// TODO Auto-generated method stub
		return null;
	}

	private Object getAddressFromGoogle(@Header("address") String address, Exchange exchange) {
        exchange.getIn().setBody(address);
        return null;
	}
}
