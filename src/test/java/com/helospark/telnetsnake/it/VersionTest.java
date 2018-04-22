package com.helospark.telnetsnake.it;

import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

import java.io.IOException;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.helospark.lightdi.annotation.Autowired;
import com.helospark.lightdi.annotation.PropertySource;
import com.helospark.lightdi.test.annotation.LightDiTest;
import com.helospark.telnetsnake.game.StartupExecutor;
import com.helospark.telnetsnake.game.output.ScreenWriter;
import com.helospark.telnetsnake.it.configuration.BaseMockOverrideConfiguration;
import com.helospark.telnetsnake.it.configuration.InMemoryDatabaseConfiguration;
import com.helospark.telnetsnake.it.configuration.OriginalApplicationContextConfiguration;

@PropertySource(order=0, value = "classpath:test_settings.properties")
@LightDiTest(rootPackage = "", classes = { OriginalApplicationContextConfiguration.class, BaseMockOverrideConfiguration.class,
        InMemoryDatabaseConfiguration.class })
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
