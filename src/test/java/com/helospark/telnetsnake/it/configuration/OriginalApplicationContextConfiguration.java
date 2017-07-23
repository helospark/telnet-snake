package com.helospark.telnetsnake.it.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

/**
 * Starting original application context via configuration class instead of XML for IT.
 * @author helospark
 */
@Configuration
@ImportResource("classpath:spring-context.xml")
public class OriginalApplicationContextConfiguration {

}
