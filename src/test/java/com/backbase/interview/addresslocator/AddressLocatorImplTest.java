package com.backbase.interview.addresslocator;

import org.junit.Test;

import com.backbase.interview.addresslocator.services.AddressLocatorImpl;

import junit.framework.Assert;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class AddressLocatorImplTest {
	
	private AddressLocatorImpl addressLocator = new AddressLocatorImpl();

	
	@Test
	public void testAddressLocator_ShouldReturnCorrectResult() throws Exception {
		String testString = addressLocator.handleWebForm("address=925 Centinela Ave");
		assertEquals("String should Match", "925 Centinela Ave", testString);
		testString = addressLocator.handleWebForm("925 Centinela Ave");
		assertEquals("String should Match", "925 Centinela Ave", testString);
	}
}
