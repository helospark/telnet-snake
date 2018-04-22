package com.helospark.telnetsnake.game.server.socket;

import java.net.ServerSocket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.helospark.lightdi.annotation.Value;
import com.helospark.lightdi.annotation.Component;

@Component
public class ServerSocketFactory {
    private static final Logger LOGGER = LoggerFactory.getLogger(ServerSocketFactory.class);
    @Value("${PORT}")
    private int port;
    @Value("${SERVER_SOCKET_TIMEOUT}")
    private int socketTimeout;

    public ServerSocket createServerSocket() {
        try {
            LOGGER.info("Initializing socket...");
            ServerSocket serverSocket = new ServerSocket(port);
            serverSocket.setSoTimeout(socketTimeout);
            LOGGER.info("Created socket on " + serverSocket.getLocalPort());
            return serverSocket;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
