package com.helospark.telnetsnake.game.server.game;

import java.util.List;

import com.helospark.lightdi.annotation.Autowired;
import com.helospark.lightdi.annotation.Component;

import com.helospark.telnetsnake.game.repository.TopListService;
import com.helospark.telnetsnake.game.server.game.domain.SnakeGameResultDto;

@Component
public class FinalDataCreator {
    @Autowired
    private TopListService topListProviderService;

    public String createFinalData(String ip, int points) {
        StringBuilder stringBuilder = new StringBuilder();
        List<SnakeGameResultDto> topList = topListProviderService.getCachedTopList();
        stringBuilder
                .append("\r\nThanx for playing! You finished with " + points + " points! Here is the toplist:\r\n");
        stringBuilder.append("Place\tPoints\r\n");
        for (int i = 0; i < topList.size(); ++i) {
            stringBuilder.append((i + 1) + ".\t" + topList.get(i).getPoints());
            if (topList.get(i).getIp().equals(ip)) {
                stringBuilder.append(" <- This is you (based on IP)");
            }
            stringBuilder.append("\r\n");
        }
        return stringBuilder.toString();
    }
}
