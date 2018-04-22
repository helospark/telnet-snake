package com.helospark.telnetsnake.game.server.game;

import java.util.List;
import java.util.Random;

import com.helospark.lightdi.annotation.Value;
import com.helospark.lightdi.annotation.Component;

import com.helospark.telnetsnake.game.server.game.domain.Coordinate;

@Component
public class FoodGenerator {
    private final Random random = new Random();
    @Value("${MAP_WIDTH}")
    private int width;
    @Value("${MAP_HEIGHT}")
    private int height;

    public Coordinate generateFood(List<Coordinate> snake) {
        int x = random.nextInt(width);
        int y = random.nextInt(height);
        Coordinate food = new Coordinate(x, y);
        int counter = 0;
        while (counter < width * height) {
            if (!snake.contains(food)) {
                break;
            }
            ++x;
            if (x >= width) {
                x = 0;
                ++y;
                if (y >= height) {
                    y = 0;
                }
            }
            food.set(x, y);
            ++counter;
        }
        return food;
    }
}
