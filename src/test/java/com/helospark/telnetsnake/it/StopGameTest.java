package com.helospark.telnetsnake.it;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;

import com.helospark.lightdi.annotation.Autowired;
import com.helospark.lightdi.test.annotation.LightDiTest;
import com.helospark.lightdi.test.annotation.TestPropertySource;
import com.helospark.lightdi.test.junit4.LightDiJUnitTestRunner;
import com.helospark.telnetsnake.game.ApplicationConfiguration;
import com.helospark.telnetsnake.game.StartupExecutor;
import com.helospark.telnetsnake.game.startupcommand.help.SocketFactory;
import com.helospark.telnetsnake.it.configuration.BaseMockOverrideConfiguration;
import com.helospark.telnetsnake.it.configuration.InMemoryDatabaseConfiguration;

@RunWith(LightDiJUnitTestRunner.class)
@TestPropertySource(locations = "classpath:test_settings.properties")
@LightDiTest(classes = { ApplicationConfiguration.class, BaseMockOverrideConfiguration.class,
        InMemoryDatabaseConfiguration.class })
public class StopGameTest {

    @Autowired
    private SocketFactory mockSocketFactory;

    @Autowired
    private StartupExecutor startupExecutor;

    @Mock
    private Socket socket;
    @Mock
    private InetSocketAddress inetSocketAddress;

    @Before
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
