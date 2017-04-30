package com.helospark.telnetsnake.startupcommand;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.helospark.telnetsnake.StartupCommand;
import com.helospark.telnetsnake.server.PortGameServerOrchestrator;
import com.helospark.telnetsnake.server.ShutdownListener;
import com.helospark.telnetsnake.server.SnakeGameServer;

@Component
public class ServerStartupCommand implements StartupCommand {
    @Autowired
    private PortGameServerOrchestrator portGameServer;
    @Autowired
    private ShutdownListener shutdownListener;

    @Override
    public void execute(List<String> args) {
        shutdownListener.startListeningForShutdown();
        Thread thread = new Thread(new SnakeGameServer(portGameServer));
        thread.start();
    }

    @Override
    public boolean canHandle(Optional<String> commandName) {
        return !commandName.isPresent() || commandName.get().equals("start");
    }
}
