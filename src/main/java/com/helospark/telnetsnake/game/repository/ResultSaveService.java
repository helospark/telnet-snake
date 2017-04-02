package com.helospark.telnetsnake.game.repository;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.helospark.telnetsnake.game.domain.SnakeGameResultDto;
import com.helospark.telnetsnake.game.repository.logfile.ResultToFileSaver;

@Component
public class ResultSaveService {
    @Autowired
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
            prepareStatement.setDate(3, new Date(new java.util.Date().getTime()));
            prepareStatement.setString(4, databaseUserInputContent);
            prepareStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
