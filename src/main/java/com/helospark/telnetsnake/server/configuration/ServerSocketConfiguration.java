package com.helospark.telnetsnake.server.configuration;

import java.net.ServerSocket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServerSocketConfiguration {
    private static final Logger LOGGER = LoggerFactory.getLogger(ServerSocketConfiguration.class);
    @Value("${PORT}")
    private int port;
    @Value("${SERVER_SOCKET_TIMEOUT}")
    private int socketTimeout;

    @Bean(name = "snakeGameServerSocket")
    public ServerSocket createServerSocket() {
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            serverSocket.setSoTimeout(socketTimeout);
            LOGGER.info("Created socket on " + serverSocket.getLocalPort());
            return serverSocket;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
