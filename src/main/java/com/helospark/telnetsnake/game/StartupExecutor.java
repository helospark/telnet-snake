package com.helospark.telnetsnake.game;

import java.util.List;
import java.util.Optional;

import com.helospark.lightdi.annotation.Autowired;
import com.helospark.lightdi.annotation.Component;

import com.beust.jcommander.JCommander;
import com.helospark.telnetsnake.game.jcommander.JCommanderArgumentParser;
import com.helospark.telnetsnake.game.jcommander.JCommanderFactory;
import com.helospark.telnetsnake.game.startupcommand.StartupCommand;

@Component
public class StartupExecutor {
    private final List<StartupCommand> commands;
    private final JCommanderFactory jCommanderFactory;
    private final JCommanderArgumentParser jCommanderArgumentParser;

    @Autowired
    public StartupExecutor(List<StartupCommand> commands,
            JCommanderFactory jCommanderFactory,
            JCommanderArgumentParser jCommanderArgumentParser) {
        this.commands = commands;
        this.jCommanderFactory = jCommanderFactory;
        this.jCommanderArgumentParser = jCommanderArgumentParser;
    }

    public void start(String[] args) {
        JCommander jCommander = jCommanderFactory.createJCommander();
        Optional<Object> parsedCommand = jCommanderArgumentParser.parseArguments(jCommander, args);
        if (parsedCommand.isPresent()) {
            commands.stream()
                    .filter(command -> command.canHandle(jCommander))
                    .findFirst()
                    .orElseThrow(() -> new IllegalStateException("No handler can handle " + jCommander))
                    .execute(parsedCommand.get());
        }
    }

}
