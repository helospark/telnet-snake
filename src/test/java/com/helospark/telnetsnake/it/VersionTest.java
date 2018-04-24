package com.helospark.telnetsnake.it;

import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

import java.io.IOException;

import org.junit.Before;
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
public class VersionTest {
    @Autowired
    private StartupExecutor startupExecutor;
    @Autowired
    private ScreenWriter screenWriter;

    @Before
    public void setUp() {
        initMocks(this);
    }

    @Test
    public void testVersion() throws IOException {
        // GIVEN

        // WHEN
        startupExecutor.start(new String[] { "-v" });

        // THEN
        verify(screenWriter).printlnToScreen("1.0.0-20170101000000");
    }

}
