package com.helospark.telnetsnake.it;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.MockitoAnnotations.initMocks;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;

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
public class UsageTest {
    @Autowired
    private StartupExecutor startupExecutor;
    @Autowired
    private ScreenWriter screenWriter;
    @Captor
    private ArgumentCaptor<String> captor;

    @Before
    public void setUp() {
        initMocks(this);
    }

    @Test
    public void testNonExistentCommandShouldShowUsage() throws IOException {
        // GIVEN
        doNothing().when(screenWriter).printlnToScreen(captor.capture());

        // WHEN
        startupExecutor.start(new String[] { "nonexistent" });

        // THEN
        assertTrue(captor.getValue().startsWith("Usage:"));
    }

    @Test
    public void testDashHShouldShowUsage() throws IOException {
        // GIVEN
        doNothing().when(screenWriter).printlnToScreen(captor.capture());

        // WHEN
        startupExecutor.start(new String[] { "-h" });

        // THEN
        assertTrue(captor.getValue().startsWith("Usage:"));
    }

    @Test
    public void testHelpShouldShowUsage() throws IOException {
        // GIVEN
        doNothing().when(screenWriter).printlnToScreen(captor.capture());

        // WHEN
        startupExecutor.start(new String[] { "--help" });

        // THEN
        assertTrue(captor.getValue().startsWith("Usage:"));
    }
}
