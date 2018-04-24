package com.helospark.telnetsnake.it;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.helospark.lightdi.annotation.Autowired;
import com.helospark.lightdi.annotation.Bean;
import com.helospark.lightdi.annotation.Configuration;
import com.helospark.lightdi.annotation.Primary;
import com.helospark.lightdi.test.annotation.LightDiTest;
import com.helospark.lightdi.test.annotation.TestPropertySource;
import com.helospark.lightdi.test.junit4.LightDiJUnitTestRunner;
import com.helospark.telnetsnake.game.ApplicationConfiguration;
import com.helospark.telnetsnake.game.StartupExecutor;
import com.helospark.telnetsnake.game.server.game.FoodGenerator;
import com.helospark.telnetsnake.game.server.game.domain.Coordinate;
import com.helospark.telnetsnake.game.server.socket.ServerSocketProvider;
import com.helospark.telnetsnake.it.TestToplistIsDisplayOnGameEndIT.TestToplistIsDisplayOnGameEndITConfiguration;
import com.helospark.telnetsnake.it.configuration.InMemoryDatabaseConfiguration;

@RunWith(LightDiJUnitTestRunner.class)
@TestPropertySource(locations = "classpath:test_settings.properties")
@LightDiTest(classes = { ApplicationConfiguration.class, TestToplistIsDisplayOnGameEndITConfiguration.class,
        InMemoryDatabaseConfiguration.class })
public class TestToplistIsDisplayOnGameEndIT extends StartGameAbstractBaseTest {
    @Autowired
    private ServerSocketProvider serverSocketProvider;
    @Autowired
    private StartupExecutor startupExecutor;

    private ServerSocket serverSocket;

    @Before
    public void setUp() {
        serverSocket = serverSocketProvider.provideActiveServerSocket();
        super.initialize(startupExecutor, serverSocket);
    }

    @After
    @Override
    public void tearDown() {
        // TODO Auto-generated method stub
        super.tearDown();
    }

    @Test
    public void testShellCodeInjectionShouldNotRunCode() throws IOException {
        // GIVEN

        // WHEN
        printWriter.println("q");

        // THEN
        String dataSentBack = readAllDataSentBack();
        assertThat(dataSentBack, containsString("Thanx for playing! You finished with 0 points! Here is the toplist:"));
        assertThat(dataSentBack, containsString("1.\t10"));
        assertThat(dataSentBack, containsString("2.\t0 <- This is you (based on IP)"));
    }

    private String readAllDataSentBack() throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        String line = "";
        while ((line = inputReader.readLine()) != null) {
            stringBuilder.append(line + "\n");
        }
        return stringBuilder.toString();
    }

    @Configuration
    public static class TestToplistIsDisplayOnGameEndITConfiguration {
        @Bean
        @Primary
        public FoodGenerator foodGenerator() {
            FoodGenerator foodGenerator = mock(FoodGenerator.class);
            when(foodGenerator.generateFood(any(List.class))).thenReturn(new Coordinate(0, 0));
            return foodGenerator;
        }
    }
}
