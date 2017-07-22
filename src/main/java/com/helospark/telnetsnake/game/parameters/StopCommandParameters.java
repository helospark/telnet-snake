package com.helospark.telnetsnake.game.parameters;

import com.beust.jcommander.Parameters;

@Parameters(commandNames = StopCommandParameters.COMMAND_NAME, commandDescription = "Stop the running server")
public class StopCommandParameters {
    public static final String COMMAND_NAME = "stop";

}
