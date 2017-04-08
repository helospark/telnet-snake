package com.helospark.telnetsnake.it;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.io.BufferedReader;
import java.net.ServerSocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.helospark.telnetsnake.server.socket.ServerSocketProvider;

@TestPropertySource(locations = "classpath:test_settings.properties")
@ContextConfiguration(locations = { "classpath:spring-context.xml" })
//@DirtiesContext
public class TestBaseOperation extends AbstractBaseTest {
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
}
