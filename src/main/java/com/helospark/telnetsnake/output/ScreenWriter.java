package com.helospark.telnetsnake.output;

import java.sql.SQLException;

import org.springframework.stereotype.Component;

@Component
public class ScreenWriter {

    public void printlnToScreen(String data) {
        System.out.println(data);
    }

    public void printToScreen(String data) {
        System.out.print(data);
        System.out.flush();
    }

    public void printlnToScreen(SQLException e) {
        e.printStackTrace();
    }
}
