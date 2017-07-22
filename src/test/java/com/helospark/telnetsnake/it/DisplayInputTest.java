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

@TestPropertySource(locations = "classpath:test_settings.properties")
@ContextConfiguration(locations = { "classpath:spring-context.xml", "classpath:override-mocks.xml" })
public class DisplayInputTest extends AbstractTestNGSpringContextTests {
    @Autowired
    private StartupExecutor startupExecutor;
    @Autowired
    private ScreenWriter screenWriter;

    @Test
    public void testDisplay() throws IOException {
        // GIVEN

        // WHEN
        startupExecutor.start(new String[] { "display", "-n", "1" });

        // THEN
        verify(screenWriter).printlnToScreen("1.2.3.4\t2017-01-01T00:00\t10\n" +
                "data\n" +
                "------------\n");
    }
}
