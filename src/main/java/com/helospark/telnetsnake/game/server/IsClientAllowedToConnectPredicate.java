package com.helospark.telnetsnake.game.server;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.helospark.telnetsnake.game.server.domain.ClientConnectionData;

@Component
public class IsClientAllowedToConnectPredicate {
    @Value("${MAX_CONNECTION_FROM_SAME_IP}")
    private int maxNumberOfConnectionFromSameIp;
    @Value("${MAX_CONNECTIONS}")
    private int maxConnections;

    public boolean test(List<ClientConnectionData> connectedClients, ClientConnectionData clientConnectionData) {
        return isNumberOfConnectionFromIpIsLessThanLimit(connectedClients, clientConnectionData) &&
                isNumberOfConnectionsLessThanLimit(connectedClients);
    }

    private boolean isNumberOfConnectionsLessThanLimit(List<ClientConnectionData> connectedClients) {
        int numberOfAllConnections = connectedClients.stream()
                .filter(data -> !data.snakeIO.isClosed())
                .collect(Collectors.toList())
                .size();
        return numberOfAllConnections < maxConnections;
    }

    private boolean isNumberOfConnectionFromIpIsLessThanLimit(List<ClientConnectionData> connectedClients, ClientConnectionData clientConnectionData) {
        int numberOfConnectionsFromThisIp = connectedClients.stream()
                .filter(data -> data.clientIp.equals(clientConnectionData.clientIp))
                .filter(data -> !data.snakeIO.isClosed())
                .collect(Collectors.toList())
                .size();
        return numberOfConnectionsFromThisIp < maxNumberOfConnectionFromSameIp;
    }
}
