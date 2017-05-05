package com.helospark.telnetsnake.parameters;

import com.beust.jcommander.Parameters;

@Parameters(commandNames = StartupCommandParameters.COMMAND_NAME, commandDescription = "Start server")
public class StartupCommandParameters {
    public static final String COMMAND_NAME = "start";

}
