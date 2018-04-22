package com.helospark.telnetsnake.game.startupcommand.help;

import java.net.InetSocketAddress;
import java.net.Socket;

import com.helospark.lightdi.annotation.Component;

@Component
public class SocketFactory {

    public Socket createSocket() {
        return new Socket();
    }

    public InetSocketAddress createInetSocketAddress(String host, int port) {
        return new InetSocketAddress(host, port);
    }
}
