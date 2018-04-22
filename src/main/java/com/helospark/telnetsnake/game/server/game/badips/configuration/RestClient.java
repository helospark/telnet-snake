package com.helospark.telnetsnake.game.server.game.badips.configuration;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.helospark.lightdi.annotation.Component;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

@Component
public class RestClient {
	private OkHttpClient client;
	private ObjectMapper objectMapper;

	public RestClient(OkHttpClient client, ObjectMapper objectMapper) {
		this.client = client;
		this.objectMapper = objectMapper;
	}

	public <T> T getForObject(String url, Class<T> responseType) {
		Request request = new Request.Builder().url(url).build();

		try (Response response = client.newCall(request).execute()) {
			if (!response.isSuccessful()) {
				throw new RuntimeException("Request is not successful");
			}
			return objectMapper.readValue(response.body().byteStream(), responseType);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

}
