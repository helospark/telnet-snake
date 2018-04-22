package com.helospark.telnetsnake.game.repository.configuration;

import java.sql.Connection;
import java.sql.Statement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.helospark.lightdi.annotation.Autowired;
import com.helospark.lightdi.annotation.Component;
import com.helospark.lightdi.annotation.Value;
import com.helospark.lightdi.exception.BeanCreationException;
import com.helospark.telnetsnake.game.repository.ClassPathFileReader;

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
