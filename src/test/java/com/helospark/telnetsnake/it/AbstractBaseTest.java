package com.helospark.telnetsnake.it;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;

public abstract class AbstractBaseTest extends AbstractTestNGSpringContextTests {
    protected Socket socket = null;
    protected PrintWriter printWriter;
    protected BufferedReader inputReader;

    public void initialize(ServerSocket serverSocket) {
        try {
            socket = new Socket("localhost", serverSocket.getLocalPort());
            printWriter = new PrintWriter(socket.getOutputStream(), true);
            inputReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
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
