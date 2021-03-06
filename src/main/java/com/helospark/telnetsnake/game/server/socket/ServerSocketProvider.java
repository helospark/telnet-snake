package com.helospark.telnetsnake.game.server.socket;

import java.net.ServerSocket;

import com.helospark.lightdi.annotation.Autowired;
import com.helospark.lightdi.annotation.Component;

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

    public boolean hasActiveSocket() {
        return this.serverSocket != null;
    }
}
