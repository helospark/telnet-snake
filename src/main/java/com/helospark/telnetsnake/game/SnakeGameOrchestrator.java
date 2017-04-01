package com.helospark.telnetsnake.game;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.helospark.telnetsnake.game.domain.SnakeGameSession;
import com.helospark.telnetsnake.game.domain.SnakeIO;

@Component
public class SnakeGameOrchestrator {
    @Autowired
    private InputStreamReader inputStreamReader;
    @Autowired
    private SnakeGameSessionFactory snakeGameDomainFactory;
    @Autowired
    private SnakePositionUpdater snakePositionUpdater;
    @Autowired
    private GameOverPredicate gameOverPredicate;
    @Autowired
    private SnakeDataSender snakeDataSender;
    @Autowired
    private SnakeGameWaiter snakeGameWaiter;
    @Autowired
    private SnakeGameCollisionHandler snakeGameCollisionHandler;
    @Autowired
    private GameFinishedHook gameFinishedHook;

    public void runSnakeGame(SnakeIO snakeIO) {
        int numberOfStepsSinceMovement = 0;
        String userInput = "";
        SnakeGameSession domain = snakeGameDomainFactory.createInitialSession();
        while (!gameOverPredicate.checkGameOver(domain, userInput, numberOfStepsSinceMovement)) {
            userInput = inputStreamReader.readInputData(snakeIO.getInputStream());
            domain.allUserInputs.append(userInput);
            boolean directionChanged = snakePositionUpdater.updateSnake(domain, userInput);
            if (directionChanged) {
                numberOfStepsSinceMovement = 0;
            }
            snakeGameCollisionHandler.handleCollisionWithFood(domain);
            snakeDataSender.sendDataToClient(domain, snakeIO.getPrintWriter());
            snakeGameWaiter.waitBeforeMoving(domain.waitTime, domain.currentDirection);
            ++numberOfStepsSinceMovement;
        }
        gameFinishedHook.onGameFinished(snakeIO, domain);
    }

}
