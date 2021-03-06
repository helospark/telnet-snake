package com.helospark.telnetsnake.game.server.game.badips.predicate;

import java.util.List;
import java.util.function.Predicate;

import com.helospark.lightdi.annotation.Autowired;
import com.helospark.lightdi.annotation.Value;
import com.helospark.lightdi.annotation.Component;

import com.helospark.telnetsnake.game.repository.ResultFromUserService;
import com.helospark.telnetsnake.game.server.game.domain.SnakeGameResultDto;

@Component
public class BadIpsIsNumberOfPriorConnectionsLargerThanThresholdPredicate implements Predicate<SnakeGameResultDto> {
    @Value("${BAD_IPS_NUMBER_OF_CONNECTION_FOR_REPORTING}")
    private int badIpsNumberOfConnection;
    @Autowired
    private ResultFromUserService resultFromUserService;

    @Override
    public boolean test(SnakeGameResultDto t) {
        List<SnakeGameResultDto> result = resultFromUserService.getMostRecentLimitedBy(t.getIp());
        return result.size() >= badIpsNumberOfConnection;
    }

}
