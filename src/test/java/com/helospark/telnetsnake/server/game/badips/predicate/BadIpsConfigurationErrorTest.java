package com.helospark.telnetsnake.server.game.badips.predicate;

import org.junit.Test;

import com.helospark.lightdi.exception.BeanCreationException;
import com.helospark.telnetsnake.game.server.game.badips.predicate.BadIpsWhitelistContainingPredicate;

public class BadIpsConfigurationErrorTest {
    private BadIpsWhitelistContainingPredicate underTest;

    @Test(expected = BeanCreationException.class)
    public void testWhitelistWithWrongConfigurationShouldThrowBeanCreationException() {
        // GIVEN
        underTest = new BadIpsWhitelistContainingPredicate(new String[] { "not_IP" });

        // WHEN
        underTest.verifyConfiguration();

        // THEN throws
    }
}
