package com.helospark.telnetsnake.server;

import java.io.IOException;
import java.io.PrintWriter;

import org.springframework.stereotype.Component;

import com.helospark.telnetsnake.game.domain.SnakeIO;
import com.helospark.telnetsnake.server.domain.ClientConnectionData;

@Component
public class ClientConnectionDeniedHandler {

    public void denyClient(ClientConnectionData clientConnectionData) throws IOException {
        SnakeIO snakeIO = clientConnectionData.snakeIO;
        PrintWriter printWriter = snakeIO.getPrintWriter();
        printWriter.println("Only one connection allowed");
        printWriter.flush();
        snakeIO.close();
    }
}
