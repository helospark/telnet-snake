package com.helospark.telnetsnake.startupcommand;

import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.helospark.telnetsnake.StartupCommand;

@Component
public class ShutdownCommand implements StartupCommand {
    private static final Logger LOGGER = LoggerFactory.getLogger(ShutdownCommand.class);
    @Value("${SHUTDOWN_PORT}")
    private int shutdownPort;

    @Override
    public void execute(List<String> args) {
        try (Socket shutdownSocket = new Socket()) {
            shutdownSocket.connect(new InetSocketAddress("localhost", shutdownPort));
        } catch (Exception e) {
            LOGGER.error("Shutdown failed", e);
        }
    }

    @Override
    public boolean canHandle(Optional<String> commandName) {
        return commandName.filter(command -> command.equals("stop")).isPresent();
    }

}
