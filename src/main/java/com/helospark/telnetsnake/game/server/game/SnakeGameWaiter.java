package com.helospark.telnetsnake.game.server.game;

import org.springframework.stereotype.Component;

import com.helospark.telnetsnake.game.server.game.domain.Direction;

@Component
public class SnakeGameWaiter {
    private final double HORIZONTAL_SPEED_MULTIPLIER = 0.75;

    public void waitBeforeMoving(int waitTime, Direction currentDirection) {
        try {
            if (currentDirection == Direction.LEFT || currentDirection == Direction.RIGHT) {
                waitTime *= HORIZONTAL_SPEED_MULTIPLIER;
            }
            Thread.sleep(waitTime);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
