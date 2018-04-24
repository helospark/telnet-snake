package com.helospark.telnetsnake.it;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;

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
import com.helospark.telnetsnake.game.server.game.IpExtractor;
import com.helospark.telnetsnake.game.server.socket.ServerSocketProvider;
import com.helospark.telnetsnake.it.configuration.BaseMockOverrideConfiguration;
import com.helospark.telnetsnake.it.configuration.InMemoryDatabaseConfiguration;

@RunWith(LightDiJUnitTestRunner.class)
@TestPropertySource(order = Environment.HIGHEST_PROPERTY_ORDER, properties = "MAX_CONNECTIONS=1")
@PropertySource(order = Environment.HIGHEST_PROPERTY_ORDER + 1, value = "classpath:test_settings.properties")
@LightDiTest(classes = { ApplicationConfiguration.class, BaseMockOverrideConfiguration.class,
        InMemoryDatabaseConfiguration.class })
public class TestConnectionFloodingFromDifferentIps extends StartGameAbstractBaseTest {

    @Autowired
    private IpExtractor mockedIpExtractor;
    @Autowired
    private StartupExecutor startupExecutor;

    @Autowired
    private ServerSocketProvider serverSocketProvider;

    private ServerSocket serverSocket;

    @Before
    public void setUp() {
        serverSocket = serverSocketProvider.provideActiveServerSocket();
        given(mockedIpExtractor.getIp(any(Socket.class)))
                .willReturn("192.168.0.1")
                .willReturn("192.168.0.2")
                .willReturn("192.168.0.3");
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

}
