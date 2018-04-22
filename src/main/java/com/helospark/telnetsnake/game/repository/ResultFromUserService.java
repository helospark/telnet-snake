package com.helospark.telnetsnake.game.repository;

import static java.util.Collections.emptyList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.helospark.lightdi.annotation.Autowired;
import com.helospark.lightdi.annotation.Component;
import com.helospark.telnetsnake.game.repository.configuration.ConnectionProvider;
import com.helospark.telnetsnake.game.server.game.domain.SnakeGameResultDto;

@Component
public class ResultFromUserService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ResultFromUserService.class);
    @Autowired
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
            LOGGER.error("Error while selecting most recent games", e);
            return emptyList();
        }
    }
}
