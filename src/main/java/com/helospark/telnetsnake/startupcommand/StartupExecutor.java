package com.helospark.telnetsnake.startupcommand;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.helospark.telnetsnake.StartupCommand;
import com.helospark.telnetsnake.startupcommand.helper.ExtractStartupCommand;

@Component
public class StartupExecutor {
    private final List<StartupCommand> commands;
    private final ExtractStartupCommand extractStartupCommand;

    @Autowired
    public StartupExecutor(List<StartupCommand> commands, ExtractStartupCommand extractStartupCommand) {
        this.commands = commands;
        this.extractStartupCommand = extractStartupCommand;
    }

    public void start(List<String> args) {
        Optional<String> commandName = extractStartupCommand.extractStartupCommand(args);
        commands.stream()
                .filter(command -> command.canHandle(commandName))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("No handler can handle " + commandName))
                .execute(args);
    }
}
