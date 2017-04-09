package com.helospark.telnetsnake.server.game;

import java.net.Socket;

import org.springframework.stereotype.Component;

@Component
public class IpExtractor {

    public String getIp(Socket socket) {
        return socket.getInetAddress().getHostAddress().toString();
    }
}
