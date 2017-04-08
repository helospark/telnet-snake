package com.helospark.telnetsnake.game.repository;

import static java.util.Collections.emptyList;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.helospark.telnetsnake.game.domain.SnakeGameResultDto;

@Component
public class TopListService {
    @Autowired
    @Lazy
    private Connection connection;

    public List<SnakeGameResultDto> getTopList(int limit) {
        try {
            List<SnakeGameResultDto> result = new ArrayList<>();
            String update = "SELECT * FROM snake_game_result ORDER BY points DESC";
            Statement selectStatement = connection.createStatement();
            selectStatement.setMaxRows(limit);
            ResultSet rs = selectStatement.executeQuery(update);
            while (rs.next()) {
                String ip = rs.getString("ip");
                int points = rs.getInt("points");
                Date date = rs.getDate("date");
                String userInput = rs.getString("userInput");
                result.add(SnakeGameResultDto.builder()
                        .withAllUserInputs(userInput)
                        .withIp(ip)
                        .withPoints(points)
                        .build());
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return emptyList();
        }
    }
}
