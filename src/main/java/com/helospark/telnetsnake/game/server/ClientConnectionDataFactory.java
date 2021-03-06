package com.helospark.telnetsnake.game.server;

import java.net.Socket;

import com.helospark.lightdi.annotation.Autowired;
import com.helospark.lightdi.annotation.Component;

import com.helospark.telnetsnake.game.server.domain.ClientConnectionData;
import com.helospark.telnetsnake.game.server.game.IpExtractor;
import com.helospark.telnetsnake.game.server.game.SnakeIOFactory;
import com.helospark.telnetsnake.game.server.game.domain.SnakeIO;

@Component
public class ClientConnectionDataFactory {
    @Autowired
    private SnakeIOFactory snakeIOFactory;
    @Autowired
    private IpExtractor ipExtractor;

    public ClientConnectionData createConnectionData(Socket clientSocket) {
        String clientIp = ipExtractor.getIp(clientSocket);
        SnakeIO snakeIO = snakeIOFactory.createSnakeIO(clientSocket);
        return ClientConnectionData.builder()
                .withClientIp(clientIp)
                .withClientSocket(clientSocket)
                .withSnakeIO(snakeIO)
                .build();
    }
}
