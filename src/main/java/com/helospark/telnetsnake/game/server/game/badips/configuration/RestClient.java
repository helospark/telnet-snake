package com.helospark.telnetsnake.game.server.game.badips.configuration;

import static java.nio.charset.StandardCharsets.UTF_8;

import java.io.IOException;

import com.google.gson.Gson;
import com.helospark.lightdi.annotation.Component;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

@Component
public class RestClient {
    private OkHttpClient client;
    private Gson gson;

    public RestClient(OkHttpClient client, Gson gson) {
        this.client = client;
        this.gson = gson;
    }

    public <T> T getForObject(String url, Class<T> responseType) {
        Request request = new Request.Builder().url(url).build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new RuntimeException("Request is not successful");
            }
            return gson.fromJson(new String(response.body().bytes(), UTF_8), responseType);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
