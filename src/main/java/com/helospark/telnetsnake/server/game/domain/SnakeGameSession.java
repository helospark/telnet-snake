package com.helospark.telnetsnake.server.game.domain;

import java.util.List;

/**
 * Encapsulates all the state of a snake game session.
 *
 * @author helospark
 */
public class SnakeGameSession {
    public Direction currentDirection;
    public List<Coordinate> snake;
    public Coordinate foodPosition;
    public int points;
    public boolean shouldGrowSnake;
    public int numberOfStepsSinceMovement;
    public int waitTime;
    public StringBuilder allUserInputs;
}
