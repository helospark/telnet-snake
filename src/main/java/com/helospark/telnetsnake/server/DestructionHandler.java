package com.helospark.telnetsnake.server;

import java.io.IOException;

import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.helospark.telnetsnake.server.socket.ServerSocketProvider;

@Component
public class DestructionHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(DestructionHandler.class);
    @Autowired
    private ServerSocketProvider serverSocketProvider;

    @PreDestroy
    public void destroy() {
        LOGGER.info("Destruction handler invoked");
        try {
            serverSocketProvider.provideActiveServerSocket().close();
        } catch (IOException e) {
            LOGGER.error("Unable to destroy server socket", e);
        }
    }
}
