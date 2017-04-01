package com.helospark.telnetsnake.server;

import java.util.concurrent.ExecutorService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.helospark.telnetsnake.game.SnakeGameOrchestrator;
import com.helospark.telnetsnake.server.domain.ClientConnectionData;

@Component
public class ClientConnectionAllowerHandler {
    @Autowired
    private ExecutorService executorService;
    @Autowired
    private SnakeGameOrchestrator snakeGameOrchestrator;

    public void connectClient(ClientConnectionData clientConnectionData) {
        executorService.execute(() -> snakeGameOrchestrator.runSnakeGame(clientConnectionData.snakeIO));
    }
}
