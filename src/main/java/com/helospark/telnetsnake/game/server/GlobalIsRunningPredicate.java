package com.helospark.telnetsnake.game.server;

import org.springframework.stereotype.Component;

@Component
public class GlobalIsRunningPredicate {
    private volatile boolean isRunning = true;

    public boolean test() {
        return isRunning;
    }

    public void stop() {
        isRunning = false;
    }
}
