package com.helospark.telnetsnake.game.server.configuration;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.helospark.lightdi.annotation.Bean;
import com.helospark.lightdi.annotation.Configuration;
import com.helospark.lightdi.annotation.Lazy;
import com.helospark.lightdi.annotation.Value;

@Configuration
@Lazy
public class ExecutorServiceConfiguration {
    @Value("${MAX_CONNECTIONS}")
    private int maxConnections;

    @Bean(name = "serverExecutorService")
    public ExecutorService createThreadPoolExecutor() {
        return Executors.newFixedThreadPool(maxConnections);
    }

}
