package com.helospark.telnetsnake.repository;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.nio.charset.Charset;

import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;

@Component
public class ClassPathFileReader {

    public String readClasspathFile(String fileName) throws IOException, URISyntaxException {
        InputStream stream = getClass().getResourceAsStream(fileName);
        return StreamUtils.copyToString(stream, Charset.forName("UTF-8"));
    }

}
