package com.helospark.telnetsnake.repository.configuration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

import com.helospark.telnetsnake.repository.ClassPathFileReader;

@Configuration
public class RepositoryConfiguration {
    private static final Logger LOGGER = LoggerFactory.getLogger(RepositoryConfiguration.class);
    @Autowired
    private ClassPathFileReader classPathFileReader;

    @Value("${DATABASE_FILE_PATH}")
    private String databaseFilePath;

    @Bean(name = "databaseConnection")
    @Lazy
    public Connection configureRepository() {
        try {
            LOGGER.info("Initializing database connection...");
            String url = "jdbc:h2:" + databaseFilePath + ";AUTO_SERVER=TRUE";
            Connection connection = DriverManager.getConnection(url);
            initializeConnection(connection);
            LOGGER.info("Database connection initialized");
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
