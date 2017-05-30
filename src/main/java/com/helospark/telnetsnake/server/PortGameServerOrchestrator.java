package com.helospark.telnetsnake.server;

import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.helospark.telnetsnake.server.domain.ClientConnectionData;
import com.helospark.telnetsnake.server.socket.ServerSocketProvider;

@Component
public class PortGameServerOrchestrator {
    private static final Logger LOGGER = LoggerFactory.getLogger(PortGameServerOrchestrator.class);
    @Autowired
    private ClientConnectionAllowerHandler clientConnectionAllowerHandler;
    @Autowired
    private ClientConnectionDeniedHandler clientConnectionDeniedHandler;
    @Autowired
    private ClientConnectionDataFactory clientConnectionDataFactory;
    @Autowired
    private IsClientAllowedToConnectPredicate isClientAllowedToConnectPredicate;
    @Autowired
    private ExpiredConnectionFilter expiredConnectionFilter;
    @Autowired
    private ServerSocketProvider serverSocketProvider;
    @Autowired
    private ShutdownHandler shutdownHandler;
    @Autowired
    private GlobalIsRunningPredicate isRunningPredicate;

    public void start() {
        LOGGER.info("Server started");
        List<ClientConnectionData> connectedClients = new ArrayList<>();
        ServerSocket serverSocket = createServerSocket();
        while (isRunningPredicate.test() && !serverSocket.isClosed()) {
            try {
                Socket clientSocket = serverSocket.accept();
                ClientConnectionData clientConnectionData = clientConnectionDataFactory
                        .createConnectionData(clientSocket);
                if (isClientAllowedToConnectPredicate.test(connectedClients, clientConnectionData)) {
                    LOGGER.info("Connection accepted from " + clientConnectionData.clientIp);
                    clientConnectionAllowerHandler.connectClient(clientConnectionData);
                    connectedClients.add(clientConnectionData);
                } else {
                    LOGGER.info("Connection denied from " + clientConnectionData.clientIp);
                    clientConnectionDeniedHandler.denyClient(clientConnectionData);
                }
            } catch (SocketTimeoutException ex) {
                connectedClients = expiredConnectionFilter.filter(connectedClients);
            } catch (Exception e) {
                connectedClients = expiredConnectionFilter.filter(connectedClients);
                LOGGER.error("Unexpected exception", e);
            }
        }
        shutdownHandler.stopGames(connectedClients);
        LOGGER.info("Shutdown complete");
    }

    private ServerSocket createServerSocket() {
        try {
            return serverSocketProvider.provideActiveServerSocket();
        } catch (Exception e) {
            LOGGER.error("Unable to open server socket, most likely port is already used", e);
            throw new RuntimeException(e);
        }
    }

}
