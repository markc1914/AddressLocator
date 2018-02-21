package com.backbase.interview.addressLocator;

import org.junit.Test;

import com.backbase.interview.addressLocator.services.AddressLocatorImpl;

import junit.framework.Assert;

public class AddressLocatorImplTest {
	
	private final String expectedResultAsString = "{ \"formatted_address\": \"925 Centinela Ave, Inglewood, CA 90302, USA\",\"lat\": 33.9757736,\"lng\": -118.3561935}";
	private AddressLocatorImpl addressLocator = new AddressLocatorImpl();
	
	
	@Test
	public void testAddressLocator_ShouldReturnCorrectResult() throws Exception {
		Assert.fail();
	}
}
