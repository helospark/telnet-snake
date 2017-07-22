package com.helospark.telnetsnake.game.server.game;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

import org.springframework.stereotype.Component;

import com.helospark.telnetsnake.game.server.game.domain.SnakeIO;

@Component
public class SnakeIOFactory {

    public SnakeIO createSnakeIO(Socket clientSocket) {
        try {
            OutputStream outputStream = clientSocket.getOutputStream();
            PrintWriter printWriter = new PrintWriter(outputStream);
            InputStream inputStream = clientSocket.getInputStream();
            return SnakeIO.builder()
                    .withInputStream(inputStream)
                    .withPrintWriter(printWriter)
                    .withSocket(clientSocket)
                    .withIsClosed(false)
                    .build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
