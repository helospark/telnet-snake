package com.helospark.telnetsnake.server.game.badips.predicate;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.helospark.lightdi.exception.BeanCreationException;
import com.helospark.telnetsnake.game.server.game.badips.predicate.BadIpsWhitelistContainingPredicate;

public class BadIpsWhitelistContainingPredicateTest {
    private BadIpsWhitelistContainingPredicate underTest;

    @Test(dataProvider = "whitelistTestDataProvider")
    public void test(String inputString, Boolean expectedResult) {
        // GIVEN
        underTest = new BadIpsWhitelistContainingPredicate(new String[] { "127.0.0.1", "192.168.0.0/24", "10.0.0.0/16" });

        // WHEN
        boolean result = underTest.test(inputString);

        // THEN
        assertThat(result, is(expectedResult));
    }

    @DataProvider(name = "whitelistTestDataProvider")
    private Object[][] whitelistTestDataProvider() {
        return new Object[][] {
                { "192.168.0.1", true },
                { "192.168.0.10", true },
                { "127.0.0.1", true },
                { "10.0.1.1", true },
                { "127.0.0.2", false },
                { "2.2.2.2", false },
        };
    }

    @Test(expectedExceptions = BeanCreationException.class, expectedExceptionsMessageRegExp = "Configuration of whitelist is not valid, please check the following IPs and subranges \\[not_IP\\].*")
    public void testWhitelistWithWrongConfigurationShouldThrowBeanCreationException() {
        // GIVEN
        underTest = new BadIpsWhitelistContainingPredicate(new String[] { "not_IP" });

        // WHEN
        underTest.verifyConfiguration();

        // THEN throws
    }
}