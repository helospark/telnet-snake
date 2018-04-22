package com.helospark.telnetsnake.game.server;

import com.helospark.lightdi.annotation.Component;

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
