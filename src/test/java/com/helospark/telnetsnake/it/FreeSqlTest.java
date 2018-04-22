package com.helospark.telnetsnake.it;

import static org.mockito.Mockito.verify;

import java.io.IOException;

import com.helospark.lightdi.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import com.helospark.telnetsnake.game.StartupExecutor;
import com.helospark.telnetsnake.game.output.ScreenWriter;
import com.helospark.telnetsnake.it.configuration.BaseMockOverrideConfiguration;
import com.helospark.telnetsnake.it.configuration.InMemoryDatabaseConfiguration;
import com.helospark.telnetsnake.it.configuration.OriginalApplicationContextConfiguration;

@TestPropertySource(locations = "classpath:test_settings.properties")
@ContextConfiguration(classes = { OriginalApplicationContextConfiguration.class, BaseMockOverrideConfiguration.class,
        InMemoryDatabaseConfiguration.class })
public class FreeSqlTest extends AbstractTestNGSpringContextTests {
    @Autowired
    private StartupExecutor startupExecutor;
    @Autowired
    private ScreenWriter screenWriter;

    @Test
    public void testFreeSql() throws IOException {
        // GIVEN

        // WHEN
        startupExecutor.start(new String[] { "sql", "-s", "SELECT * FROM snake_game_result WHERE ip='1.2.3.4'" });

        // THEN
        verify(screenWriter).printlnToScreen("IP\t|\tPOINTS\t|\tDATE\t|\tUSERINPUT\t|\t\n" +
                "1.2.3.4\t|\t10\t|\t2017-01-01 00:00:00.0\t|\tdata\t|\t\n");
    }
}
