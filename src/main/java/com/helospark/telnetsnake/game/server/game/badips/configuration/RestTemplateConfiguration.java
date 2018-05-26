package com.helospark.telnetsnake.game.server.game.badips.configuration;

import com.google.gson.Gson;
import com.helospark.lightdi.annotation.Bean;
import com.helospark.lightdi.annotation.Configuration;

import okhttp3.OkHttpClient;

@Configuration
public class RestTemplateConfiguration {

    @Bean
    public OkHttpClient okHttpClient() {
        return new OkHttpClient();
    }

    @Bean
    private Gson gson() {
        return new Gson();
    }

}
