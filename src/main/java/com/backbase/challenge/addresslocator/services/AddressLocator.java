package com.backbase.challenge.addresslocator.services;

import com.backbase.challenge.addresslocator.exception.AddressLocatorException;

public interface AddressLocator {

	public String locateAddressAndReturnAsJson (final String addressString) throws AddressLocatorException;
	
}
