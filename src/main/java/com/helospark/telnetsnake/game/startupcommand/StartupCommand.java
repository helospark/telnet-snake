package com.helospark.telnetsnake.game.startupcommand;

import com.beust.jcommander.JCommander;

public interface StartupCommand {

    public void execute(Object commandObject);

    public boolean canHandle(JCommander jCommander);

    Object createCommandObject();
}
