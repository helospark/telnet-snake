package com.helospark.telnetsnake.game.domain;

import java.io.Closeable;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.Socket;

import javax.annotation.Generated;

public class SnakeIO {
    private final InputStream inputStream;
    private final PrintWriter printWriter;
    private final Socket socket;
    private boolean isClosed;

    @Generated("SparkTools")
    private SnakeIO(Builder builder) {
        this.inputStream = builder.inputStream;
        this.printWriter = builder.printWriter;
        this.socket = builder.socket;
        this.isClosed = builder.isClosed;
    }

    public InputStream getInputStream() {
        return inputStream;
    }

    public PrintWriter getPrintWriter() {
        return printWriter;
    }

    public Socket getSocket() {
        return socket;
    }

    public boolean isClosed() {
        return isClosed;
    }

    public void close() {
        tryClose(socket);
        tryClose(printWriter);
        tryClose(inputStream);
        isClosed = true;
    }

    private void tryClose(Closeable closeable) {
        try {
            closeable.close();
        } catch (Exception e) {
        }
    }

    @Generated("SparkTools")
    public static Builder builder() {
        return new Builder();
    }

    @Generated("SparkTools")
    public static final class Builder {
        private InputStream inputStream;
        private PrintWriter printWriter;
        private Socket socket;
        private boolean isClosed;

        private Builder() {
        }

        public Builder withInputStream(InputStream inputStream) {
            this.inputStream = inputStream;
            return this;
        }

        public Builder withPrintWriter(PrintWriter printWriter) {
            this.printWriter = printWriter;
            return this;
        }

        public Builder withSocket(Socket socket) {
            this.socket = socket;
            return this;
        }

        public Builder withIsClosed(boolean isClosed) {
            this.isClosed = isClosed;
            return this;
        }

        public SnakeIO build() {
            return new SnakeIO(this);
        }
    }

}
