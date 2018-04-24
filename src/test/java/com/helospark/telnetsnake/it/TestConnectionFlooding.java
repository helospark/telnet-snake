package com.helospark.telnetsnake.it;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.helospark.lightdi.annotation.Autowired;
import com.helospark.lightdi.annotation.PropertySource;
import com.helospark.lightdi.properties.Environment;
import com.helospark.lightdi.test.annotation.LightDiTest;
import com.helospark.lightdi.test.annotation.TestPropertySource;
import com.helospark.lightdi.test.junit4.LightDiJUnitTestRunner;
import com.helospark.telnetsnake.game.ApplicationConfiguration;
import com.helospark.telnetsnake.game.StartupExecutor;
import com.helospark.telnetsnake.game.server.socket.ServerSocketProvider;
import com.helospark.telnetsnake.it.configuration.InMemoryDatabaseConfiguration;

@RunWith(LightDiJUnitTestRunner.class)
@PropertySource(order = Environment.HIGHEST_PROPERTY_ORDER + 1, value = "classpath:test_settings.properties")
@TestPropertySource(order = Environment.HIGHEST_PROPERTY_ORDER, properties = "MAX_CONNECTION_FROM_SAME_IP=1")
@LightDiTest(classes = { ApplicationConfiguration.class, InMemoryDatabaseConfiguration.class,
        InMemoryDatabaseConfiguration.class })
public class TestConnectionFlooding extends StartGameAbstractBaseTest {

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

    @Override
    @After
    public void tearDown() {
        super.tearDown();
    }

    @Test
    public void testConnectionFloodingShouldOnlyAllowOneConnection() {
        try {
            // GIVEN first connection is created

            // WHEN
            Socket secondConnection = new Socket("localhost", serverSocket.getLocalPort());
            BufferedReader secondInputReader = new BufferedReader(
                    new InputStreamReader(secondConnection.getInputStream()));

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
            for (int i = 0; i < 100000 && !printWriter.checkError(); ++i) {
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
