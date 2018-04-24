package com.helospark.telnetsnake.it;

import static org.junit.Assert.assertEquals;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;

import com.helospark.lightdi.annotation.Autowired;
import com.helospark.lightdi.test.annotation.LightDiTest;
import com.helospark.lightdi.test.annotation.TestPropertySource;
import com.helospark.lightdi.test.junit4.LightDiJUnitTestRunner;
import com.helospark.telnetsnake.game.ApplicationConfiguration;
import com.helospark.telnetsnake.game.repository.TopListService;
import com.helospark.telnetsnake.game.server.game.domain.SnakeGameResultDto;
import com.helospark.telnetsnake.it.configuration.InMemoryDatabaseConfiguration;

@RunWith(LightDiJUnitTestRunner.class)
@TestPropertySource(locations = { "classpath:bad_ips_mocked_settings.properties",
        "classpath:test_settings.properties" })
@LightDiTest(classes = { ApplicationConfiguration.class, InMemoryDatabaseConfiguration.class })
public class TopListServiceIT {
    @Autowired
    private TopListService topListService;

    @Test
    public void testToplistShouldContainDataFromDatabase() {
        // GIVEN
        List<SnakeGameResultDto> uncachedToplist = topListService.getUncachedToplist(3);

        // WHEN
        List<SnakeGameResultDto> toplist = topListService.getCachedTopList();

        // THEN
        assertEquals(toplist.size(), 1);
        assertEquals(toplist.get(0).getIp(), "1.2.3.4");
        assertEquals(toplist, uncachedToplist);
    }

    @Test
    public void testToplistShouldUpdateData() {
        // GIVEN
        topListService.updateTopListWithResult(buildSnakeGameResultWithPoints(20));
        topListService.updateTopListWithResult(buildSnakeGameResultWithPoints(30));

        // WHEN
        List<SnakeGameResultDto> toplist = topListService.getCachedTopList();

        // THEN
        assertEquals(toplist.size(), 3);
        assertEquals(toplist.get(0).getPoints(), 30);
        assertEquals(toplist.get(1).getPoints(), 20);
        assertEquals(toplist.get(2).getPoints(), 10);
    }

    @Test
    public void testToplistShouldUpdateDataAndShouldNotContainMoreThanLimit() {
        // GIVEN
        topListService.updateTopListWithResult(buildSnakeGameResultWithPoints(20));
        topListService.updateTopListWithResult(buildSnakeGameResultWithPoints(30));
        topListService.updateTopListWithResult(buildSnakeGameResultWithPoints(40));

        // WHEN
        List<SnakeGameResultDto> toplist = topListService.getCachedTopList();

        // THEN
        assertEquals(toplist.size(), 3);
        assertEquals(toplist.get(0).getPoints(), 40);
        assertEquals(toplist.get(1).getPoints(), 30);
        assertEquals(toplist.get(2).getPoints(), 20);
    }

    private SnakeGameResultDto buildSnakeGameResultWithPoints(int points) {
        return SnakeGameResultDto.builder()
                .withAllUserInputs("")
                .withIp("2.3.4.5")
                .withLocalDateTime(LocalDateTime.now())
                .withPoints(points)
                .build();
    }

}
