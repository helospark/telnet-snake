package com.helospark.telnetsnake.game.jcommander;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.beust.jcommander.JCommander;
import com.helospark.telnetsnake.game.output.ScreenWriter;
import com.helospark.telnetsnake.game.parameters.GlobalParameters;

@Component
public class JCommanderArgumentParser {
    private final JCommanderCommandObjectExtractor jCommanderCommandObjectExtractor;
    private final JCommanderUsagePrinter jCommanderUsagePrinter;
    private final ScreenWriter screenWriter;
    private final String version;

    public JCommanderArgumentParser(JCommanderCommandObjectExtractor jCommanderCommandObjectExtractor, JCommanderUsagePrinter jCommanderUsagePrinter,
            ScreenWriter screenWriter, @Value("${PROJECT.VERSION}") String version) {
        this.jCommanderCommandObjectExtractor = jCommanderCommandObjectExtractor;
        this.screenWriter = screenWriter;
        this.jCommanderUsagePrinter = jCommanderUsagePrinter;
        this.version = version;
    }

    public Optional<Object> parseArguments(JCommander jCommander, String[] args) {
        try {
            jCommander.parse(args);
        } catch (Exception e) {
            jCommanderUsagePrinter.printUsage(jCommander);
            return Optional.empty();
        }
        GlobalParameters globalParameters = (GlobalParameters) jCommander.getObjects().get(0);
        Optional<Object> optionalParsedCommandObject = jCommanderCommandObjectExtractor.extract(jCommander);

        if (isVersionDisplayNeeded(globalParameters)) {
            screenWriter.printlnToScreen(version);
        } else if (isUsageDisplayNeeded(globalParameters, optionalParsedCommandObject)) {
            jCommanderUsagePrinter.printUsage(jCommander);
            return Optional.empty();
        }
        return optionalParsedCommandObject;
    }

    private boolean isVersionDisplayNeeded(GlobalParameters globalParameters) {
        return globalParameters.getVersion().isPresent();
    }

    private boolean isUsageDisplayNeeded(GlobalParameters globalParameters, Optional<Object> optionalParsedCommandObject) {
        return globalParameters.getHelp().isPresent() || !optionalParsedCommandObject.isPresent();
    }
}
