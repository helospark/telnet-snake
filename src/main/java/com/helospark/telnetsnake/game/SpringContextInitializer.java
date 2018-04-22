package com.helospark.telnetsnake.game;

import java.io.IOException;

import com.helospark.lightdi.LightDi;
import com.helospark.lightdi.LightDiContext;

public class SpringContextInitializer {
    private static LightDiContext context;

    public static void main(String[] args) throws IOException {
        initializeDIFramework();

        StartupExecutor startupExecutor = context.getBean(StartupExecutor.class);
        startupExecutor.start(args);
    }

    private static void initializeDIFramework() {
        context = LightDi.initContextByClass(ApplicationConfiguration.class);
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                context.close();
            }
        });
    }
}
