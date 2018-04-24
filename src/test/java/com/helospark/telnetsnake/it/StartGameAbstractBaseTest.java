package com.helospark.telnetsnake.it;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import com.helospark.telnetsnake.game.StartupExecutor;

public abstract class StartGameAbstractBaseTest {
    protected Socket socket = null;
    protected PrintWriter printWriter;
    protected BufferedReader inputReader;

    public void initialize(StartupExecutor startupExecutor, ServerSocket serverSocket) {
        try {
            socket = new Socket("localhost", serverSocket.getLocalPort());
            printWriter = new PrintWriter(socket.getOutputStream(), true);
            inputReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            startupExecutor.start(new String[] { "start" });
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void tearDown() {
        try {
            printWriter.println("q");
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            socket.close();
            printWriter.close();
            inputReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
