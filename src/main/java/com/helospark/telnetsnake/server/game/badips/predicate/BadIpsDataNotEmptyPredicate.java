package com.helospark.telnetsnake.server.game.badips.predicate;

import java.util.function.Predicate;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.helospark.telnetsnake.server.game.domain.SnakeGameResultDto;

@Component
public class BadIpsDataNotEmptyPredicate implements Predicate<SnakeGameResultDto> {
    @Value("${BAD_IPS_REPORT_ONLY_USERS_WHO_SENT_DATA}")
    private boolean reportOnlyUsersWhoSentData;

    @Override
    public boolean test(SnakeGameResultDto t) {
        if (reportOnlyUsersWhoSentData) {
            return t.getAllUserInputs() != null && t.getAllUserInputs().length() > 0;
        }
        return true;
    }

}
