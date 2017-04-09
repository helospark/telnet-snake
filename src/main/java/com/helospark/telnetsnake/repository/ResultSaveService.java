package com.helospark.telnetsnake.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.helospark.telnetsnake.repository.logfile.ResultToFileSaver;
import com.helospark.telnetsnake.server.game.domain.SnakeGameResultDto;

@Component
public class ResultSaveService {
    @Autowired
    @Lazy
    private Connection connection;
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
            PreparedStatement prepareStatement = connection.prepareStatement(update);
            prepareStatement.setString(1, snakeGameResultDto.getIp());
            prepareStatement.setInt(2, snakeGameResultDto.getPoints());
            prepareStatement.setTimestamp(3, new Timestamp(new java.util.Date().getTime()));
            prepareStatement.setString(4, databaseUserInputContent);
            prepareStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
