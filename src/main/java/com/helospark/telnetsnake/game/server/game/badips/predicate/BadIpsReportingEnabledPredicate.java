package com.helospark.telnetsnake.game.server.game.badips.predicate;

import java.util.function.Predicate;

import com.helospark.lightdi.annotation.Value;
import com.helospark.lightdi.annotation.Component;

import com.helospark.telnetsnake.game.server.game.domain.SnakeGameResultDto;

@Component
public class BadIpsReportingEnabledPredicate implements Predicate<SnakeGameResultDto> {
    @Value("${BAD_IPS_REPORTING_ENABLED}")
    private boolean badIpsReportingEnabled;

    @Override
    public boolean test(SnakeGameResultDto result) {
        return badIpsReportingEnabled;
    }
}
