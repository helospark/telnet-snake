package com.helospark.telnetsnake.server.game.badips;

import java.util.function.Predicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.helospark.telnetsnake.server.game.badips.predicate.BadIpsIsNumberOfPriorConnectionsLargerThanThresholdPredicate;
import com.helospark.telnetsnake.server.game.badips.predicate.BadIpsReportingEnabledPredicate;
import com.helospark.telnetsnake.server.game.badips.predicate.BadIpsWhitelistContainingPredicate;
import com.helospark.telnetsnake.server.game.domain.SnakeGameResultDto;

@Component
public class BadIpsReportingPredicate implements Predicate<SnakeGameResultDto> {
    @Autowired
    private BadIpsReportingEnabledPredicate badIpsReportingEnabledPredicate;
    @Autowired
    private BadIpsWhitelistContainingPredicate whitelistContainingPredicate;
    @Autowired
    private BadIpsIsNumberOfPriorConnectionsLargerThanThresholdPredicate numberOfConnectionMoreThanThreshold;

    @Override
    public boolean test(SnakeGameResultDto snakeGameResultDto) {
        return badIpsReportingEnabledPredicate.test(snakeGameResultDto) &&
                !whitelistContainingPredicate.test(snakeGameResultDto.getIp()) &&
                numberOfConnectionMoreThanThreshold.test(snakeGameResultDto);
    }

}
