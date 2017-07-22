package com.helospark.telnetsnake.it;

import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

import java.io.IOException;

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
public class VersionTest extends AbstractTestNGSpringContextTests {
    @Autowired
    private StartupExecutor startupExecutor;
    @Autowired
    private ScreenWriter screenWriter;

    @BeforeMethod
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
