package com.helospark.telnetsnake.it;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.io.File;
import java.net.ServerSocket;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.helospark.lightdi.annotation.Autowired;
import com.helospark.lightdi.test.annotation.LightDiTest;
import com.helospark.lightdi.test.annotation.TestPropertySource;
import com.helospark.lightdi.test.junit4.LightDiJUnitTestRunner;
import com.helospark.telnetsnake.game.ApplicationConfiguration;
import com.helospark.telnetsnake.game.StartupExecutor;
import com.helospark.telnetsnake.game.repository.configuration.ConnectionProvider;
import com.helospark.telnetsnake.game.server.socket.ServerSocketProvider;
import com.helospark.telnetsnake.it.configuration.InMemoryDatabaseConfiguration;

@RunWith(LightDiJUnitTestRunner.class)
@TestPropertySource(locations = "classpath:test_settings.properties")
@LightDiTest(classes = { ApplicationConfiguration.class, InMemoryDatabaseConfiguration.class,
        InMemoryDatabaseConfiguration.class })
public class TestCodeInjection extends StartGameAbstractBaseTest {
    @Autowired
    private ConnectionProvider connectionProvider;

    @Autowired
    private ServerSocketProvider serverSocketProvider;
    private ServerSocket serverSocket;
    @Autowired
    private StartupExecutor startupExecutor;

    @Before
    public void setUp() {
        serverSocket = serverSocketProvider.provideActiveServerSocket();
        super.initialize(startupExecutor, serverSocket);
    }

    @After
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
        Statement statement = connectionProvider.get().createStatement();
        ResultSet rs = statement.executeQuery("SELECT * FROM snake_game_result");
        assertThat(rs.next(), is(true));
    }

}
