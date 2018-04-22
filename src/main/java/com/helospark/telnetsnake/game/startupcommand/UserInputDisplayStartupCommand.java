package com.helospark.telnetsnake.game.startupcommand;

import java.util.List;
import java.util.Objects;

import com.helospark.lightdi.annotation.Autowired;
import com.helospark.lightdi.annotation.Component;

import com.beust.jcommander.JCommander;
import com.helospark.telnetsnake.game.output.ScreenWriter;
import com.helospark.telnetsnake.game.parameters.DisplayGamesParameters;
import com.helospark.telnetsnake.game.repository.MostRecentGamesService;
import com.helospark.telnetsnake.game.server.game.domain.SnakeGameResultDto;

@Component
public class UserInputDisplayStartupCommand implements StartupCommand {
    @Autowired
    private MostRecentGamesService mostRecentGamesService;
    @Autowired
    private ScreenWriter screenWriter;

    @Override
    public void execute(Object commandObject) {
        DisplayGamesParameters command = (DisplayGamesParameters) commandObject;

        List<SnakeGameResultDto> mostRecent;
        if (command.getLimit().isPresent()) {
            mostRecent = mostRecentGamesService.getMostRecentLimitedBy(10);
        } else {
            mostRecent = mostRecentGamesService.getAllWithoutLimit();
        }
        mostRecent.stream().forEach(game -> display(game));
    }

    private void display(SnakeGameResultDto game) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(game.getIp() + "\t" + game.getLocalDateTime() + "\t" + game.getPoints() + "\n");
        stringBuilder.append(game.getAllUserInputs() + "\n");
        stringBuilder.append("------------" + "\n");
        screenWriter.printlnToScreen(stringBuilder.toString());
    }

    @Override
    public boolean canHandle(JCommander jCommander) {
        return Objects.equals(jCommander.getParsedCommand(), DisplayGamesParameters.COMMAND_NAME);
    }

    @Override
    public Object createCommandObject() {
        return new DisplayGamesParameters();
    }

}
