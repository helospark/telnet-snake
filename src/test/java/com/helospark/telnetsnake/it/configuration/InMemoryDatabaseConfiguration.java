package com.helospark.telnetsnake.it.configuration;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import com.helospark.telnetsnake.repository.ClassPathFileReader;
import com.helospark.telnetsnake.repository.configuration.ConnectionProvider;

@Configuration
public class InMemoryDatabaseConfiguration {
    private static final Logger LOGGER = LoggerFactory.getLogger(InMemoryDatabaseConfiguration.class);
    @Autowired
    private ClassPathFileReader classPathFileReader;

    @Bean
    @Primary
    public ConnectionProvider getConnection() throws SQLException {
        ConnectionProvider connectionProvider = mock(ConnectionProvider.class);
        given(connectionProvider.get()).willReturn(createMockConnection());
        return connectionProvider;
    }

    @Bean
    public Connection createMockConnection() throws SQLException {
        String url = "jdbc:h2:mem:test";
        Connection connection = DriverManager.getConnection(url);
        initializeConnection(connection);
        LOGGER.info("Created mock database");
        return connection;
    }

    private void initializeConnection(Connection connection) {
        try {
            String initialSql = classPathFileReader.readClasspathFile("/initialize_database.sql");
            Statement tableCreationStatement = connection.createStatement();
            tableCreationStatement.execute(initialSql);

            String mockDataToInitialize = "INSERT INTO snake_game_result VALUES ('1.2.3.4', 10, '2017-01-01', 'data')";

            Statement initializeStatement = connection.createStatement();
            initializeStatement.execute(mockDataToInitialize);

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
