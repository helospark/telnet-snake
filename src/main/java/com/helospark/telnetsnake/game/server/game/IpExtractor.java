package com.helospark.telnetsnake.game.server.game;

import java.net.Socket;

import com.helospark.lightdi.annotation.Component;

@Component
public class IpExtractor {

    public String getIp(Socket socket) {
        return socket.getInetAddress().getHostAddress().toString();
    }
}
