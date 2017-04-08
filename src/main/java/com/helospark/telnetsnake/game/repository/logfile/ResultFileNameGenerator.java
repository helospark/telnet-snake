package com.helospark.telnetsnake.game.repository.logfile;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.helospark.telnetsnake.game.domain.SnakeGameResultDto;

@Component
public class ResultFileNameGenerator {
    private static final String FILE_PATH_SEPARATOR = "/";
    @Value("${RESULT_FILE_PATH}")
    private String resultFilePath;

    public String generateFileName(SnakeGameResultDto snakeGameResultDto) {
        String ipWithUnderscores = createBasePathWithSlash(resultFilePath) + snakeGameResultDto.getIp().replace('.', '_');
        ipWithUnderscores += "_" + System.currentTimeMillis();
        return ipWithUnderscores;
    }

    private String createBasePathWithSlash(String filePath) {
        if (filePath.endsWith(FILE_PATH_SEPARATOR))
            return filePath;
        else
            return filePath + FILE_PATH_SEPARATOR;
    }

}