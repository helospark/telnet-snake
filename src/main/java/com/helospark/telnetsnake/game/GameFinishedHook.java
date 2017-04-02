package com.helospark.telnetsnake.game;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.helospark.telnetsnake.game.domain.SnakeGameResultDto;
import com.helospark.telnetsnake.game.domain.SnakeGameSession;
import com.helospark.telnetsnake.game.domain.SnakeIO;
import com.helospark.telnetsnake.game.repository.ResultSaveService;

@Component
public class GameFinishedHook {
    private static final Logger LOGGER = LoggerFactory.getLogger(GameFinishedHook.class);
    @Autowired
    private IpExtractor ipExtractor;
    @Autowired
    private SnakeDataSender snakeDataSender;
    @Autowired
    private ResultSaveService resultSaveService;

    public void onGameFinished(SnakeIO snakeIO, SnakeGameSession domain) {
        String currentUserIp = ipExtractor.getIp(snakeIO.getSocket());
        saveResult(currentUserIp, domain.points, domain.allUserInputs.toString());
        snakeDataSender.sendFinalResult(currentUserIp, domain.points, snakeIO.getPrintWriter());
        snakeIO.close();
        LOGGER.info("Connection closed for " + currentUserIp);
    }

    private void saveResult(String ip, int points, String userInput) {
        SnakeGameResultDto snakeGameResultDto = SnakeGameResultDto.builder()
                .withIp(ip)
                .withPoints(points)
                .withAllUserInputs(userInput)
                .build();
        resultSaveService.saveResult(snakeGameResultDto);
    }

}
