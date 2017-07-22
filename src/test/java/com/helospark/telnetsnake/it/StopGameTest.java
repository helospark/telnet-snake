package com.helospark.telnetsnake.it;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.helospark.telnetsnake.game.StartupExecutor;
import com.helospark.telnetsnake.game.startupcommand.help.SocketFactory;

@TestPropertySource(locations = "classpath:test_settings.properties")
@ContextConfiguration(locations = { "classpath:spring-context.xml", "classpath:override-mocks.xml" })
public class StopGameTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private SocketFactory mockSocketFactory;

    @Autowired
    private StartupExecutor startupExecutor;

    @Mock
    private Socket socket;
    @Mock
    private InetSocketAddress inetSocketAddress;

    @BeforeMethod
    public void setUp() {
        initMocks(this);
    }

    @Test
    public void testShutdown() throws IOException {
        // GIVEN
        given(mockSocketFactory.createSocket()).willReturn(socket);
        given(mockSocketFactory.createInetSocketAddress("localhost", 0)).willReturn(inetSocketAddress);

        // WHEN
        startupExecutor.start(new String[] { "stop" });

        // THEN
        verify(mockSocketFactory).createSocket();
        verify(mockSocketFactory).createInetSocketAddress("localhost", 0);
        verify(socket).connect(inetSocketAddress);
    }
}
