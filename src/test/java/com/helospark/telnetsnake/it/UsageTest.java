package com.helospark.telnetsnake.it;

import static org.mockito.Mockito.doNothing;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.testng.Assert.assertTrue;

import java.io.IOException;

import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.helospark.telnetsnake.game.StartupExecutor;
import com.helospark.telnetsnake.game.output.ScreenWriter;

@TestPropertySource(locations = "classpath:test_settings.properties")
@ContextConfiguration(locations = { "classpath:spring-context.xml", "classpath:override-mocks.xml" })
public class UsageTest extends AbstractTestNGSpringContextTests {
    @Autowired
    private StartupExecutor startupExecutor;
    @Autowired
    private ScreenWriter screenWriter;
    @Captor
    private ArgumentCaptor<String> captor;

    @BeforeMethod
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
