package com.helospark.telnetsnake.game.server.game.badips;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.helospark.lightdi.annotation.Autowired;
import com.helospark.lightdi.annotation.Component;
import com.helospark.lightdi.annotation.Value;
import com.helospark.telnetsnake.game.server.game.badips.configuration.RestClient;
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
    private RestClient restClient;

    public void report(String ip) {
        LOGGER.info("Reporting '{}' to BadIps", ip);
        setActiveKey();
        sendIp(ip);
    }

    private void sendIp(String ip) {
        try {
            String url = badIpsReportUrl.replaceAll("\\{ip\\}", ip);
            BadIpsResponse responseEntity = restClient.getForObject(url, BadIpsResponse.class);
            LOGGER.debug("BadIps returned '{}'", responseEntity);
        } catch (Exception e) {
            LOGGER.error("Unable to send ip to BadIps", e);
        }
    }

    private void setActiveKey() {
        try {
            String url = badIpsSetApiKeyUrl.replaceAll("\\{key\\}", apiKey);
            BadIpsResponse responseEntity = restClient.getForObject(url, BadIpsResponse.class);
            LOGGER.debug("BadIps returned '{}'", responseEntity);
        } catch (Exception e) {
            LOGGER.error("Unable to set current key", e);
        }
    }
}
