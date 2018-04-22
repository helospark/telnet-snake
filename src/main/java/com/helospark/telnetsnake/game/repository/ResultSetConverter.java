package com.helospark.telnetsnake.game.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import com.helospark.lightdi.annotation.Component;

import com.helospark.telnetsnake.game.server.game.domain.SnakeGameResultDto;

@Component
public class ResultSetConverter {

    public List<SnakeGameResultDto> convert(ResultSet resultSet) throws SQLException {
        List<SnakeGameResultDto> result = new ArrayList<>();
        while (resultSet.next()) {
            String ip = resultSet.getString("ip");
            int points = resultSet.getInt("points");
            Timestamp date = resultSet.getTimestamp("date");
            String userInput = resultSet.getString("userInput");
            result.add(SnakeGameResultDto.builder()
                    .withAllUserInputs(userInput)
                    .withIp(ip)
                    .withPoints(points)
                    .withLocalDateTime(convertDate(date))
                    .build());
        }
        return result;
    }

    private LocalDateTime convertDate(Timestamp date) {
        Instant instant = Instant.ofEpochMilli(date.getTime());
        return LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
    }
}
