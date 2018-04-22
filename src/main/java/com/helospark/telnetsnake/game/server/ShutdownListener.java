package com.helospark.telnetsnake.game.server;

import java.net.ServerSocket;
import java.net.Socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.helospark.lightdi.annotation.Autowired;
import com.helospark.lightdi.annotation.Component;
import com.helospark.lightdi.annotation.Value;

@Component
public class ShutdownListener {
    private static final Logger LOGGER = LoggerFactory.getLogger(ShutdownListener.class);
    @Autowired
    private GlobalIsRunningPredicate isRunningPredicate;
    @Value("${SHUTDOWN_PORT}")
    private int shutdownPort;

    public void startListeningForShutdown() {
        new Thread(() -> {
            Socket acceptedConnection = null;
            while (acceptedConnection == null) {
                try (ServerSocket serverSocket = new ServerSocket(shutdownPort)) {
                    acceptedConnection = serverSocket.accept();
                } catch (Exception e) {
                    LOGGER.error("Exception on shutdown listening", e);
                    throw new RuntimeException(e);
                }
            }
            LOGGER.info("Preparing to shut down...");
            isRunningPredicate.stop();
        }).start();
    }
}
