package com.helospark.telnetsnake.game.repository;

import static java.util.Collections.emptyList;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.helospark.telnetsnake.game.repository.configuration.ConnectionProvider;
import com.helospark.telnetsnake.game.server.game.domain.SnakeGameResultDto;

@Component
public class MostRecentGamesService {
    private static final Logger LOGGER = LoggerFactory.getLogger(MostRecentGamesService.class);
    @Autowired
    private ConnectionProvider connectionProvider;

    @Autowired
    private ResultSetConverter resultSetConverter;

    public List<SnakeGameResultDto> getMostRecentLimitedBy(int limit) {
        try {
            String update = "SELECT * FROM snake_game_result ORDER BY date DESC";
            Statement selectStatement = connectionProvider.get().createStatement();
            selectStatement.setMaxRows(limit);
            ResultSet resultSet = selectStatement.executeQuery(update);
            return resultSetConverter.convert(resultSet);
        } catch (Exception e) {
            LOGGER.error("Error while selecting most recent games", e);
            return emptyList();
        }
    }

    public List<SnakeGameResultDto> getMostRecentGamesAfterDate(LocalDate localDate) {
        try {
            String sql = "SELECT * FROM snake_game_result ORDER BY date DESC WHERE date > ?";
            PreparedStatement selectStatement = connectionProvider.get().prepareStatement(sql);
            selectStatement.setDate(1, convertDate(localDate));
            ResultSet resultSet = selectStatement.executeQuery(sql);
            return resultSetConverter.convert(resultSet);
        } catch (Exception e) {
            LOGGER.error("Error while selecting most recent games", e);
            return emptyList();
        }
    }

    private Date convertDate(LocalDate localDate) {
        return new Date(localDate.atStartOfDay(ZoneOffset.systemDefault()).toInstant().toEpochMilli());
    }

    public List<SnakeGameResultDto> getAllWithoutLimit() {
        try {
            String update = "SELECT * FROM snake_game_result ORDER BY date DESC";
            Statement selectStatement = connectionProvider.get().createStatement();
            ResultSet resultSet = selectStatement.executeQuery(update);
            return resultSetConverter.convert(resultSet);
        } catch (Exception e) {
            LOGGER.error("Error while selecting most recent games", e);
            return emptyList();
        }
    }
}
