package com.helospark.telnetsnake.game.jcommander;

import java.util.Optional;

import org.springframework.stereotype.Component;

import com.beust.jcommander.JCommander;

@Component
public class JCommanderCommandObjectExtractor {

    public Optional<Object> extract(JCommander jCommander) {
        String parsedCommandName = jCommander.getParsedCommand();
        return Optional.ofNullable(jCommander.getCommands().get(parsedCommandName))
                .map(command -> command.getObjects())
                .filter(commandList -> !commandList.isEmpty())
                .map(commandList -> commandList.get(0));
    }
}
