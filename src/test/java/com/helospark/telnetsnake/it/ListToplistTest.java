package com.helospark.telnetsnake.it;

import static org.mockito.Mockito.verify;

import java.io.IOException;

import org.junit.Test;
import org.junit.runner.RunWith;

import com.helospark.lightdi.annotation.Autowired;
import com.helospark.lightdi.test.annotation.LightDiTest;
import com.helospark.lightdi.test.annotation.TestPropertySource;
import com.helospark.lightdi.test.junit4.LightDiJUnitTestRunner;
import com.helospark.telnetsnake.game.ApplicationConfiguration;
import com.helospark.telnetsnake.game.StartupExecutor;
import com.helospark.telnetsnake.game.output.ScreenWriter;
import com.helospark.telnetsnake.it.configuration.BaseMockOverrideConfiguration;
import com.helospark.telnetsnake.it.configuration.InMemoryDatabaseConfiguration;

@RunWith(LightDiJUnitTestRunner.class)
@TestPropertySource(locations = "classpath:test_settings.properties")
@LightDiTest(classes = { ApplicationConfiguration.class, BaseMockOverrideConfiguration.class,
        InMemoryDatabaseConfiguration.class })
public class ListToplistTest {
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
