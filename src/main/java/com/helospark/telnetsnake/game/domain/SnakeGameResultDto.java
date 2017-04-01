package com.helospark.telnetsnake.game.domain;

import javax.annotation.Generated;

public class SnakeGameResultDto {
    private final String id;
    private final int points;

    @Generated("SparkTools")
    private SnakeGameResultDto(Builder builder) {
        this.id = builder.id;
        this.points = builder.points;
    }

    @Generated("SparkTools")
    public static Builder builder() {
        return new Builder();
    }

    public String getId() {
        return id;
    }

    public int getPoints() {
        return points;
    }

    @Generated("SparkTools")
    public static final class Builder {
        private String id;
        private int points;

        private Builder() {
        }

        public Builder withId(String id) {
            this.id = id;
            return this;
        }

        public Builder withPoints(int points) {
            this.points = points;
            return this;
        }

        public SnakeGameResultDto build() {
            return new SnakeGameResultDto(this);
        }
    }

}
