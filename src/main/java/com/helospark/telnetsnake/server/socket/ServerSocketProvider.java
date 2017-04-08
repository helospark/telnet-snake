package com.helospark.telnetsnake.server.socket;

import java.net.ServerSocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ServerSocketProvider {
    @Autowired
    private ServerSocketFactory serverSocketFactory;

    private ServerSocket serverSocket = null;
    private final Object serverSocketGuard = new Object();

    public ServerSocket provideActiveServerSocket() {
        if (this.serverSocket == null) {
            synchronized (serverSocketGuard) {
                if (this.serverSocket == null) {
                    this.serverSocket = serverSocketFactory.createServerSocket();
                }
            }
        }
        return this.serverSocket;
    }
}
