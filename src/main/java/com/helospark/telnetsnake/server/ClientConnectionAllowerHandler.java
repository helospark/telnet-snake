package com.helospark.telnetsnake.server;

import java.util.concurrent.ExecutorService;

import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.helospark.telnetsnake.server.domain.ClientConnectionData;
import com.helospark.telnetsnake.server.game.SnakeGameOrchestrator;

@Component
public class ClientConnectionAllowerHandler {
    @Autowired
    @Lazy
    private ExecutorService executorService;
    @Autowired
    private SnakeGameOrchestrator snakeGameOrchestrator;

    public void connectClient(ClientConnectionData clientConnectionData) {
        executorService.execute(() -> {
            MDC.put("user_ip", clientConnectionData.clientIp);
            snakeGameOrchestrator.runSnakeGame(clientConnectionData.snakeIO);
        });
    }
}
