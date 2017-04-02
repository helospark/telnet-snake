package com.helospark.telnetsnake.it;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.io.File;
import java.net.ServerSocket;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@TestPropertySource(locations = "classpath:test_settings.properties")
@ContextConfiguration(locations = { "classpath:spring-context.xml" })
@DirtiesContext
public class TestCodeInjection extends AbstractBaseTest {
    @Autowired
    private Connection connection;

    @Autowired
    private ServerSocket serverSocket;

    @BeforeMethod
    public void setUp() {
        super.initialize(serverSocket);
    }

    @AfterMethod
    @Override
    public void tearDown() {
        // TODO Auto-generated method stub
        super.tearDown();
    }

    @Test
    public void testShellCodeInjectionShouldNotRunCode() {
        // GIVEN

        // WHEN
        printWriter.println("touch /tmp/test_file");
        printWriter.println("q");

        // THEN
        assertThat(new File("/tmp/test_file").exists(), is(false));
    }

    @Test
    public void testJavaInjectionShouldNotRunCode() {
        // GIVEN

        // WHEN
        printWriter.println("File file = new File(\"/tmp/test_file\").createNewFile()");
        printWriter.println("q");

        // THEN
        assertThat(new File("/tmp/test_file").exists(), is(false));
    }

    @Test
    public void testSqlInjectShouldNotRunSql() throws SQLException {
        // GIVEN

        // WHEN
        printWriter.println("DROP TABLE snake_game_result;");
        printWriter.println("q");

        // THEN
        assertDatabaseContainsAtLeastOneEntries();
    }

    private void assertDatabaseContainsAtLeastOneEntries() throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery("SELECT * FROM snake_game_result");
        assertThat(rs.next(), is(true));
    }
}
