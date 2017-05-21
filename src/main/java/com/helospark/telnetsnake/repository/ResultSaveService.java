package com.helospark.telnetsnake.repository;

import java.sql.PreparedStatement;
import java.sql.Timestamp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.helospark.telnetsnake.repository.configuration.ConnectionProvider;
import com.helospark.telnetsnake.repository.logfile.ResultToFileSaver;
import com.helospark.telnetsnake.server.game.domain.SnakeGameResultDto;

@Component
public class ResultSaveService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ResultSaveService.class);
    @Autowired
    @Lazy
    private ConnectionProvider connectionProvider;
    @Autowired
    private ResultToFileSaver resultToFileSaver;
    @Value("${SAVE_RESULT_TO_FILE}")
    private boolean saveResultToFile;

    public void saveResult(SnakeGameResultDto snakeGameResultDto) {
        String databaseUserInputContent = snakeGameResultDto.getAllUserInputs();
        if (saveResultToFile) {
            databaseUserInputContent = resultToFileSaver.saveToFile(snakeGameResultDto);
        }
        try {
            String update = "INSERT INTO snake_game_result VALUES (?, ?, ?, ?)";
            PreparedStatement prepareStatement = connectionProvider.get().prepareStatement(update);
            prepareStatement.setString(1, snakeGameResultDto.getIp());
            prepareStatement.setInt(2, snakeGameResultDto.getPoints());
            prepareStatement.setTimestamp(3, new Timestamp(new java.util.Date().getTime()));
            prepareStatement.setString(4, databaseUserInputContent);
            prepareStatement.executeUpdate();
        } catch (Exception e) {
            LOGGER.error("Error while saving result", e);
        }
    }

}
