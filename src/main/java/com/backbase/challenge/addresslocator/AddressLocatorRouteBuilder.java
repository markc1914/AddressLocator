package com.backbase.challenge.addresslocator;

import com.backbase.challenge.addresslocator.data.Constants;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.dataformat.xmljson.XmlJsonDataFormat;
import org.springframework.stereotype.Component;

/**
 *
 * @author markc1914
 * @since 2/21/18
 */
@Component
public class AddressLocatorRouteBuilder extends RouteBuilder {

    /**
     * this is my first time using a Camel router. I'm guessing this controls all the
     * I/O formatting around requests, responses, etc
     *
     * @throws Exception
     */
    public void configure() throws Exception {

        XmlJsonDataFormat xmlJsonFormat = new XmlJsonDataFormat();
        xmlJsonFormat.setEncoding("UTF-8");
        xmlJsonFormat.setForceTopLevelObject(true);
        xmlJsonFormat.setTrimSpaces(false);
        xmlJsonFormat.setSkipWhitespace(false);

        from(Constants.DIRECT_GOOGLE)
                .setHeader(Exchange.HTTP_QUERY, simple("address=${header.param1}"))
                .setHeader(Exchange.HTTP_METHOD, constant("POST"))
                .to("http://maps.googleapis.com/maps/api/geocode/xml")
                .convertBodyTo(String.class)
                .setProperty(Constants.RESULT, simple(Constants.EXCHANGE_BODY))
                .to("log:com.backbase.challenge.addresslocator.XML"); // so you see the xml

        from(Constants.DIRECT_XML2JSON)
                .marshal(xmlJsonFormat)
                .setProperty(Constants.RESULT, simple(Constants.EXCHANGE_BODY))
                .convertBodyTo(String.class)
                .to("log:com.backbase.challenge.addresslocator.JSON");

        restConfiguration().setHost("localhost");
        restConfiguration().port(8888);

        //we are doing a post, so we need input and output
        rest(Constants.ENDPOINT_ADDRESS).produces(Constants.MEDIA_TYPE_JSON)
                .post().description("The Address Search endpoint")
                .to("bean:addresslocator?method=locateAddressAndReturnAsJson");

    }
}
