package com.helospark.telnetsnake.startupcommand;

import java.net.Socket;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.beust.jcommander.JCommander;
import com.helospark.telnetsnake.output.ScreenWriter;
import com.helospark.telnetsnake.parameters.StopCommandParameters;
import com.helospark.telnetsnake.startupcommand.help.SocketFactory;

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
