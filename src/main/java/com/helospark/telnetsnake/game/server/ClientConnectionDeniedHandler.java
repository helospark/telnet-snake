package com.helospark.telnetsnake.game.server;

import java.io.IOException;
import java.io.PrintWriter;

import com.helospark.lightdi.annotation.Component;

import com.helospark.telnetsnake.game.server.domain.ClientConnectionData;
import com.helospark.telnetsnake.game.server.game.domain.SnakeIO;

@Component
public class ClientConnectionDeniedHandler {

    public void denyClient(ClientConnectionData clientConnectionData) throws IOException {
        SnakeIO snakeIO = clientConnectionData.snakeIO;
        PrintWriter printWriter = snakeIO.getPrintWriter();
        printWriter.println("Too many connections");
        printWriter.flush();
        snakeIO.close();
    }
}
