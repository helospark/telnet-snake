package com.helospark.telnetsnake.game.repository.configuration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.helospark.telnetsnake.game.repository.ClassPathFileReader;

@Configuration
public class SqliteConfiguration {
    @Autowired
    private ClassPathFileReader classPathFileReader;

    @Value("${DATABASE_FILE_PATH}")
    private String databaseFilePath;

    @Bean(name = "sqliteConnection")
    public Connection configureHsql() {
        try {
            String url = "jdbc:sqlite:" + databaseFilePath;
            Connection connection = DriverManager.getConnection(url);
            initializeConnection(connection);
            return connection;
        } catch (Exception e) {
            throw new BeanCreationException("...", e);
        }
    }

    private void initializeConnection(Connection connection) {
        try {
            String initialSql = classPathFileReader.readClasspathFile("/initialize_database.sql");
            Statement statement = connection.createStatement();
            statement.execute(initialSql);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
