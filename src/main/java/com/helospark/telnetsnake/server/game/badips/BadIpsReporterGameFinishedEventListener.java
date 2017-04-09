package com.helospark.telnetsnake.server.game.badips;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.helospark.telnetsnake.server.game.GameFinishedEventListener;
import com.helospark.telnetsnake.server.game.domain.SnakeGameResultDto;

@Component
public class BadIpsReporterGameFinishedEventListener implements GameFinishedEventListener {
    @Autowired
    private BadIpsReportingPredicate badIpsReportingPredicate;

    @Override
    public void onGameFinishedEvent(SnakeGameResultDto snakeGameResultDto) {
        if (badIpsReportingPredicate.test(snakeGameResultDto)) {

        }
    }

}
