package com.backbase.challenge.addresslocator.data;

/**
 *
 * Constants so we don't have duplicated literals
 * @author markc1914
 * @since 2/21/18
 */
public interface Constants {
    String ENDPOINT_ADDRESS = "/validateAddress";
    String MEDIA_TYPE_JSON = "application/json";
    String SERVLET_NAME = "AddressLocatorCamelServlet";
    String DIRECT_GOOGLE = "direct://google";
    String DIRECT_XML2JSON = "direct://xml2json";
    String RESULT = "result";
    String EXCHANGE_BODY = "${body}";
    String UTF_8 = "UTF-8";
}
