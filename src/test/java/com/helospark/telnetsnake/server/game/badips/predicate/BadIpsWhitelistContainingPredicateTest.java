package com.helospark.telnetsnake.server.game.badips.predicate;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import com.helospark.telnetsnake.game.server.game.badips.predicate.BadIpsWhitelistContainingPredicate;

@RunWith(Parameterized.class)
public class BadIpsWhitelistContainingPredicateTest {
    private BadIpsWhitelistContainingPredicate underTest;

    private String inputString;
    private boolean expectedResult;

    public BadIpsWhitelistContainingPredicateTest(String inputString, boolean expectedResult) {
        this.inputString = inputString;
        this.expectedResult = expectedResult;
    }

    @Test
    public void test() {
        // GIVEN
        underTest = new BadIpsWhitelistContainingPredicate(new String[] { "127.0.0.1", "192.168.0.0/24", "10.0.0.0/16" });

        // WHEN
        boolean result = underTest.test(inputString);

        // THEN
        assertThat(result, is(expectedResult));
    }

    @Parameters
    public static Collection<Object[]> whitelistTestDataProvider() {
        return Arrays.asList(new Object[][] {
                { "192.168.0.1", true },
                { "192.168.0.10", true },
                { "127.0.0.1", true },
                { "10.0.1.1", true },
                { "127.0.0.2", false },
                { "2.2.2.2", false },
        });
    }

}