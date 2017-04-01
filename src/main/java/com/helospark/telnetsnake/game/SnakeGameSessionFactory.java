package com.helospark.telnetsnake.game;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.helospark.telnetsnake.game.domain.Coordinate;
import com.helospark.telnetsnake.game.domain.Direction;
import com.helospark.telnetsnake.game.domain.SnakeGameSession;

@Component
public class SnakeGameSessionFactory {
    @Value("${MAP_HEIGHT}")
    private int gameHeight;
    @Value("${INITIAL_WAIT_TIME_BETWEEN_STEPS}")
    private int initialWaitTime;
    @Value("${INITIAL_SNAKE_BODY_START_POSITION}")
    private int snakeBodyStartPosition;
    @Value("${INITIAL_SNAKE_BODY_END_POSITION}")
    private int snakeBodyEndPosition;

    @Autowired
    private FoodGenerator foodGeneratorService;

    public SnakeGameSession createInitialSession() {
        SnakeGameSession domain = new SnakeGameSession();
        domain.snake = createSnake();
        domain.currentDirection = Direction.LEFT;
        domain.waitTime = initialWaitTime;
        domain.foodPosition = foodGeneratorService.generateFood(domain.snake);
        domain.shouldGrowSnake = false;
        domain.numberOfStepsSinceMovement = 0;
        domain.points = 0;
        domain.allUserInputs = new StringBuilder();
        return domain;
    }

    private List<Coordinate> createSnake() {
        List<Coordinate> snake = new ArrayList<>();
        for (int i = snakeBodyStartPosition; i < snakeBodyEndPosition; ++i) {
            snake.add(new Coordinate(i, gameHeight / 2));
        }
        return snake;
    }

}
