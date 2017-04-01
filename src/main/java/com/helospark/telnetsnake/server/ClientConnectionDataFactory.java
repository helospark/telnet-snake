package com.helospark.telnetsnake.server;

import java.net.Socket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.helospark.telnetsnake.game.IpExtractor;
import com.helospark.telnetsnake.game.SnakeIOFactory;
import com.helospark.telnetsnake.game.domain.SnakeIO;
import com.helospark.telnetsnake.server.domain.ClientConnectionData;

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
