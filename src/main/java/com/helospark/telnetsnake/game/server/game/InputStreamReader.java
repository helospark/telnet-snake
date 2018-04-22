package com.helospark.telnetsnake.game.server.game;

import java.io.InputStream;

import com.helospark.lightdi.annotation.Component;

@Component
public class InputStreamReader {

	public String readInputData(InputStream inputStream) {
		try {
			int numberOfBytesAvailable = inputStream.available();
			if (numberOfBytesAvailable > 0) {
				byte[] buffer = new byte[numberOfBytesAvailable];
				inputStream.read(buffer);
				return new String(buffer);
			}
			return "";
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
