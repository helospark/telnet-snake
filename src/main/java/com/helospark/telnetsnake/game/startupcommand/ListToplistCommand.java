package com.helospark.telnetsnake.game.startupcommand;

import java.util.List;
import java.util.Objects;

import com.helospark.lightdi.annotation.Autowired;
import com.helospark.lightdi.annotation.Component;

import com.beust.jcommander.JCommander;
import com.helospark.telnetsnake.game.output.ScreenWriter;
import com.helospark.telnetsnake.game.parameters.TopListCommandParameters;
import com.helospark.telnetsnake.game.repository.TopListService;
import com.helospark.telnetsnake.game.server.game.domain.SnakeGameResultDto;

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
        List<SnakeGameResultDto> topList = topListService.getUncachedToplist(limit);
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
