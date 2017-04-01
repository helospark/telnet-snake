package com.helospark.telnetsnake.game.repository.configuration;

import java.sql.Connection;
import java.sql.DriverManager;

import org.springframework.beans.factory.BeanCreationException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HsqlConfiguration {

    @Bean(name = "sqliteConnection")
    public Connection configureHsql() {
        try {
            // TODO: change url based on properties file
            String url = "jdbc:sqlite:/tmp/database.db";
            return DriverManager.getConnection(url);
        } catch (Exception e) {
            throw new BeanCreationException("...", e);
        }
    }
}
