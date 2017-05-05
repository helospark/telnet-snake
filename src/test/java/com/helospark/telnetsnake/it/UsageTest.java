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

import com.helospark.telnetsnake.StartupExecutor;
import com.helospark.telnetsnake.output.ScreenWriter;

@TestPropertySource(locations = "classpath:test_settings.properties")
@ContextConfiguration(locations = { "classpath:spring-context.xml", "classpath:override-mocks.xml" })
public class UsageTest extends AbstractTestNGSpringContextTests {
    private static final String USAGE_STRING = "java -jar jarfile [options] command [command options]\n" +
            "  Options:\n" +
            "    -h, --help\n" +
            "      Display usage\n" +
            "  Commands:\n" +
            "    start      Start server\n" +
            "      Usage: start\n" +
            "\n" +
            "    sql      Run SQL commands\n" +
            "      Usage: sql [options]\n" +
            "        Options:\n" +
            "          -s, --sql\n" +
            "            SQL command, if absent interactive SQL terminal is shown\n" +
            "\n" +
            "    display      Display games\n" +
            "      Usage: display [options]\n" +
            "        Options:\n" +
            "          -n, -l, --limit\n" +
            "            Number of games to display (if not specified all)\n" +
            "\n" +
            "    toplist      Display toplist\n" +
            "      Usage: toplist [options]\n" +
            "        Options:\n" +
            "          -n, -l, --limit\n" +
            "            Number of entries to display\n" +
            "\n" +
            "    stop      Stop the running server\n" +
            "      Usage: stop";
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
