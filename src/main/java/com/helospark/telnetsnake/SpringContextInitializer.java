package com.helospark.telnetsnake;

import static java.util.Arrays.asList;

import java.io.IOException;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.helospark.telnetsnake.startupcommand.StartupExecutor;

public class SpringContextInitializer {
    private static ApplicationContext context;

    public static void main(String[] args) throws IOException {
        initializeDIFramework();
        StartupExecutor daemon = context.getBean(StartupExecutor.class);
        daemon.start(asList(args));
    }

    private static void initializeDIFramework() {
        context = new ClassPathXmlApplicationContext("classpath:spring-context.xml");
    }
}
