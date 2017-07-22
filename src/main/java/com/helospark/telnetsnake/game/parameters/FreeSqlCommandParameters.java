package com.helospark.telnetsnake.game.parameters;

import java.util.Optional;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;

@Parameters(commandNames = FreeSqlCommandParameters.COMMAND_NAME, commandDescription = "Run SQL commands")
public class FreeSqlCommandParameters {
    public static final String COMMAND_NAME = "sql";

    @Parameter(names = { "-s", "--sql" }, description = "SQL command, if absent interactive SQL terminal is shown")
    private String sql;

    public Optional<String> getSql() {
        return Optional.ofNullable(sql);
    }

}
