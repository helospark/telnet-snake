package com.helospark.telnetsnake.server.game;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.helospark.telnetsnake.repository.ResultSaveService;
import com.helospark.telnetsnake.server.game.domain.SnakeGameResultDto;
import com.helospark.telnetsnake.server.game.domain.SnakeGameSession;
import com.helospark.telnetsnake.server.game.domain.SnakeIO;

@Component
public class GameFinishedHook {
    private static final Logger LOGGER = LoggerFactory.getLogger(GameFinishedHook.class);
    @Autowired
    private IpExtractor ipExtractor;
    @Autowired
    private SnakeDataSender snakeDataSender;
    @Autowired
    private ResultSaveService resultSaveService;
    @Autowired
    private List<GameFinishedEventListener> gameFinishedListeners;

    public void onGameFinished(SnakeIO snakeIO, SnakeGameSession domain) {
        String currentUserIp = ipExtractor.getIp(snakeIO.getSocket());
        SnakeGameResultDto snakeGameResultDto = createResultObject(currentUserIp, domain.points, domain.allUserInputs.toString());
        resultSaveService.saveResult(snakeGameResultDto);
        gameFinishedListeners.forEach(listener -> listener.onGameFinishedEvent(snakeGameResultDto));
        snakeDataSender.sendFinalResult(currentUserIp, domain.points, snakeIO.getPrintWriter());
        snakeIO.close();
        LOGGER.info("Connection closed for " + currentUserIp);
    }

    private SnakeGameResultDto createResultObject(String ip, int points, String userInput) {
        SnakeGameResultDto snakeGameResultDto = SnakeGameResultDto.builder()
                .withIp(ip)
                .withPoints(points)
                .withAllUserInputs(userInput)
                .build();
        return snakeGameResultDto;
    }

}
