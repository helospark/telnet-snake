package com.helospark.telnetsnake.startupcommand.helper;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

@Component
public class ExtractStartupCommand {

    public Optional<String> extractStartupCommand(List<String> args) {
        List<String> commandNames = args.stream()
                .filter(argument -> !argument.startsWith("-"))
                .collect(Collectors.toList());
        if (commandNames.size() > 1) {
            throw new IllegalStateException("More than one command is given");
        }
        return commandNames.stream().findFirst();
    }

}