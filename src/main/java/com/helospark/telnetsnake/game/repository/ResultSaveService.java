package com.helospark.telnetsnake.game.repository;

import java.sql.PreparedStatement;
import java.sql.Timestamp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.helospark.lightdi.annotation.Autowired;
import com.helospark.lightdi.annotation.Component;
import com.helospark.lightdi.annotation.Value;
import com.helospark.telnetsnake.game.repository.configuration.ConnectionProvider;
import com.helospark.telnetsnake.game.repository.logfile.ResultToFileSaver;
import com.helospark.telnetsnake.game.server.game.domain.SnakeGameResultDto;

@Component
public class ResultSaveService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ResultSaveService.class);
    @Autowired
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
