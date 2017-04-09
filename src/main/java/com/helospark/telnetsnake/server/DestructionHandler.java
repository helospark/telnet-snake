package com.helospark.telnetsnake.server;

import java.net.ServerSocket;
import java.sql.Connection;
import java.sql.SQLException;

import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.helospark.telnetsnake.server.socket.ServerSocketProvider;

@Component
public class DestructionHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(DestructionHandler.class);
    @Autowired
    private ServerSocketProvider serverSocketProvider;
    @Autowired
    @Lazy
    private Connection connection;

    @PreDestroy
    public void destroy() {
        LOGGER.info("Destruction handler invoked");
        try {
            if (serverSocketProvider.hasActiveSocket()) {
                ServerSocket activeSocket = serverSocketProvider.provideActiveServerSocket();
                activeSocket.close();
            }
        } catch (Exception e) {
            LOGGER.error("Unable to destroy server socket", e);
        }
        try {
            connection.close();
        } catch (SQLException e) {
            LOGGER.error("Unable to destroy database connection", e);
        }
    }
}
