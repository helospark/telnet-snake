package com.helospark.telnetsnake.game.parameters;

import java.util.Optional;

import com.beust.jcommander.Parameter;

public class GlobalParameters {
    @Parameter(names = { "-h", "--help" }, help = true, description = "Display usage")
    public Boolean help;
    @Parameter(names = { "-v", "--version" }, help = true, description = "Display version")
    public Boolean version;

    public Optional<Boolean> getHelp() {
        return Optional.ofNullable(help);
    }

    public Optional<Boolean> getVersion() {
        return Optional.ofNullable(version);
    }

}
