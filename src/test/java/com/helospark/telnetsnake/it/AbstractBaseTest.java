package com.helospark.telnetsnake.it;

import static java.util.Collections.emptyList;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;

import com.helospark.telnetsnake.startupcommand.StartupExecutor;

public abstract class AbstractBaseTest extends AbstractTestNGSpringContextTests {
    protected Socket socket = null;
    protected PrintWriter printWriter;
    protected BufferedReader inputReader;

    @Autowired
    private StartupExecutor startupExecutor;

    public void initialize(ServerSocket serverSocket) {
        try {
            socket = new Socket("localhost", serverSocket.getLocalPort());
            printWriter = new PrintWriter(socket.getOutputStream(), true);
            inputReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            startupExecutor.start(emptyList());
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
