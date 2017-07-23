package com.helospark.telnetsnake.it.configuration;

import static org.mockito.Mockito.mock;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import com.helospark.telnetsnake.game.output.ScreenWriter;
import com.helospark.telnetsnake.game.server.game.IpExtractor;
import com.helospark.telnetsnake.game.startupcommand.help.SocketFactory;

/**
 * Common configuration of overriding some beans for IT.
 * @author helospark
 */
@Configuration
public class BaseMockOverrideConfiguration {

    @Bean
    @Primary
    public IpExtractor ipExtractor() {
        return mock(IpExtractor.class);
    }

    @Bean
    @Primary
    public SocketFactory socketFactory() {
        return mock(SocketFactory.class);
    }

    @Bean
    @Primary
    public ScreenWriter screenWriter() {
        return mock(ScreenWriter.class);
    }

}
