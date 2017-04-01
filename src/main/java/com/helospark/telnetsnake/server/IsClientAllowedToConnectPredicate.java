package com.helospark.telnetsnake.server;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.helospark.telnetsnake.server.domain.ClientConnectionData;

@Component
public class IsClientAllowedToConnectPredicate {
    @Value("${MAX_CONNECTION_FROM_SAME_IP}")
    private int maxNumberOfConnectionFromSameIp;

    public boolean test(List<ClientConnectionData> connectedClients, ClientConnectionData clientConnectionData) {
        int numberOfConnectionsFromThisIp = connectedClients.stream()
                .filter(data -> data.clientIp.equals(clientConnectionData.clientIp))
                .collect(Collectors.toList())
                .size();
        return numberOfConnectionsFromThisIp < maxNumberOfConnectionFromSameIp;
    }
}
