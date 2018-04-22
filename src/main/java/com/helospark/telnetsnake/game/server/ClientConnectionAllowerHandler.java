package com.helospark.telnetsnake.game.server;

import java.util.concurrent.ExecutorService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import com.helospark.lightdi.annotation.Autowired;
import com.helospark.lightdi.annotation.Component;
import com.helospark.telnetsnake.game.server.domain.ClientConnectionData;
import com.helospark.telnetsnake.game.server.game.SnakeGameOrchestrator;

@Component
public class ClientConnectionAllowerHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(ClientConnectionAllowerHandler.class);

    @Autowired
    private ExecutorService executorService;
    @Autowired
    private SnakeGameOrchestrator snakeGameOrchestrator;

    public void connectClient(ClientConnectionData clientConnectionData) {
        executorService.execute(() -> {
            try {
                MDC.put("user_ip", clientConnectionData.clientIp);
                snakeGameOrchestrator.runSnakeGame(clientConnectionData.snakeIO);
            } catch (Exception e) {
                LOGGER.error("Unhandled exception", e);
            }
        });
    }
}
