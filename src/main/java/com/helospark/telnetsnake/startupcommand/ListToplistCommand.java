package com.helospark.telnetsnake.startupcommand;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.beust.jcommander.JCommander;
import com.helospark.telnetsnake.output.ScreenWriter;
import com.helospark.telnetsnake.parameters.TopListCommandParameters;
import com.helospark.telnetsnake.repository.TopListService;
import com.helospark.telnetsnake.server.game.domain.SnakeGameResultDto;

@Component
public class ListToplistCommand implements StartupCommand {
    private static final int DEFAULT_TOPLIST_NUMBER = 10;
    @Autowired
    private TopListService topListService;
    @Autowired
    private ScreenWriter screenWriter;

    @Override
    public void execute(Object commandObject) {
        TopListCommandParameters toplistCommand = (TopListCommandParameters) commandObject;
        int limit = toplistCommand.getLimit().orElse(DEFAULT_TOPLIST_NUMBER);
        List<SnakeGameResultDto> topList = topListService.getTopList(limit);
        topList.stream()
                .forEach(entry -> screenWriter.printlnToScreen(entry.getIp() + " " + entry.getPoints()));
    }

    @Override
    public boolean canHandle(JCommander jCommander) {
        return Objects.equals(jCommander.getParsedCommand(), TopListCommandParameters.COMMAND_NAME);
    }

    @Override
    public Object createCommandObject() {
        return new TopListCommandParameters();
    }

}
