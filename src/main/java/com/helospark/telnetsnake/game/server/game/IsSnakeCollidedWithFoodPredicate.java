package com.helospark.telnetsnake.game.server.game;

import java.util.List;

import org.springframework.stereotype.Component;

import com.helospark.telnetsnake.game.server.game.domain.Coordinate;

@Component
public class IsSnakeCollidedWithFoodPredicate {

	public boolean test(List<Coordinate> snake, Coordinate food) {
		Coordinate snakeHeadPosition = snake.get(0);
		return snakeHeadPosition.equals(food);
	}
}
