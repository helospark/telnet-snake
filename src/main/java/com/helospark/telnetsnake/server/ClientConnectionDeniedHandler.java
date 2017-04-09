package com.helospark.telnetsnake.server;

import java.io.IOException;
import java.io.PrintWriter;

import org.springframework.stereotype.Component;

import com.helospark.telnetsnake.server.domain.ClientConnectionData;
import com.helospark.telnetsnake.server.game.domain.SnakeIO;

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
