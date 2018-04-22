package com.helospark.telnetsnake.game.jcommander;

import java.util.List;

import com.helospark.lightdi.annotation.Autowired;
import com.helospark.lightdi.annotation.Component;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.JCommander.Builder;
import com.helospark.telnetsnake.game.parameters.GlobalParameters;
import com.helospark.telnetsnake.game.startupcommand.StartupCommand;

@Component
public class JCommanderFactory {
    @Autowired
    private List<StartupCommand> commands;

    public JCommander createJCommander() {
        GlobalParameters globalParameters = new GlobalParameters();
        Builder jCommanderBuilder = JCommander.newBuilder()
                .addObject(globalParameters);

        initializeCommands(jCommanderBuilder);
        JCommander jCommander = jCommanderBuilder.build();
        return jCommander;
    }

    private void initializeCommands(Builder jCommanderBuilder) {
        commands.stream()
                .forEach(command -> jCommanderBuilder.addCommand(command.createCommandObject()));
    }

}
