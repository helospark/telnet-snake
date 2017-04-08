package com.helospark.telnetsnake.startupcommand;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.helospark.telnetsnake.StartupCommand;

@Component
public class FreeSqlCommand implements StartupCommand {
    @Autowired
    @Lazy
    private Connection connection;

    @Override
    public void execute(List<String> args) {
        try {
            BufferedReader buffer = new BufferedReader(new InputStreamReader(System.in));
            while (true) {
                System.out.print("sql: ");
                System.out.flush();
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
        Statement statement = connection.createStatement();
        boolean hasResultSet = statement.execute(sql);
        if (hasResultSet) {
            ResultSet result = statement.getResultSet();
            printResultSet(result);
        }
    }

    private void printResultSet(ResultSet result) throws SQLException {
        int columnCount = result.getMetaData().getColumnCount();
        for (int i = 1; i < columnCount + 1; ++i) {
            System.out.print(result.getMetaData().getColumnName(i) + "\t|\t");
        }
        System.out.println();

        while (result.next()) {
            for (int i = 1; i < columnCount + 1; ++i) {
                System.out.print(result.getObject(i) + "\t|\t");
            }
            System.out.println();
        }
    }

    @Override
    public boolean canHandle(Optional<String> commandName) {
        return commandName.isPresent() && commandName.get().equals("freesql");
    }

}
