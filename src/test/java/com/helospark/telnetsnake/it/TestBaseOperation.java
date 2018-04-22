package com.helospark.telnetsnake.it;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.BufferedReader;
import java.net.ServerSocket;
import java.util.List;

import com.helospark.lightdi.annotation.Autowired;
import com.helospark.lightdi.annotation.Bean;
import com.helospark.lightdi.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.helospark.telnetsnake.game.server.game.FoodGenerator;
import com.helospark.telnetsnake.game.server.game.domain.Coordinate;
import com.helospark.telnetsnake.game.server.socket.ServerSocketProvider;
import com.helospark.telnetsnake.it.TestBaseOperation.TestBaseOperationsITConfiguration;
import com.helospark.telnetsnake.it.configuration.InMemoryDatabaseConfiguration;
import com.helospark.telnetsnake.it.configuration.OriginalApplicationContextConfiguration;

@TestPropertySource(locations = "classpath:test_settings.properties")
@ContextConfiguration(classes = { OriginalApplicationContextConfiguration.class,
        TestBaseOperationsITConfiguration.class, InMemoryDatabaseConfiguration.class })
public class TestBaseOperation extends StartGameAbstractBaseTest {
    private static final String EXPECTED_FIRST_FRAME_WITHOUT_FOOD = "" +
            "Telnet snake! Use w,a,s,d,q commands (may need to press enter to flush)\n" +
            "_________________________________________\n" +
            "|                                        |\n" +
            "|                                        |\n" +
            "|                                        |\n" +
            "|                                        |\n" +
            "|                                        |\n" +
            "|                                        |\n" +
            "|                                        |\n" +
            "|                                        |\n" +
            "|                                        |\n" +
            "|                                        |\n" +
            "|    #XX                                 |\n" +
            "|                                        |\n" +
            "|                                        |\n" +
            "|                                        |\n" +
            "|                                        |\n" +
            "|                                        |\n" +
            "|                                        |\n" +
            "|                                        |\n" +
            "|                                        |\n" +
            "|                                        |\n" +
            "-----------------------------------------\n" +
            "Points: |0 | w,a,s,d,q: \n";

    private static final String EXPECTED_SECOND_FRAME_WITHOUT_FOOD = "" +
            "Telnet snake! Use w,a,s,d,q commands (may need to press enter to flush)\n" +
            "_________________________________________\n" +
            "|                                        |\n" +
            "|                                        |\n" +
            "|                                        |\n" +
            "|                                        |\n" +
            "|                                        |\n" +
            "|                                        |\n" +
            "|                                        |\n" +
            "|                                        |\n" +
            "|                                        |\n" +
            "|                                        |\n" +
            "|   #XX                                  |\n" +
            "|                                        |\n" +
            "|                                        |\n" +
            "|                                        |\n" +
            "|                                        |\n" +
            "|                                        |\n" +
            "|                                        |\n" +
            "|                                        |\n" +
            "|                                        |\n" +
            "|                                        |\n" +
            "-----------------------------------------\n" +
            "Points: |0 | w,a,s,d,q: \n";

    @Autowired
    private ServerSocketProvider serverSocketProvider;
    private ServerSocket serverSocket;

    @BeforeMethod
    public void setUp() {
        this.serverSocket = serverSocketProvider.provideActiveServerSocket();
        super.initialize(serverSocket);
    }

    @Override
    @AfterMethod
    public void tearDown() {
        super.tearDown();
    }

    @Test
    public void testConnectionAndFirstTwoFrames() {
        // GIVEN in setUp

        // WHEN
        String firstFrame = readFrame(inputReader);
        String secondFrame = readFrame(inputReader);

        // THEN
        assertThat(firstFrame.indexOf('O'), is(firstFrame.lastIndexOf('O'))); // Have a single food
        assertFrameEqualsWithoutRandomFood(firstFrame, EXPECTED_FIRST_FRAME_WITHOUT_FOOD);
        assertFrameEqualsWithoutRandomFood(secondFrame, EXPECTED_SECOND_FRAME_WITHOUT_FOOD);
    }

    private void assertFrameEqualsWithoutRandomFood(String frame, String expectedFrame) {
        assertThat(frame.replace('O', ' '), is(expectedFrame));
    }

    private String readFrame(BufferedReader in) {
        try {
            StringBuilder result = new StringBuilder();
            String line = "";
            while (!line.contains("Point")) {
                line = in.readLine();
                if (line == null) {
                    break;
                }
                if (!line.isEmpty()) {
                    result.append(line + "\n");
                }
            }
            return result.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Configuration
    public static class TestBaseOperationsITConfiguration {
        @Bean
        public FoodGenerator foodGenerator() {
            FoodGenerator foodGenerator = mock(FoodGenerator.class);
            when(foodGenerator.generateFood(any(List.class))).thenReturn(new Coordinate(0, 0));
            return foodGenerator;
        }
    }
}
