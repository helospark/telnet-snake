package com.helospark.telnetsnake.game.server.game.badips;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.helospark.telnetsnake.game.server.game.GameFinishedEventListener;
import com.helospark.telnetsnake.game.server.game.domain.SnakeGameResultDto;

@Component
public class BadIpsReporterGameFinishedEventListener implements GameFinishedEventListener {
    @Autowired
    private BadIpsReportingPredicate badIpsReportingPredicate;
    @Autowired
    private BadIpsReportingService badIpsReportingService;

    @Override
    public void onGameFinishedEvent(SnakeGameResultDto snakeGameResultDto) {
        if (badIpsReportingPredicate.test(snakeGameResultDto)) {
            badIpsReportingService.report(snakeGameResultDto.getIp());
        }
    }

}
