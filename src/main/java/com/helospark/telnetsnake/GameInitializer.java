package com.helospark.telnetsnake;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.helospark.telnetsnake.server.SnakeGameServer;
import com.helospark.telnetsnake.server.PortGameServerOrchestrator;

@Component
public class GameInitializer {
    @Autowired
    private PortGameServerOrchestrator portGameServer;

    @PostConstruct
    public void initializeGame() {
        Thread thread = new Thread(new SnakeGameServer(portGameServer));
        thread.start();
    }
}
