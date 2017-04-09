package com.helospark.telnetsnake.server.game;

import java.util.List;
import java.util.function.Predicate;

import org.springframework.stereotype.Component;

import com.helospark.telnetsnake.server.game.domain.Coordinate;

@Component
public class IsSnakeCollidedWithItselfPredicate implements Predicate<List<Coordinate>> {

    @Override
    public boolean test(List<Coordinate> snake) {
        Coordinate snakeHead = snake.get(0);
        for (int i = 1; i < snake.size(); ++i) {
            Coordinate snakePartPosition = snake.get(i);
            if (snakePartPosition.equals(snakeHead)) {
                return true;
            }
        }
        return false;
    }

}
