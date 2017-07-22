package com.helospark.telnetsnake.game.parameters;

import java.util.Optional;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;

@Parameters(commandNames = DisplayGamesParameters.COMMAND_NAME, commandDescription = "Display games")
public class DisplayGamesParameters {
    public static final String COMMAND_NAME = "display";

    @Parameter(names = { "-n", "-l", "--limit" }, description = "Number of games to display (if not specified all)")
    private Integer limit;

    public Optional<Integer> getLimit() {
        return Optional.ofNullable(limit);
    }

}
