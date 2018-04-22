package com.helospark.telnetsnake.game.repository;

import static java.nio.charset.StandardCharsets.UTF_8;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;

import com.helospark.lightdi.annotation.Component;

@Component
public class ClassPathFileReader {

    public String readClasspathFile(String fileName) throws IOException, URISyntaxException {
        InputStream stream = getClass().getResourceAsStream(fileName);
        return new String(readFully(stream).toByteArray(), UTF_8);
    }

    private ByteArrayOutputStream readFully(InputStream inputStream)
            throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int length = 0;
        while ((length = inputStream.read(buffer)) != -1) {
            baos.write(buffer, 0, length);
        }
        return baos;
    }

}
