package com.helospark.telnetsnake.it;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.helospark.telnetsnake.game.IpExtractor;

@TestPropertySource(locations = "classpath:test_settings.properties", properties = { "PORT=7777", "MAX_CONNECTIONS=1" })
@ContextConfiguration(locations = { "classpath:spring-context.xml", "classpath:override-mocks.xml" })
public class TestConnectionFloodingFromDifferentIps extends AbstractBaseTest {

    @Autowired
    private IpExtractor mockedIpExtractor;

    @Override
    @BeforeMethod
    public void setUp() {
        given(mockedIpExtractor.getIp(any(Socket.class)))
                .willReturn("192.168.0.1")
                .willReturn("192.168.0.2")
                .willReturn("192.168.0.3");
        super.setUp();
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
            Socket secondConnection = new Socket("localhost", 7777);
            BufferedReader secondInputReader = new BufferedReader(new InputStreamReader(secondConnection.getInputStream()));

            // THEN
            assertThat(secondInputReader.readLine(), is("Too many connections"));
            assertThat(secondInputReader.readLine(), is(nullValue()));
            secondConnection.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
