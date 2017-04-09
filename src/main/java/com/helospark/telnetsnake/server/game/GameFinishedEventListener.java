package com.helospark.telnetsnake.server.game;

import com.helospark.telnetsnake.server.game.domain.SnakeGameResultDto;

public interface GameFinishedEventListener {

    public void onGameFinishedEvent(SnakeGameResultDto snakeGameResultDto);

}
