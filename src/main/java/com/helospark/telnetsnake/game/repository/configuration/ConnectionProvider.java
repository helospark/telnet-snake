package com.helospark.telnetsnake.game.repository.configuration;

import java.sql.Connection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ConnectionProvider {
    @Autowired
    private ConnectionFactory connectionFactory;
    private Connection connection = null;

    public Connection get() {
        if (connection == null) {
            synchronized (this) {
                if (connection == null) {
                    connection = connectionFactory.configureRepository();
                }
            }
        }
        return connection;
    }

    public boolean isInitialized() {
        return connection != null;
    }
}
