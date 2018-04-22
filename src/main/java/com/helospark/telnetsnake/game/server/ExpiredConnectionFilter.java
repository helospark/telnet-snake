package com.helospark.telnetsnake.game.server;

import java.util.List;
import java.util.stream.Collectors;

import com.helospark.lightdi.annotation.Component;

import com.helospark.telnetsnake.game.server.domain.ClientConnectionData;

@Component
public class ExpiredConnectionFilter {

    public List<ClientConnectionData> filter(List<ClientConnectionData> connectedClients) {
        return connectedClients
                .stream()
                .filter(entry -> !entry.snakeIO.isClosed())
                .collect(Collectors.toList());
    }
}
