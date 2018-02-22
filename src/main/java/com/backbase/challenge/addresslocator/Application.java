package com.backbase.challenge.addresslocator;

import com.backbase.challenge.addresslocator.data.Constants;
import org.apache.camel.component.servlet.CamelHttpTransportServlet;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;

/**
 *  Spring Boot Application Class
 */
@SpringBootApplication
@EnableAutoConfiguration
public class Application {

    /**
     * Main method to start the application.
     */
    public static void main(String[] args) throws Exception {
        SpringApplication.run(Application.class, args);
    }

    /**
     * Registers the Camel Servlet with Spring Boot
     * @return the registration for the Camel HTTP Servlet Bean
     */
    @Bean
    public ServletRegistrationBean camelServletRegistrationBean() {
        ServletRegistrationBean registration = new ServletRegistrationBean(new CamelHttpTransportServlet(), "/camel/*");
        registration.setName(Constants.SERVLET_NAME);
        return registration;
    }

}
