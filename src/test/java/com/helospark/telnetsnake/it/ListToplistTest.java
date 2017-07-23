package com.helospark.telnetsnake.it;

import static org.mockito.Mockito.verify;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
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
public class ListToplistTest extends AbstractTestNGSpringContextTests {
    @Autowired
    private StartupExecutor startupExecutor;
    @Autowired
    private ScreenWriter screenWriter;

    @Test
    public void testToplist() throws IOException {
        // GIVEN

        // WHEN
        startupExecutor.start(new String[] { "toplist", "-n", "1" });

        // THEN
        verify(screenWriter).printlnToScreen("1.2.3.4 10");
    }
}
