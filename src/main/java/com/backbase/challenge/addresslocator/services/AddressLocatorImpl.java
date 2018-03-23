package com.backbase.challenge.addresslocator.services;

import com.backbase.challenge.addresslocator.data.Constants;
import com.backbase.challenge.addresslocator.exception.AddressLocatorException;

import org.apache.camel.CamelContext;
import org.apache.camel.EndpointInject;
import org.apache.camel.Exchange;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.impl.DefaultExchange;
import org.apache.camel.impl.HashMapHeadersMapFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.URLDecoder;
import java.util.Map;


@RestController
@Component
public class AddressLocatorImpl implements AddressLocator {

	@EndpointInject(uri = Constants.DIRECT_GOOGLE)
	private ProducerTemplate producer;

	@EndpointInject(uri = Constants.DIRECT_XML2JSON)
	private ProducerTemplate marshalProducer;

	private static final Logger logger = LoggerFactory.getLogger(AddressLocatorImpl.class.getName());

	/**
	 * Here is our Rest Endpoint that handles the address string per the requirements
	 * @param addressString the string representing the address
	 * @return the Google GeocodeResponse represented as JSON
	 * @throws AddressLocatorException 
	 */
	@PostMapping(value= Constants.ENDPOINT_ADDRESS, produces = Constants.MEDIA_TYPE_JSON)
	public String locateAddressAndReturnAsJson(@RequestBody String addressString) throws AddressLocatorException {

		String addressAsStringFromGoogle;
		try {
			addressString = URLDecoder.decode(addressString, Constants.UTF_8);
			addressString = handleWebForm(addressString);
			CamelContext context = producer.getCamelContext();
			Exchange exchange = new DefaultExchange(context);
			exchange.getIn().setBody(addressString);
			Map<String, Object> headerMap = new HashMapHeadersMapFactory().newMap();
			headerMap.put("param1", addressString);
			exchange.getIn().setHeaders(headerMap);
			producer.send(exchange);
			addressAsStringFromGoogle = new String(((byte [])exchange.getProperty(Constants.RESULT)), Constants.UTF_8);
		} catch (Exception e) {
			logger.error("Error is {}", e);
			throw new AddressLocatorException(e);
		}
		return addressAsStringFromGoogle;
	}

	/**
	 * Ordinarily we'd build a structure for input, but there is just one line - so to
	 * keep it simple
	 * @param input the string to be checked
	 * @return the part after the equals sign - for web forms
	 */
	public String handleWebForm(String input) {
		int equalsLocation = input.indexOf('=');
		if(equalsLocation > 0 ) {
			input = input.substring(equalsLocation + 1);
		}
		return input;
	}
}
