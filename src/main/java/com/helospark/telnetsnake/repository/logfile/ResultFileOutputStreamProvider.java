package com.helospark.telnetsnake.repository.logfile;

import java.io.FileOutputStream;
import java.io.PrintWriter;

import org.springframework.stereotype.Component;

@Component
public class ResultFileOutputStreamProvider {

    public PrintWriter getPrintWriter(String outputFileName) {
        try {
            return new PrintWriter(new FileOutputStream(outputFileName, true));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
