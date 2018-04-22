package com.helospark.telnetsnake.game;

import com.helospark.lightdi.annotation.ComponentScan;
import com.helospark.lightdi.annotation.Configuration;
import com.helospark.lightdi.annotation.PropertySource;

@Configuration
@PropertySource(value = "file:/etc/telnet-snake/config.properties", order = -1, ignoreResourceNotFound = true)
@PropertySource(value = "classpath:default_settings.properties", order = 0, ignoreResourceNotFound = false)
@ComponentScan
public class ApplicationConfiguration {

}
