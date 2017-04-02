package com.helospark.telnetsnake.it;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@TestPropertySource(locations = "classpath:test_settings.properties", properties = { "MAX_CONNECTION_FROM_SAME_IP=1" })
@ContextConfiguration(locations = { "classpath:spring-context.xml" })
@DirtiesContext
public class TestConnectionFlooding extends AbstractBaseTest {

    @Autowired
    private ServerSocket serverSocket;

    @BeforeMethod
    public void setUp() {
        super.initialize(serverSocket);
    }

    @Override
    @AfterMethod
    public void tearDown() {
        super.tearDown();
    }

    @Test
    public void testConnectionFloodingShouldOnlyAllowOneConnection() {
        try {
            // GIVEN first connection is created

            // WHEN
            Socket secondConnection = new Socket("localhost", serverSocket.getLocalPort());
            BufferedReader secondInputReader = new BufferedReader(new InputStreamReader(secondConnection.getInputStream()));

            // THEN
            assertThat(secondInputReader.readLine(), is("Too many connections"));
            assertThat(secondInputReader.readLine(), is(nullValue()));
            secondConnection.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testSendingTooMuchDataServerShouldDisconnect() {
        try {
            // GIVEN

            // WHEN
            for (int i = 0; i < 10000 && !printWriter.checkError(); ++i) {
                printWriter.print("data");
                printWriter.flush();
            }

            // THEN
            assertThat(printWriter.checkError(), is(true));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
