package com.helospark.telnetsnake.game.server.game;

import com.helospark.telnetsnake.game.server.game.domain.SnakeGameResultDto;

public interface GameFinishedEventListener {

    public void onGameFinishedEvent(SnakeGameResultDto snakeGameResultDto);

}
