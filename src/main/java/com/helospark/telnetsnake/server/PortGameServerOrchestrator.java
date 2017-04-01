package com.helospark.telnetsnake.server;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.helospark.telnetsnake.server.domain.ClientConnectionData;

@Component
public class PortGameServerOrchestrator {
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
    private ServerSocketFactory serverSocketFactory;
    @Autowired
    private ShutdownHandler shutdownHandler;

    public void start() {
        ServerSocket serverSocket = serverSocketFactory.createServerSocket();
        handleIncomingConnection(serverSocket);
    }

    private void handleIncomingConnection(ServerSocket serverSocket) {
        boolean isRunning = true;
        List<ClientConnectionData> connectedClients = new ArrayList<>();
        while (isRunning) {
            try {
                Socket clientSocket = serverSocket.accept();
                ClientConnectionData clientConnectionData = clientConnectionDataFactory.createConnectionData(clientSocket);
                if (isClientAllowedToConnectPredicate.test(connectedClients, clientConnectionData)) {
                    clientConnectionAllowerHandler.connectClient(clientConnectionData);
                    connectedClients.add(clientConnectionData);
                } else {
                    clientConnectionDeniedHandler.denyClient(clientConnectionData);
                }
            } catch (Exception ex) {
                connectedClients = expiredConnectionFilter.filter(connectedClients);
            }
        }
        shutdownHandler.stopGames(connectedClients);
    }

}
