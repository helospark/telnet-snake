package com.helospark.telnetsnake.startupcommand;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.helospark.telnetsnake.StartupCommand;
import com.helospark.telnetsnake.repository.MostRecentGamesService;
import com.helospark.telnetsnake.server.game.domain.SnakeGameResultDto;

@Component
public class UserInputDisplayStartupCommand implements StartupCommand {
    @Autowired
    private MostRecentGamesService mostRecentGamesService;

    @Override
    public void execute(List<String> args) {
        List<SnakeGameResultDto> mostRecent;
        if (args.contains("-l")) {
            mostRecent = mostRecentGamesService.getMostRecentLimitedBy(10);
        } else {
            mostRecent = mostRecentGamesService.getAllWithoutLimit();
        }
        mostRecent.stream()
                .forEach(game -> display(game));
    }

    private void display(SnakeGameResultDto game) {
        System.out.println(game.getIp() + "\t" + game.getLocalDateTime() + "\t" + game.getPoints());
        System.out.println(game.getAllUserInputs());
        System.out.println("------------");
    }

    @Override
    public boolean canHandle(Optional<String> commandName) {
        return commandName.isPresent() && commandName.get().equals("display-most-recent");
    }

}
