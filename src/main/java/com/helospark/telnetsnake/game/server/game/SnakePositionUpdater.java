package com.helospark.telnetsnake.game.server.game;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.helospark.telnetsnake.game.server.game.domain.Coordinate;
import com.helospark.telnetsnake.game.server.game.domain.Direction;
import com.helospark.telnetsnake.game.server.game.domain.SnakeGameSession;

@Component
public class SnakePositionUpdater {
    @Autowired
    private InputDirectionProvider inputDirectionProvider;
    @Value("${MAP_WIDTH}")
    private int width;
    @Value("${MAP_HEIGHT}")
    private int height;

    public boolean updateSnake(SnakeGameSession domain, String userInput) {
        Direction previousDirection = domain.currentDirection;
        Coordinate snakeTail = domain.snake.get(domain.snake.size() - 1).copy();
        domain.currentDirection = inputDirectionProvider.getNewDirection(userInput, previousDirection);
        updateSnake(domain.snake, previousDirection);
        if (domain.shouldGrowSnake) {
            domain.snake.add(snakeTail);
            domain.shouldGrowSnake = false;
        }
        return previousDirection != domain.currentDirection;
    }

    private Coordinate updateSnake(List<Coordinate> snake, Direction currentDirection) {
        Coordinate previousCoordinate = moveSnakeHeadInCurrentDirection(snake, currentDirection);
        for (int i = 1; i < snake.size(); ++i) {
            Coordinate currentSnakePartPosition = snake.get(i);
            Coordinate copyOfPosition = currentSnakePartPosition.copy();
            currentSnakePartPosition.set(previousCoordinate);
            previousCoordinate = copyOfPosition;
        }
        return previousCoordinate;
    }

    private Coordinate moveSnakeHeadInCurrentDirection(List<Coordinate> snake, Direction currentDirection) {
        Coordinate snakeHeadPosition = snake.get(0);
        Coordinate previousCoordinate = snakeHeadPosition.copy();
        snakeHeadPosition.add(currentDirection.getDirectionVector());
        snakeHeadPosition.limitToConstraints(width, height);
        return previousCoordinate;
    }
}
