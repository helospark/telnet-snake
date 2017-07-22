package com.helospark.telnetsnake.game.server.game.badips;

import java.net.URI;
import java.util.Collections;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.helospark.telnetsnake.game.server.game.badips.domain.BadIpsResponse;

@Component
public class BadIpsReportingService {
    private static final Logger LOGGER = LoggerFactory.getLogger(BadIpsReportingService.class);
    @Value("${BAD_IPS_API_KEY}")
    private String badIpsApiKey;
    @Value("${BAD_IPS_REPORT_URL}")
    private String badIpsReportUrl;
    @Value("${BAD_IPS_API_KEY}")
    private String apiKey;
    @Value("${BAD_IPS_KEY_SETTING_URL}")
    private String badIpsSetApiKeyUrl;
    @Autowired
    private RestTemplate restTemplate;

    public void report(String ip) {
        LOGGER.info("Reporting '{}' to BadIps", ip);
        setActiveKey();
        sendIp(ip);
    }

    private void sendIp(String ip) {
        try {
            URI uri = UriComponentsBuilder.fromUriString(badIpsReportUrl)
                    .buildAndExpand(Collections.singletonMap("ip", ip))
                    .toUri();
            ResponseEntity<BadIpsResponse> responseEntity = restTemplate.getForEntity(uri, BadIpsResponse.class);
            LOGGER.debug("BadIps returned '{}'", responseEntity);
        } catch (Exception e) {
            LOGGER.error("Unable to send ip to BadIps", e);
        }
    }

    private void setActiveKey() {
        try {
            URI uri = UriComponentsBuilder.fromUriString(badIpsSetApiKeyUrl)
                    .buildAndExpand(Collections.singletonMap("key", apiKey))
                    .toUri();
            ResponseEntity<BadIpsResponse> responseEntity = restTemplate.getForEntity(uri, BadIpsResponse.class);
            LOGGER.debug("BadIps returned '{}'", responseEntity);
        } catch (Exception e) {
            LOGGER.error("Unable to set current key", e);
        }
    }
}
