package com.helospark.telnetsnake;

import java.io.IOException;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringContextInitializer {
    private static ApplicationContext context;

    public static void main(String[] args) throws IOException {
        initializeDIFramework();
        GameInitializer daemon = context.getBean(GameInitializer.class);
        daemon.initializeGame();
    }

    private static void initializeDIFramework() {
        context = new ClassPathXmlApplicationContext("classpath:spring-context.xml");
    }
}
