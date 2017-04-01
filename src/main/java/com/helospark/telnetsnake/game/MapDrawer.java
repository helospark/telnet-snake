package com.helospark.telnetsnake.game;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.helospark.telnetsnake.game.domain.Coordinate;
import com.helospark.telnetsnake.game.domain.SnakeGameSession;

@Component
public class MapDrawer {
    @Value("${MAP_WIDTH}")
    private int width;
    @Value("${MAP_HEIGHT}")
    private int height;

    public char[][] createMap(SnakeGameSession domain) {
        char[][] map = createEmptyMap();
        drawSnakeToMap(domain.snake, map);
        drawFoodToMap(domain.foodPosition, map);
        return map;
    }

    private char[][] createEmptyMap() {
        char[][] map = new char[height][width];
        for (int i = 0; i < height; ++i) {
            for (int j = 0; j < width; ++j) {
                map[i][j] = ' ';
            }
        }
        return map;
    }

    private void drawFoodToMap(Coordinate foodPosition, char[][] map) {
        map[foodPosition.getY()][foodPosition.getX()] = 'O';
    }

    private void drawSnakeToMap(List<Coordinate> snake, char[][] map) {
        Coordinate snakeHead = snake.get(0);
        map[snakeHead.getY()][snakeHead.getX()] = '#';
        for (int i = 1; i < snake.size(); ++i) {
            Coordinate snakePart = snake.get(i);
            map[snakePart.getY()][snakePart.getX()] = 'X';
        }
    }

}
