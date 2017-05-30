package com.helospark.telnetsnake.server;

import java.net.Socket;
import java.util.List;
import java.util.concurrent.ExecutorService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.helospark.telnetsnake.server.domain.ClientConnectionData;

@Component
public class ShutdownHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(ShutdownHandler.class);
    @Autowired
    private ExecutorService executorService;

    public void stopGames(List<ClientConnectionData> connectedClients) {
        connectedClients
                .stream()
                .forEach(client -> closeSocket(client));
        executorService.shutdown();
    }

    private void closeSocket(ClientConnectionData client) {
        try {
            Socket socket = client.clientSocket;
            socket.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
