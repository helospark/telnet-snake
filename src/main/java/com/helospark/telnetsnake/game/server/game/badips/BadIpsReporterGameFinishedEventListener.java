package com.helospark.telnetsnake.game.server.game.badips;

import com.helospark.lightdi.annotation.Autowired;
import com.helospark.lightdi.annotation.Component;

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
