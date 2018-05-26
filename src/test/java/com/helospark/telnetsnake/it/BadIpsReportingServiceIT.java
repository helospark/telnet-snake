package com.helospark.telnetsnake.it;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathEqualTo;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.http.RequestMethod;
import com.github.tomakehurst.wiremock.matching.RequestPatternBuilder;
import com.helospark.lightdi.annotation.Autowired;
import com.helospark.lightdi.test.annotation.LightDiTest;
import com.helospark.lightdi.test.annotation.TestPropertySource;
import com.helospark.lightdi.test.junit4.LightDiJUnitTestRunner;
import com.helospark.telnetsnake.game.ApplicationConfiguration;
import com.helospark.telnetsnake.game.server.game.badips.BadIpsReportingService;
import com.helospark.telnetsnake.it.configuration.InMemoryDatabaseConfiguration;

@RunWith(LightDiJUnitTestRunner.class)
@TestPropertySource(locations = "classpath:bad_ips_mocked_settings.properties")
@LightDiTest(classes = { ApplicationConfiguration.class, InMemoryDatabaseConfiguration.class })
public class BadIpsReportingServiceIT {
    // dynamic port would be nice here
    private static final int WIREMOCK_TEST_PORT = 32323;

    @Autowired
    private BadIpsReportingService badIpsReportingService;

    private WireMockServer wireMockServer;
    private WireMock wireMock;

    @Before
    public void setUp() {
        wireMockServer = new WireMockServer(wireMockConfig().port(32323));
        wireMockServer.start();
        wireMock = new WireMock("localhost", WIREMOCK_TEST_PORT);

        String successResponse = ""
                + "{"
                + "   \"err\": \"\","
                + "   \"suc\": \"OK\","
                + "   \"key\": \"Some other data\""
                + "}";
        wireMock.register(get(urlPathEqualTo("/add/telnet/1.2.3.4"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(successResponse)));

        wireMock.register(get(urlPathEqualTo("/set/key/key_value"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(successResponse)));
    }

    @After
    public void tearDown() {
        wireMockServer.stop();
    }

    @Test
    public void testReportShouldSetTheUrlAndSendTheIp() {
        // GIVEN

        // WHEN
        badIpsReportingService.report("1.2.3.4");

        // THEN
        wireMock.verifyThat(
                new RequestPatternBuilder(RequestMethod.GET,
                        urlPathEqualTo("/set/key/key_value")));
        wireMock.verifyThat(
                new RequestPatternBuilder(RequestMethod.GET,
                        urlPathEqualTo("/add/telnet/1.2.3.4")));
    }

}
