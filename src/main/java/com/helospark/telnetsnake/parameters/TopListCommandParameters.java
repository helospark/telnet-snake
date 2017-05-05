package com.helospark.telnetsnake.parameters;

import java.util.Optional;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;

@Parameters(commandNames = TopListCommandParameters.COMMAND_NAME, commandDescription = "Display toplist")
public class TopListCommandParameters {
    public static final String COMMAND_NAME = "toplist";

    @Parameter(names = { "-n", "-l", "--limit" }, description = "Number of entries to display")
    private Integer limit;

    public Optional<Integer> getLimit() {
        return Optional.ofNullable(limit);
    }

}
