package com.helospark.telnetsnake.game;

import org.springframework.stereotype.Component;

import com.helospark.telnetsnake.game.domain.Direction;

@Component
public class InputDirectionProvider {

    public Direction getNewDirection(String receivedString, Direction currentDirection) {
        if (receivedString.length() > 0) {
            if (receivedString.charAt(0) == 's' && currentDirection != Direction.DOWN) {
                return Direction.UP;
            }
            if (receivedString.charAt(0) == 'w' && currentDirection != Direction.UP) {
                return Direction.DOWN;
            }
            if (receivedString.charAt(0) == 'a' && currentDirection != Direction.RIGHT) {
                return Direction.LEFT;
            }
            if (receivedString.charAt(0) == 'd' && currentDirection != Direction.LEFT) {
                return Direction.RIGHT;
            }
        }
        return currentDirection;
    }

}
