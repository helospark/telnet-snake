package com.helospark.telnetsnake.startupcommand.help;

import java.net.InetSocketAddress;
import java.net.Socket;

import org.springframework.stereotype.Component;

@Component
public class SocketFactory {

    public Socket createSocket() {
        return new Socket();
    }

    public InetSocketAddress createInetSocketAddress(String host, int port) {
        return new InetSocketAddress(host, port);
    }
}
