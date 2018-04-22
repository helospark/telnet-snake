package com.helospark.telnetsnake.game.repository.logfile;

import java.io.PrintWriter;

import com.helospark.lightdi.annotation.Autowired;
import com.helospark.lightdi.annotation.Component;

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
