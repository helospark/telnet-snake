package com.helospark.telnetsnake.game.server.game.badips;

import java.util.function.Predicate;

import com.helospark.lightdi.annotation.Autowired;
import com.helospark.lightdi.annotation.Component;

import com.helospark.telnetsnake.game.server.game.badips.predicate.BadIpsDataNotEmptyPredicate;
import com.helospark.telnetsnake.game.server.game.badips.predicate.BadIpsIsNumberOfPriorConnectionsLargerThanThresholdPredicate;
import com.helospark.telnetsnake.game.server.game.badips.predicate.BadIpsReportingEnabledPredicate;
import com.helospark.telnetsnake.game.server.game.badips.predicate.BadIpsWhitelistContainingPredicate;
import com.helospark.telnetsnake.game.server.game.domain.SnakeGameResultDto;

@Component
public class BadIpsReportingPredicate implements Predicate<SnakeGameResultDto> {
    @Autowired
    private BadIpsReportingEnabledPredicate badIpsReportingEnabledPredicate;
    @Autowired
    private BadIpsWhitelistContainingPredicate whitelistContainingPredicate;
    @Autowired
    private BadIpsIsNumberOfPriorConnectionsLargerThanThresholdPredicate numberOfConnectionMoreThanThreshold;
    @Autowired
    private BadIpsDataNotEmptyPredicate badIpsDataNotEmptyPredicate;

    @Override
    public boolean test(SnakeGameResultDto snakeGameResultDto) {
        return badIpsReportingEnabledPredicate.test(snakeGameResultDto) &&
                badIpsDataNotEmptyPredicate.test(snakeGameResultDto) &&
                !whitelistContainingPredicate.test(snakeGameResultDto.getIp()) &&
                numberOfConnectionMoreThanThreshold.test(snakeGameResultDto);
    }

}
