package com.helospark.telnetsnake.game.server.game;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.helospark.telnetsnake.game.server.game.domain.SnakeGameSession;

@Component
public class SnakeGameCollisionHandler {
    @Autowired
    private IsSnakeCollidedWithFoodPredicate isSnakeCollidedWithFoodPredicate;
    @Autowired
    private FoodGenerator foodGeneratorService;

    public void handleCollisionWithFood(SnakeGameSession domain) {
        if (isSnakeCollidedWithFoodPredicate.test(domain.snake, domain.foodPosition)) {
            ++domain.points;
            domain.foodPosition = foodGeneratorService.generateFood(domain.snake);
            domain.shouldGrowSnake = true;
        } else {
            domain.shouldGrowSnake = false;
        }
    }

}
