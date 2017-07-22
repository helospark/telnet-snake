package com.helospark.telnetsnake.game.repository.logfile;

import java.io.PrintWriter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.helospark.telnetsnake.game.server.game.domain.SnakeGameResultDto;

@Component
public class ResultToFileSaver {
    @Autowired
    private ResultFileNameGenerator resultFileNameGenerator;
    @Autowired
    private ResultFileOutputStreamProvider resultFileOutputStreamProvider;

    public String saveToFile(SnakeGameResultDto snakeGameResultDto) {
        String fileName = resultFileNameGenerator.generateFileName(snakeGameResultDto);
        PrintWriter printWriter = resultFileOutputStreamProvider.getPrintWriter(fileName);
        printWriter.println(snakeGameResultDto.getAllUserInputs());
        printWriter.flush();
        return fileName;
    }
}
