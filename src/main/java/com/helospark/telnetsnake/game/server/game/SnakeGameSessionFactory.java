package com.helospark.telnetsnake.game.server.game;

import java.util.ArrayList;
import java.util.List;

import com.helospark.lightdi.annotation.Autowired;
import com.helospark.lightdi.annotation.Value;
import com.helospark.lightdi.annotation.Component;

import com.helospark.telnetsnake.game.server.game.domain.Coordinate;
import com.helospark.telnetsnake.game.server.game.domain.Direction;
import com.helospark.telnetsnake.game.server.game.domain.SnakeGameSession;

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
