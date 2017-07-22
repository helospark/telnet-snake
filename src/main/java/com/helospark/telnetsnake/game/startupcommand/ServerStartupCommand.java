package com.helospark.telnetsnake.game.startupcommand;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.beust.jcommander.JCommander;
import com.helospark.telnetsnake.game.parameters.StartupCommandParameters;
import com.helospark.telnetsnake.game.server.PortGameServerOrchestrator;
import com.helospark.telnetsnake.game.server.ShutdownListener;
import com.helospark.telnetsnake.game.server.SnakeGameServer;

@Component
public class ServerStartupCommand implements StartupCommand {
    @Autowired
    private PortGameServerOrchestrator portGameServer;
    @Autowired
    private ShutdownListener shutdownListener;

    @Override
    public void execute(Object commandObject) {
        StartupCommandParameters stopCommandParameters = (StartupCommandParameters) commandObject;
        shutdownListener.startListeningForShutdown();
        Thread thread = new Thread(new SnakeGameServer(portGameServer));
        thread.start();
    }

    @Override
    public boolean canHandle(JCommander jCommander) {
        return Objects.equals(jCommander.getParsedCommand(), StartupCommandParameters.COMMAND_NAME);
    }

    @Override
    public Object createCommandObject() {
        return new StartupCommandParameters();
    }
}
