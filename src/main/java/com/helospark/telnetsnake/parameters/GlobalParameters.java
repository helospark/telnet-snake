package com.helospark.telnetsnake.parameters;

import java.util.Optional;

import com.beust.jcommander.Parameter;

public class GlobalParameters {
    @Parameter(names = { "-h", "--help" }, help = true, description = "Display usage")
    public Boolean help;

    public Optional<Boolean> getHelp() {
        return Optional.ofNullable(help);
    }

}
