package com.helospark.telnetsnake.server.domain;

import java.net.Socket;

import javax.annotation.Generated;

import com.helospark.telnetsnake.server.game.domain.SnakeIO;

public class ClientConnectionData {
    public Socket clientSocket;
    public String clientIp;
    public SnakeIO snakeIO;

    @Generated("SparkTools")
    private ClientConnectionData(Builder builder) {
        this.clientSocket = builder.clientSocket;
        this.clientIp = builder.clientIp;
        this.snakeIO = builder.snakeIO;
    }

    @Generated("SparkTools")
    public static Builder builder() {
        return new Builder();
    }

    @Generated("SparkTools")
    public static final class Builder {
        private Socket clientSocket;
        private String clientIp;
        private SnakeIO snakeIO;

        private Builder() {
        }

        public Builder withClientSocket(Socket clientSocket) {
            this.clientSocket = clientSocket;
            return this;
        }

        public Builder withClientIp(String clientIp) {
            this.clientIp = clientIp;
            return this;
        }

        public Builder withSnakeIO(SnakeIO snakeIO) {
            this.snakeIO = snakeIO;
            return this;
        }

        public ClientConnectionData build() {
            return new ClientConnectionData(this);
        }
    }

}
