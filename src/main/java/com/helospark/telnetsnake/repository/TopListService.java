package com.helospark.telnetsnake.repository;

import static java.util.Collections.emptyList;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.helospark.telnetsnake.repository.configuration.ConnectionFactory;
import com.helospark.telnetsnake.repository.configuration.ConnectionProvider;
import com.helospark.telnetsnake.server.game.domain.SnakeGameResultDto;

@Component
public class TopListService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ConnectionFactory.class);
    @Autowired
    @Lazy
    private ConnectionProvider connectionProvider;

    @Autowired
    private ResultSetConverter resultSetConverter;

    public List<SnakeGameResultDto> getTopList(int limit) {
        try {
            String update = "SELECT * FROM snake_game_result ORDER BY points DESC";
            Statement selectStatement = connectionProvider.get().createStatement();
            selectStatement.setMaxRows(limit);
            ResultSet resultSet = selectStatement.executeQuery(update);
            return resultSetConverter.convert(resultSet);
        } catch (Exception e) {
            LOGGER.error("Error while getting toplist", e);
            return emptyList();
        }
    }
}
