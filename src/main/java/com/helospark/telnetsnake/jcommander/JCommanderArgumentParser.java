package com.helospark.telnetsnake.jcommander;

import java.util.Optional;

import org.springframework.stereotype.Component;

import com.beust.jcommander.JCommander;
import com.helospark.telnetsnake.parameters.GlobalParameters;

@Component
public class JCommanderArgumentParser {
    private final JCommanderCommandObjectExtractor jCommanderCommandObjectExtractor;
    private final JCommanderUsagePrinter jCommanderUsagePrinter;

    public JCommanderArgumentParser(JCommanderCommandObjectExtractor jCommanderCommandObjectExtractor, JCommanderUsagePrinter jCommanderUsagePrinter) {
        this.jCommanderCommandObjectExtractor = jCommanderCommandObjectExtractor;
        this.jCommanderUsagePrinter = jCommanderUsagePrinter;
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

        if (isUsageDisplayNeeded(globalParameters, optionalParsedCommandObject)) {
            jCommanderUsagePrinter.printUsage(jCommander);
            return Optional.empty();
        }
        return optionalParsedCommandObject;
    }

    private boolean isUsageDisplayNeeded(GlobalParameters globalParameters, Optional<Object> optionalParsedCommandObject) {
        return globalParameters.getHelp().isPresent() || !optionalParsedCommandObject.isPresent();
    }
}
