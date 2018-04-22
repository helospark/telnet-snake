package com.helospark.telnetsnake.game.repository;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.helospark.lightdi.annotation.Autowired;
import com.helospark.lightdi.annotation.Component;
import com.helospark.lightdi.annotation.Value;
import com.helospark.telnetsnake.game.repository.configuration.ConnectionFactory;
import com.helospark.telnetsnake.game.repository.configuration.ConnectionProvider;
import com.helospark.telnetsnake.game.server.game.domain.SnakeGameResultDto;

@Component
public class TopListService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ConnectionFactory.class);
    @Autowired
    private ConnectionProvider connectionProvider;
    @Autowired
    private ResultSetConverter resultSetConverter;
    @Autowired
    private SnakeGameResultDtoToplistComparator comparator;
    @Value("${TOPLIST_SIZE}")
    private int maximumToplistSize;

    private SortedSet<SnakeGameResultDto> toplistCache;

    @PostConstruct
    public void initializeInMemoryToplist() {
        try {
            List<SnakeGameResultDto> toplistAsList = getUncachedToplist(maximumToplistSize);
            toplistCache = new TreeSet<>(comparator);
            toplistCache.addAll(toplistAsList);
            LOGGER.info("Preinitialized inmemory toplist cache");
        } catch (Exception e) {
            LOGGER.error("Error while getting toplist", e);
            throw new RuntimeException("Error while getting toplist", e);
        }
    }

    public List<SnakeGameResultDto> getUncachedToplist(int size) {
        try {
            String update = "SELECT * FROM snake_game_result ORDER BY points DESC";
            Statement selectStatement = connectionProvider.get().createStatement();
            selectStatement.setMaxRows(size);
            ResultSet resultSet = selectStatement.executeQuery(update);
            return resultSetConverter.convert(resultSet);
        } catch (Exception e) {
            throw new RuntimeException("Unable to get toplist", e);
        }
    }

    public List<SnakeGameResultDto> getCachedTopList() {
        synchronized (toplistCache) {
            return new ArrayList<>(toplistCache);
        }
    }

    public void updateTopListWithResult(SnakeGameResultDto snakeGameResultDto) {
        synchronized (toplistCache) {
            if (toplistCache.size() < maximumToplistSize) {
                toplistCache.add(snakeGameResultDto);
            } else if (toplistCache.last().getPoints() < snakeGameResultDto.getPoints()) {
                toplistCache.remove(toplistCache.last());
                toplistCache.add(snakeGameResultDto);
            }
        }
    }
}
