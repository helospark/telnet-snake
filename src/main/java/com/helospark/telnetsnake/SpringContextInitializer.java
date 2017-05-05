package com.helospark.telnetsnake;

import java.io.IOException;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringContextInitializer {
    private static ConfigurableApplicationContext context;

    public static void main(String[] args) throws IOException {
        initializeDIFramework();

        StartupExecutor startupExecutor = context.getBean(StartupExecutor.class);
        startupExecutor.start(args);
    }

    private static void initializeDIFramework() {
        context = new ClassPathXmlApplicationContext("classpath:spring-context.xml");
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                context.close();
            }
        });
    }
}
