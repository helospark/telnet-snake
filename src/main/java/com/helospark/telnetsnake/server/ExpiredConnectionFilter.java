package com.helospark.telnetsnake.server;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.helospark.telnetsnake.server.domain.ClientConnectionData;

@Component
public class ExpiredConnectionFilter {

    public List<ClientConnectionData> filter(List<ClientConnectionData> connectedClients) {
        return connectedClients
                .stream()
                .filter(entry -> !entry.snakeIO.isClosed())
                .collect(Collectors.toList());
    }
}
