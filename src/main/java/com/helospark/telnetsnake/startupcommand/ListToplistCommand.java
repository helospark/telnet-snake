package com.helospark.telnetsnake.startupcommand;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.helospark.telnetsnake.StartupCommand;
import com.helospark.telnetsnake.game.domain.SnakeGameResultDto;
import com.helospark.telnetsnake.game.repository.TopListService;

@Component
public class ListToplistCommand implements StartupCommand {
    @Autowired
    private TopListService topListService;

    @Override
    public void execute(List<String> args) {
        List<SnakeGameResultDto> topList = topListService.getTopList(10);
        topList.stream()
                .forEach(entry -> System.out.println(entry.getIp() + " " + entry.getPoints()));
    }

    @Override
    public boolean canHandle(Optional<String> commandName) {
        return commandName.isPresent() && commandName.get().equals("toplist");
    }

}
