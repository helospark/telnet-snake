package com.helospark.telnetsnake.game.server.game.badips.predicate;

import java.util.function.Predicate;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

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
