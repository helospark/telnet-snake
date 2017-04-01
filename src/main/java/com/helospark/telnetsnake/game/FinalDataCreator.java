package com.helospark.telnetsnake.game;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.helospark.telnetsnake.game.domain.SnakeGameResultDto;
import com.helospark.telnetsnake.game.repository.TopListProviderService;

@Component
public class FinalDataCreator {
    @Autowired
    private TopListProviderService topListProviderService;

    public String createFinalData(String ip, int points) {
        StringBuilder stringBuilder = new StringBuilder();
        List<SnakeGameResultDto> topList = topListProviderService.select(10);
        stringBuilder.append("\r\nThanx for playing! You finished with " + points + " points! Here is the toplist:\r\n");
        stringBuilder.append("Place\tPoints\r\n");
        for (int i = 0; i < topList.size(); ++i) {
            stringBuilder.append((i + 1) + ".\t" + topList.get(i).getPoints());
            if (topList.get(i).getId().equals(ip)) {
                stringBuilder.append(" <- This is you (based on IP)");
            }
            stringBuilder.append("\r\n");
        }
        return stringBuilder.toString();
    }
}
