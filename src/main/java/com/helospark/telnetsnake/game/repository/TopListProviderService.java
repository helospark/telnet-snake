package com.helospark.telnetsnake.game.repository;

import static java.util.Collections.emptyList;

import java.sql.Connection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.helospark.telnetsnake.game.domain.SnakeGameResultDto;

@Component
public class TopListProviderService {
    @Autowired
    private Connection connection;

    public List<SnakeGameResultDto> select(int limit) {
        // TODO: Fill this
        return emptyList();
    }

    public void saveResult(SnakeGameResultDto snakeGameResultDto) {
        // TODO: Fill this
    }
}
