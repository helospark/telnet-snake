package com.helospark.telnetsnake;

import java.util.List;
import java.util.Optional;

public interface StartupCommand {

    public void execute(List<String> args);

    public boolean canHandle(Optional<String> commandName);
}
