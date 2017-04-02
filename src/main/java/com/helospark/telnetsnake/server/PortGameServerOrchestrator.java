package com.helospark.telnetsnake.server;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.helospark.telnetsnake.server.domain.ClientConnectionData;

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
    @Qualifier("snakeGameServerSocket")
    private ServerSocket serverSocket;
    @Autowired
    private ShutdownHandler shutdownHandler;

    public void start() {
        LOGGER.info("Server started");
        boolean isRunning = true;
        List<ClientConnectionData> connectedClients = new ArrayList<>();
        while (isRunning) {
            try {
                Socket clientSocket = serverSocket.accept();
                ClientConnectionData clientConnectionData = clientConnectionDataFactory.createConnectionData(clientSocket);
                if (isClientAllowedToConnectPredicate.test(connectedClients, clientConnectionData)) {
                    LOGGER.info("Connection accepted from " + clientConnectionData.clientIp);
                    clientConnectionAllowerHandler.connectClient(clientConnectionData);
                    connectedClients.add(clientConnectionData);
                } else {
                    LOGGER.info("Connection denied from " + clientConnectionData.clientIp);
                    clientConnectionDeniedHandler.denyClient(clientConnectionData);
                }
            } catch (Exception ex) {
                connectedClients = expiredConnectionFilter.filter(connectedClients);
            }
        }
        shutdownHandler.stopGames(connectedClients);
    }

}
