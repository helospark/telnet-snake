package com.helospark.telnetsnake.game.server;

import java.net.ServerSocket;
import java.sql.SQLException;

import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.helospark.lightdi.annotation.Autowired;
import com.helospark.lightdi.annotation.Component;
import com.helospark.lightdi.annotation.Eager;
import com.helospark.telnetsnake.game.repository.configuration.ConnectionProvider;
import com.helospark.telnetsnake.game.repository.configuration.HsqlAutoServerManager;
import com.helospark.telnetsnake.game.server.socket.ServerSocketProvider;

@Component
@Eager
public class DestructionHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(DestructionHandler.class);
    @Autowired
    private ServerSocketProvider serverSocketProvider;
    @Autowired
    private ConnectionProvider connectionProvider;
    @Autowired
    private HsqlAutoServerManager hsqlAutoServerManager;

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
            if (connectionProvider.isInitialized()) {
                connectionProvider.get().close();
            }
        } catch (SQLException e) {
            LOGGER.error("Unable to destroy database connection", e);
        }
        try {
            hsqlAutoServerManager.shutdown();
        } catch (Exception e) {
            LOGGER.error("Error shutting down server", e);
        }
    }
}
