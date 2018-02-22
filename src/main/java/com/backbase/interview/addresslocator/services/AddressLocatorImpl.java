package com.backbase.interview.addresslocator.services;

import org.apache.camel.*;
import org.apache.camel.impl.DefaultExchange;
import org.apache.camel.impl.HashMapHeadersMapFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Map;

import static com.backbase.interview.addresslocator.data.Constants.*;


@RestController
@Component("[addressLocator]")
public class AddressLocatorImpl implements AddressLocator {

	@EndpointInject(uri=DIRECT_GOOGLE)
	ProducerTemplate producer;

	@EndpointInject(uri= DIRECT_XML2JSON)
	ProducerTemplate marshalProducer;

	private String addressAsJson;
	private static final Logger logger = LoggerFactory.getLogger(AddressLocatorImpl.class.getName());

	@PostMapping(value= ENDPOINT_ADDRESS, produces = "application/json")
	public String locateAddressAndReturnAsJson(@RequestBody String addressString) {

		logger.info("Welcome to My Web Service");
		try {
			addressString = URLDecoder.decode(addressString,"UTF-8" );
			addressString = handleWebForm(addressString);
			CamelContext context = producer.getCamelContext();
			Exchange exchange = new DefaultExchange(context);
			exchange.getIn().setBody(addressString);
			Map<String, Object> headerMap = new HashMapHeadersMapFactory().newMap();
			headerMap.put("param1", addressString);
			exchange.getIn().setHeaders(headerMap);
			producer.send(exchange);
			addressAsJson = exchange.getProperty(RESULT).toString();
			marshalProducer.send(exchange);
			addressAsJson = new String(((byte [])exchange.getProperty(RESULT)), "UTF-8");
			logger.info("Response was ::: {}", addressAsJson);
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		return addressAsJson;
	}

	/**
	 * there's a better way to do this, but time is short
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
