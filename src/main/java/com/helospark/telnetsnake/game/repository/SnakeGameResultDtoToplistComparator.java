package com.helospark.telnetsnake.game.repository;

import java.time.ZoneOffset;
import java.util.Comparator;

import com.helospark.lightdi.annotation.Component;

import com.helospark.telnetsnake.game.server.game.domain.SnakeGameResultDto;

@Component
public class SnakeGameResultDtoToplistComparator implements Comparator<SnakeGameResultDto> {

    @Override
    public int compare(SnakeGameResultDto a, SnakeGameResultDto b) {
        if (a.getPoints() != b.getPoints()) {
            return b.getPoints() - a.getPoints();
        } else {
            return (int) (b.getLocalDateTime().toInstant(ZoneOffset.UTC).getEpochSecond()
                    - a.getLocalDateTime().toInstant(ZoneOffset.UTC).getEpochSecond());
        }
    }

}
