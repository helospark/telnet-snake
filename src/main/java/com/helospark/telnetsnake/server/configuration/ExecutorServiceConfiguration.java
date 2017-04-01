package com.helospark.telnetsnake.server.configuration;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ExecutorServiceConfiguration {
    @Value("${MAX_CONNECTIONS}")
    private int maxConnections;

    @Bean(name = "serverExecutorService")
    public ExecutorService createThreadPoolExecutor() {
        return Executors.newFixedThreadPool(maxConnections);
    }
}
