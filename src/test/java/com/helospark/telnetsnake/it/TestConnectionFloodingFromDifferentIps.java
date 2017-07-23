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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.helospark.telnetsnake.game.server.game.IpExtractor;
import com.helospark.telnetsnake.game.server.socket.ServerSocketProvider;
import com.helospark.telnetsnake.it.configuration.BaseMockOverrideConfiguration;
import com.helospark.telnetsnake.it.configuration.InMemoryDatabaseConfiguration;
import com.helospark.telnetsnake.it.configuration.OriginalApplicationContextConfiguration;

@TestPropertySource(locations = "classpath:test_settings.properties", properties = { "MAX_CONNECTIONS=1" })
@ContextConfiguration(classes = { OriginalApplicationContextConfiguration.class, BaseMockOverrideConfiguration.class,
        InMemoryDatabaseConfiguration.class })
@DirtiesContext
public class TestConnectionFloodingFromDifferentIps extends StartGameAbstractBaseTest {

    @Autowired
    private IpExtractor mockedIpExtractor;

    @Autowired
    private ServerSocketProvider serverSocketProvider;

    private ServerSocket serverSocket;

    @BeforeMethod
    public void setUp() {
        serverSocket = serverSocketProvider.provideActiveServerSocket();
        given(mockedIpExtractor.getIp(any(Socket.class)))
                .willReturn("192.168.0.1")
                .willReturn("192.168.0.2")
                .willReturn("192.168.0.3");
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
