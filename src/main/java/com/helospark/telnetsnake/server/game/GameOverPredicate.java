package com.helospark.telnetsnake.server.game;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.helospark.telnetsnake.server.game.domain.SnakeGameSession;

@Component
public class GameOverPredicate {
    @Value("${MAXIMUM_USER_INPUT_PER_SESSION}")
    private int maximumUserInputPerSession;
    @Value("${NO_MOVEMENT_TIMEOUT}")
    private int noMovementTimeout;
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
        if (domain.allUserInputs.length() > maximumUserInputPerSession) {
            return true;
        }
        if (numberOfStepsSinceMovement >= noMovementTimeout) {
            return true;
        }
        return false;
    }
}
