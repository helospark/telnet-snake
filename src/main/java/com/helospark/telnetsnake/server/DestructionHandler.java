package com.helospark.telnetsnake.server;

import java.io.Closeable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class DestructionHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(DestructionHandler.class);
    private final List<Closeable> closeables = new ArrayList<>();

    public void addCloseableForDestruction(Closeable closeable) {
        synchronized (closeables) {
            closeables.add(closeable);
        }
    }

    @PostConstruct
    public void destroy() {
        LOGGER.info("Destruction handler invoced");
        synchronized (closeables) {
            for (Closeable closeable : closeables) {
                try {
                    closeable.close();
                } catch (Exception e) {
                    LOGGER.error("Exception while destroying " + closeable, e);
                }
            }
        }
    }
}
