package com.helospark.telnetsnake.game.server.game.badips.predicate;

import java.util.Arrays;
import java.util.function.Predicate;

import javax.annotation.PostConstruct;

import com.helospark.lightdi.annotation.Autowired;
import com.helospark.lightdi.annotation.Component;
import com.helospark.lightdi.annotation.Value;
import com.helospark.lightdi.exception.BeanCreationException;

@Component
public class BadIpsWhitelistContainingPredicate implements Predicate<String> {
    private final String[] whitelistedIps;

    @Autowired
    public BadIpsWhitelistContainingPredicate(@Value("${BAD_IPS_WHITELISTED_IPS}") String[] whitelistedIps) {
        this.whitelistedIps = whitelistedIps;
    }

    @PostConstruct
    public void verifyConfiguration() {
        try {
            Arrays.stream(whitelistedIps)
                    .forEach(whitelistEntry -> new IpAddressMatcher(whitelistEntry));
        } catch (Exception e) {
            throw new BeanCreationException("Configuration of whitelist is not valid, "
                    + "please check the following IPs and subranges " + Arrays.toString(whitelistedIps), e);
        }
    }

    @Override
    public boolean test(String ip) {
        return Arrays.stream(whitelistedIps)
                .filter(whitelist -> doesWhitelistMatch(whitelist, ip))
                .findFirst()
                .isPresent();
    }

    private boolean doesWhitelistMatch(String whitelistEntry, String ip) {
        return new IpAddressMatcher(whitelistEntry).matches(ip);
    }

}
