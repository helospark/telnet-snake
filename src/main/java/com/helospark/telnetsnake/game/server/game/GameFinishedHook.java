package com.helospark.telnetsnake.game.server.game;

import java.time.LocalDateTime;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.helospark.lightdi.annotation.Autowired;
import com.helospark.lightdi.annotation.Component;

import com.helospark.telnetsnake.game.repository.ResultSaveService;
import com.helospark.telnetsnake.game.repository.TopListService;
import com.helospark.telnetsnake.game.server.game.domain.SnakeGameResultDto;
import com.helospark.telnetsnake.game.server.game.domain.SnakeGameSession;
import com.helospark.telnetsnake.game.server.game.domain.SnakeIO;

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
    @Autowired
    private TopListService toplistService;

    public void onGameFinished(SnakeIO snakeIO, SnakeGameSession domain) {
        String currentUserIp = ipExtractor.getIp(snakeIO.getSocket());
        SnakeGameResultDto snakeGameResultDto = createResultObject(currentUserIp, domain.points,
                domain.allUserInputs.toString());
        resultSaveService.saveResult(snakeGameResultDto);
        toplistService.updateTopListWithResult(snakeGameResultDto);
        gameFinishedListeners.forEach(listener -> listener.onGameFinishedEvent(snakeGameResultDto));
        snakeDataSender.sendFinalResult(currentUserIp, domain.points, snakeIO.getPrintWriter());
        snakeIO.close();
        LOGGER.info("Game finished with " + currentUserIp);
    }

    private SnakeGameResultDto createResultObject(String ip, int points, String userInput) {
        SnakeGameResultDto snakeGameResultDto = SnakeGameResultDto.builder()
                .withIp(ip)
                .withPoints(points)
                .withAllUserInputs(userInput)
                .withLocalDateTime(LocalDateTime.now())
                .build();
        return snakeGameResultDto;
    }

}
