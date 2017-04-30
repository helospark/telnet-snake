package com.helospark.telnetsnake.repository;

import static java.util.Collections.emptyList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.helospark.telnetsnake.repository.configuration.ConnectionProvider;
import com.helospark.telnetsnake.server.game.domain.SnakeGameResultDto;

@Component
public class ResultFromUserService {
    @Autowired
    @Lazy
    private ConnectionProvider connectionProvider;

    @Autowired
    private ResultSetConverter resultSetConverter;

    public List<SnakeGameResultDto> getMostRecentLimitedBy(String ip) {
        try {
            String sql = "SELECT * FROM snake_game_result WHERE ip=? ORDER BY date DESC";
            PreparedStatement selectStatement = connectionProvider.get().prepareStatement(sql);
            selectStatement.setString(1, ip);
            ResultSet resultSet = selectStatement.executeQuery();
            return resultSetConverter.convert(resultSet);
        } catch (Exception e) {
            e.printStackTrace();
            return emptyList();
        }
    }
}
