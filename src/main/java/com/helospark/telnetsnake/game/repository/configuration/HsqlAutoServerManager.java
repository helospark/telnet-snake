package com.helospark.telnetsnake.game.repository.configuration;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Optional;

import org.hsqldb.Server;
import org.hsqldb.persist.HsqlProperties;
import org.hsqldb.server.ServerAcl.AclFormatException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.helospark.lightdi.annotation.Component;
import com.helospark.lightdi.annotation.Value;

/**
 * Code to implement H2's ";AUTO_SERVER=true" functionality for HSQLDB.
 */
@Component
public class HsqlAutoServerManager {
    private static final Logger LOGGER = LoggerFactory.getLogger(HsqlAutoServerManager.class);
    private static final String AUTOSTART_SERVER_FILE_LOCK_POSTFIX = "_autoserver_lock";
    private final String databaseFileName;
    private final int expectedPort;
    private Optional<Server> ownedServer = Optional.empty();

    public HsqlAutoServerManager(@Value("${DATABASE_FILE_PATH}") String databaseFileName,
            @Value("${DATABASE_PORT}") int expectedPort) {
        this.databaseFileName = databaseFileName;
        this.expectedPort = expectedPort;
    }

    /**
     * Starts a connection either by starting a new server and connect to it, or
     * find the running server for the given file and connect to it. TODO proper
     * exception handling
     *
     * @return JDBC connection, make sure to close it
     */
    public synchronized Connection createConnection() {
        try {
            String portToConnect;
            File databasePortFile = new File(databaseFileName + AUTOSTART_SERVER_FILE_LOCK_POSTFIX);
            if (!databasePortFile.exists()) {
                LOGGER.info("Creating new connection");
                portToConnect = startNewServer();
                writeServerPortToFile(databasePortFile, portToConnect);
                LOGGER.info("Connection created on port: " + portToConnect);
            } else {
                LOGGER.info(databasePortFile.getAbsolutePath() + " exists, initializing from there");
                portToConnect = readServerPortFromFile(databasePortFile);
            }
            return createJdbcHsqlConnectionToPort(portToConnect);
        } catch (Exception e) {
            throw new RuntimeException("Unable to create DB connection, check lock file", e);
        }
    }

    /**
     * If this JVM started the server, the server will be shut down, otherwise
     * no operation.
     */
    public synchronized void shutdown() throws SQLException {
        if (ownedServer.isPresent()) {
            ownedServer.get().shutdown();
            File file = new File(databaseFileName + AUTOSTART_SERVER_FILE_LOCK_POSTFIX);
            file.delete();
            LOGGER.info("Database shutdown");
        } else {
            LOGGER.info("Database is not owned by this JVM");
        }
    }

    private String startNewServer() throws IOException, AclFormatException, FileNotFoundException {
        String randomPortToStartServerServerOn = String.valueOf(getDatabasePort());
        HsqlProperties properties = createHsqlProperties(randomPortToStartServerServerOn);
        startServer(properties);
        return randomPortToStartServerServerOn;
    }

    private HsqlProperties createHsqlProperties(String port) {
        HsqlProperties properties = new HsqlProperties();
        properties.setProperty("server.database.0", "file:" + databaseFileName + ";hsqldb.write_delay=false");
        properties.setProperty("server.dbname.0", "mydb");
        properties.setProperty("server.port", port);
        return properties;
    }

    private void startServer(HsqlProperties p) throws IOException, AclFormatException {
        Server dserver = new Server();
        dserver.setProperties(p);
        dserver.setLogWriter(null);
        dserver.setErrWriter(null);
        dserver.start();
        ownedServer = Optional.of(dserver);
    }

    private String readServerPortFromFile(File file) throws FileNotFoundException, IOException {
        String portToConnect;
        BufferedReader is = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
        portToConnect = is.readLine();
        is.close();
        return portToConnect;
    }

    private void writeServerPortToFile(File file, String port) throws IOException, FileNotFoundException {
        file.createNewFile();
        PrintStream fs = new PrintStream(new FileOutputStream(file));
        fs.print(port);
        fs.close();
    }

    private Integer getDatabasePort() throws IOException {
        if (expectedPort == -1) {
            try (ServerSocket socket = new ServerSocket(0)) {
                return socket.getLocalPort();
            }
        } else {
            return expectedPort;
        }
    }

    private Connection createJdbcHsqlConnectionToPort(String portToConnect) throws SQLException {
        String connectionUri = "jdbc:hsqldb:hsql://localhost:" + portToConnect + "/mydb";
        LOGGER.info("Connecting to server with URI: " + connectionUri);
        return DriverManager.getConnection(connectionUri, "SA", "");
    }
}
