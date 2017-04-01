package com.helospark.telnetsnake.game;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.helospark.telnetsnake.game.domain.SnakeGameResultDto;
import com.helospark.telnetsnake.game.domain.SnakeGameSession;
import com.helospark.telnetsnake.game.domain.SnakeIO;
import com.helospark.telnetsnake.game.repository.TopListProviderService;

@Component
public class GameFinishedHook {
    @Autowired
    private TopListProviderService topListProviderService;
    @Autowired
    private IpExtractor ipExtractor;
    @Autowired
    private SnakeDataSender snakeDataSender;

    public void onGameFinished(SnakeIO snakeIO, SnakeGameSession domain) {
        String currentUserIp = ipExtractor.getIp(snakeIO.getSocket());
        snakeDataSender.sendFinalResult(currentUserIp, domain.points, snakeIO.getPrintWriter());
        saveResult(currentUserIp, domain.points, domain.allUserInputs.toString());
        snakeIO.close();
    }

    private void saveResult(String ip, int points, String string) {
        SnakeGameResultDto snakeGameResultDto = SnakeGameResultDto.builder()
                .withId(ip)
                .withPoints(points)
                .build();
        topListProviderService.saveResult(snakeGameResultDto);
    }

}
