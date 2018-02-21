package com.backbase.interview.addressLocator;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.servlet.CamelHttpTransportServlet;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Application {

    private static final String SERVLET_NAME = "AddressLocatorCamelServlet";

    /**
     * Main method to start the application.
     */
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public ServletRegistrationBean camelServletRegistrationBean() {
        ServletRegistrationBean registration = new ServletRegistrationBean(new CamelHttpTransportServlet(), "/camel/*");
        registration.setName(SERVLET_NAME);
        return registration;
    }

    class GoogleAddressRoute extends RouteBuilder {


        /**
         * this is my first time using a Camel router. I'm guessing this controls all the
         * I/O formatting around requests, responses, etc
         *
         * @throws Exception
         */
        public void configure() throws Exception {

            restConfiguration()
                    .component("servlet")
                    .bindingMode(RestBindingMode.json)
                    .dataFormatProperty("prettyPrint", "true")
                    .apiContextPath("/api-doc")
                    .apiProperty("api.title", "Mark's Google Address Service API")
                    .apiProperty("api.version", "1.0.0")
                    .apiProperty("cors", "true");

            //we are doing a post, so we need input and output
            rest("/getAddress").produces("application/json")


                    //define our HTTP Post (address input from our user)
                    .post().description("The Address Search endpoint")
                    .to("bean:addressLocator?method=locateAddressAndReturnAsJson");

            from("bean:addressLocator?method=locateAddressAndReturnAsJson")
                    .setHeader(Exchange.HTTP_QUERY).simple("id=${body}")
                    .to("http://maps.googleapis.com/maps/api/geocode/xml")
            ;
        }
    }
}
