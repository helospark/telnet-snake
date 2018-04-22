package com.helospark.telnetsnake.game.jcommander;

import com.helospark.lightdi.annotation.Autowired;
import com.helospark.lightdi.annotation.Component;

import com.beust.jcommander.JCommander;
import com.helospark.telnetsnake.game.output.ScreenWriter;

@Component
public class JCommanderUsagePrinter {
    private final ScreenWriter screenWriter;

    @Autowired
    public JCommanderUsagePrinter(ScreenWriter screenWriter) {
        this.screenWriter = screenWriter;
    }

    public void printUsage(JCommander jCommander) {
        StringBuilder stringBuilder = new StringBuilder();
        jCommander.usage(stringBuilder);
        screenWriter.printlnToScreen(stringBuilder.toString());
    }

}
