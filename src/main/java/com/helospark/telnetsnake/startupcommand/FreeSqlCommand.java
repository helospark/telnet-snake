package com.helospark.telnetsnake.startupcommand;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Objects;

import org.hsqldb.jdbc.JDBCClobClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.beust.jcommander.JCommander;
import com.helospark.telnetsnake.output.ScreenWriter;
import com.helospark.telnetsnake.parameters.FreeSqlCommandParameters;
import com.helospark.telnetsnake.repository.configuration.ConnectionProvider;

@Component
public class FreeSqlCommand implements StartupCommand {
    @Autowired
    private ConnectionProvider connectionProvider;
    @Autowired
    private ScreenWriter screenWriter;

    @Override
    public void execute(Object commandObject) {
        FreeSqlCommandParameters freeSqlCommandParameters = (FreeSqlCommandParameters) commandObject;

        if (freeSqlCommandParameters.getSql().isPresent()) {
            executeSingleCommand(freeSqlCommandParameters.getSql().get());
        } else {
            startInteractiveSqlTerminal();
        }

    }

    private void executeSingleCommand(String sql) {
        try {
            executeSql(sql);
        } catch (SQLException e) {
            screenWriter.printlnToScreen("Error executing SQL");
            screenWriter.printlnToScreen(e);
        }
    }

    private void startInteractiveSqlTerminal() {
        screenWriter.printlnToScreen("DB schema: snake_game_result (\n" +
                "ip VARCHAR(20) NOT NULL,\n" +
                "points INTEGER NOT NULL,\n" +
                "date DATETIME NOT NULL,\n" +
                "userInput CLOB NOT NULL)\n");
        screenWriter.printlnToScreen("Type 'exit' or ctrl+c to exit from interactive session");
        try {
            BufferedReader buffer = new BufferedReader(new InputStreamReader(System.in));
            while (true) {
                screenWriter.printToScreen("sql: ");
                String line = buffer.readLine();
                if (line.equalsIgnoreCase("exit")) {
                    break;
                }
                try {
                    executeSql(line);
                } catch (SQLException e) {
                    System.out.println("ERROR: " + e.getMessage());
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void executeSql(String sql) throws SQLException {
        Statement statement = connectionProvider.get().createStatement();
        boolean hasResultSet = statement.execute(sql);
        if (hasResultSet) {
            ResultSet result = statement.getResultSet();
            printResultSet(result);
        }
    }

    private void printResultSet(ResultSet result) throws SQLException {
        StringBuilder stringBuilder = new StringBuilder();
        int columnCount = result.getMetaData().getColumnCount();
        for (int i = 1; i < columnCount + 1; ++i) {
            stringBuilder.append(result.getMetaData().getColumnName(i) + "\t|\t");
        }
        stringBuilder.append("\n");

        while (result.next()) {
            for (int i = 1; i < columnCount + 1; ++i) {

                Object currentValue = result.getObject(i);
                if (currentValue instanceof JDBCClobClient) {
                    String value = ((JDBCClobClient) currentValue).getSubString(1, 1024);
                    stringBuilder.append(value + "\t|\t");
                } else {
                    stringBuilder.append(currentValue.toString() + "\t|\t");
                }
            }
            stringBuilder.append("\n");
        }
        screenWriter.printlnToScreen(stringBuilder.toString());
    }

    @Override
    public boolean canHandle(JCommander jCommander) {
        return Objects.equals(jCommander.getParsedCommand(), FreeSqlCommandParameters.COMMAND_NAME);
    }

    @Override
    public Object createCommandObject() {
        return new FreeSqlCommandParameters();
    }

}
