package com.helospark.telnetsnake.it;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathEqualTo;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.http.RequestMethod;
import com.github.tomakehurst.wiremock.matching.RequestPatternBuilder;
import com.helospark.telnetsnake.server.game.badips.BadIpsReportingService;

@TestPropertySource(locations = "classpath:bad_ips_mocked_settings.properties")
@ContextConfiguration(locations = { "classpath:spring-context.xml" })
public class BadIpsReportingServiceIT extends AbstractTestNGSpringContextTests {
    // dynamic port would be nice here
    private static final int WIREMOCK_TEST_PORT = 32323;

    @Autowired
    private BadIpsReportingService badIpsReportingService;

    private WireMockServer wireMockServer;
    private WireMock wireMock;

    @BeforeMethod
    public void setUp() {
        wireMockServer = new WireMockServer(wireMockConfig().port(32323));
        wireMockServer.start();
        wireMock = new WireMock("localhost", WIREMOCK_TEST_PORT);

        String successResponse = ""
                + "{"
                + "   \"err\": \"\","
                + "   \"suc\": \"OK\""
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

    @AfterMethod
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
