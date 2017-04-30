package com.helospark.telnetsnake.server;

import java.net.ServerSocket;
import java.net.Socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class ShutdownListener {
    private static final Logger LOGGER = LoggerFactory.getLogger(ShutdownListener.class);
    @Autowired
    private GlobalIsRunningPredicate isRunningPredicate;
    @Value("${SHUTDOWN_PORT}")
    private int shutdownPort;

    @Async
    public void startListeningForShutdown() {
        Socket acceptedConnection = null;
        while (acceptedConnection == null) {
            try (ServerSocket serverSocket = new ServerSocket(shutdownPort)) {
                acceptedConnection = serverSocket.accept();
            } catch (Exception e) {
                LOGGER.error("Exception on shutdown listening", e);
            }
        }
        LOGGER.info("Preparing to shut down...");
        isRunningPredicate.stop();
    }
}
