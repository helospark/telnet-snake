package com.helospark.telnetsnake.repository;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.helospark.telnetsnake.server.game.domain.SnakeGameResultDto;

@Component
public class ResultSetConverter {

    public List<SnakeGameResultDto> convert(ResultSet resultSet) throws SQLException {
        List<SnakeGameResultDto> result = new ArrayList<>();
        while (resultSet.next()) {
            String ip = resultSet.getString("ip");
            int points = resultSet.getInt("points");
            Date date = resultSet.getDate("date");
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

    private LocalDateTime convertDate(Date date) {
        Instant instant = Instant.ofEpochMilli(date.getTime());
        return LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
    }
}
