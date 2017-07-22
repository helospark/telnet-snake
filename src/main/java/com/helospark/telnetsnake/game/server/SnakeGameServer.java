package com.helospark.telnetsnake.game.server;

public class SnakeGameServer implements Runnable {
    private final PortGameServerOrchestrator portGameServerOrchestrator;

    public SnakeGameServer(PortGameServerOrchestrator portGameServer) {
        this.portGameServerOrchestrator = portGameServer;
    }

    @Override
    public void run() {
        portGameServerOrchestrator.start();
    }

}