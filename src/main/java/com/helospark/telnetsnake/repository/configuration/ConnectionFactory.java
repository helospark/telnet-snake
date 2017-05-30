package com.helospark.telnetsnake.repository.configuration;

import java.sql.Connection;
import java.sql.Statement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.helospark.telnetsnake.repository.ClassPathFileReader;

@Component
public class ConnectionFactory {
    private static final Logger LOGGER = LoggerFactory.getLogger(ConnectionFactory.class);
    @Autowired
    private ClassPathFileReader classPathFileReader;
    @Autowired
    private HsqlAutoServerManager hsqlAutoServerManager;

    @Value("${DATABASE_FILE_PATH}")
    private String databaseFilePath;

    public Connection configureRepository() {
        try {
            LOGGER.info("Initializing database connection...");
            Connection connection = hsqlAutoServerManager.createConnection();
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
            LOGGER.error("Error while initalizing connection", e);
        }
    }
}
