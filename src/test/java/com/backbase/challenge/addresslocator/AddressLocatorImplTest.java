package com.backbase.challenge.addresslocator;

import com.backbase.challenge.addresslocator.services.AddressLocatorImpl;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * The only thing my code does is manipulate the form data, so this test is simple
 */
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
