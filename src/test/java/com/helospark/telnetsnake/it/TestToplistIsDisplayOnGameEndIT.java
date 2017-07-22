package com.helospark.telnetsnake.it;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.helospark.telnetsnake.game.server.game.FoodGenerator;
import com.helospark.telnetsnake.game.server.game.domain.Coordinate;
import com.helospark.telnetsnake.game.server.socket.ServerSocketProvider;
import com.helospark.telnetsnake.it.TestToplistIsDisplayOnGameEndIT.TestToplistIsDisplayOnGameEndITConfiguration;
import com.helospark.telnetsnake.it.configuration.InMemoryDatabaseConfiguration;

@TestPropertySource(locations = "classpath:test_settings.properties")
@ContextConfiguration(classes = { TestToplistIsDisplayOnGameEndITConfiguration.class,
        InMemoryDatabaseConfiguration.class })
@DirtiesContext
public class TestToplistIsDisplayOnGameEndIT extends StartGameAbstractBaseTest {
    @Autowired
    private ServerSocketProvider serverSocketProvider;
    private ServerSocket serverSocket;

    @BeforeMethod
    public void setUp() {
        serverSocket = serverSocketProvider.provideActiveServerSocket();
        super.initialize(serverSocket);
    }

    @AfterMethod
    @Override
    public void tearDown() {
        // TODO Auto-generated method stub
        super.tearDown();
    }

    @Test
    public void testShellCodeInjectionShouldNotRunCode() throws IOException {
        // GIVEN

        // WHEN
        printWriter.println("q");

        // THEN
        String dataSentBack = readAllDataSentBack();
        assertThat(dataSentBack, containsString("1.\t10"));
        assertThat(dataSentBack, containsString("2.\t0 <- This is you (based on IP)"));
    }

    private String readAllDataSentBack() throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        String line = "";
        while ((line = inputReader.readLine()) != null) {
            stringBuilder.append(line + "\n");
        }
        return stringBuilder.toString();
    }

    @Configuration
    @ImportResource("classpath:spring-context.xml")
    public static class TestToplistIsDisplayOnGameEndITConfiguration {
        @Bean
        public FoodGenerator foodGenerator() {
            FoodGenerator foodGenerator = mock(FoodGenerator.class);
            when(foodGenerator.generateFood(any(List.class))).thenReturn(new Coordinate(0, 0));
            return foodGenerator;
        }
    }
}
