package com.helospark.telnetsnake.game.server.game;

import org.springframework.stereotype.Component;

@Component
public class LogFileWriter {

    public void write(String userInput) {
        // TODO Auto-generated method stub

    }

    private String getLogFile(String ip) {
        return "/var/log/snake/" + ip.replace(".", "_");
    }

}
