package com.helospark.telnetsnake.server;

import java.net.ServerSocket;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ServerSocketFactory {
    @Autowired
    private DestructionHandler destructionHandler;
    @Value("${PORT}")
    private int port;
    @Value("${SERVER_SOCKET_TIMEOUT}")
    private int socketTimeout;

    public ServerSocket createServerSocket() {
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            serverSocket.setSoTimeout(socketTimeout);
            destructionHandler.addCloseableForDestruction(serverSocket);
            return serverSocket;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PostConstruct
    public void closeSockets() {

    }
}
