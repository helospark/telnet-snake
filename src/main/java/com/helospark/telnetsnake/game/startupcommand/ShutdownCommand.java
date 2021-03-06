package com.helospark.telnetsnake.game.startupcommand;

import java.net.Socket;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.helospark.lightdi.annotation.Autowired;
import com.helospark.lightdi.annotation.Value;
import com.helospark.lightdi.annotation.Component;

import com.beust.jcommander.JCommander;
import com.helospark.telnetsnake.game.output.ScreenWriter;
import com.helospark.telnetsnake.game.parameters.StopCommandParameters;
import com.helospark.telnetsnake.game.startupcommand.help.SocketFactory;

@Component
public class ShutdownCommand implements StartupCommand {
    private static final Logger LOGGER = LoggerFactory.getLogger(ShutdownCommand.class);
    @Value("${SHUTDOWN_PORT}")
    private int shutdownPort;
    @Autowired
    private SocketFactory socketFactory;
    @Autowired
    private ScreenWriter screenWriter;

    @Override
    public void execute(Object commandObject) {
        StopCommandParameters stopCommandParameters = (StopCommandParameters) commandObject;
        try (Socket shutdownSocket = socketFactory.createSocket()) {
            shutdownSocket.connect(socketFactory.createInetSocketAddress("localhost", shutdownPort));
        } catch (Exception e) {
            LOGGER.error("Shutdown failed", e);
            screenWriter.printlnToScreen("Unable to stop server " + e.getMessage());
        }
    }

    @Override
    public boolean canHandle(JCommander jCommander) {
        return Objects.equals(jCommander.getParsedCommand(), StopCommandParameters.COMMAND_NAME);
    }

    @Override
    public Object createCommandObject() {
        return new StopCommandParameters();
    }

}
