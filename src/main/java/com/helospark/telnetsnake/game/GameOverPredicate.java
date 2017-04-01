package com.helospark.telnetsnake.game;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.helospark.telnetsnake.game.domain.SnakeGameSession;

@Component
public class GameOverPredicate {
    private final int MOVEMENT_TIMOUT = 100;
    @Autowired
    private IsSnakeCollidedWithItselfPredicate isSnakeCollidedWithItselfPredicate;

    public boolean checkGameOver(SnakeGameSession domain, String userInput, int numberOfStepsSinceMovement) {
        boolean isSnakeCollidedWithItself = isSnakeCollidedWithItselfPredicate.test(domain.snake);
        if (isSnakeCollidedWithItself) {
            return true;
        }
        if (userInput.startsWith("q")) {
            return true;
        }
        if (domain.allUserInputs.length() > 10000) {
            return true;
        }
        if (numberOfStepsSinceMovement >= MOVEMENT_TIMOUT) {
            return true;
        }
        return false;
    }
}
