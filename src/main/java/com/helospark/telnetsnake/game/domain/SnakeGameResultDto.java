package com.helospark.telnetsnake.game.domain;

import javax.annotation.Generated;

public class SnakeGameResultDto {
    private final String ip;
    private final int points;
    private String allUserInputs;

    @Generated("SparkTools")
    private SnakeGameResultDto(Builder builder) {
        this.ip = builder.ip;
        this.points = builder.points;
        this.allUserInputs = builder.allUserInputs;
    }

    public void setAllUserInputs(String allUserInputs) {
        this.allUserInputs = allUserInputs;
    }

    public String getIp() {
        return ip;
    }

    public int getPoints() {
        return points;
    }

    public String getAllUserInputs() {
        return allUserInputs;
    }

    /**
     * Creates builder to build {@link SnakeGameResultDto}.
     *
     * @return created builder
     */
    @Generated("SparkTools")
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Builder to build {@link SnakeGameResultDto}.
     */
    @Generated("SparkTools")
    public static final class Builder {
        private String ip;
        private int points;
        private String allUserInputs;

        private Builder() {
        }

        public Builder withIp(String ip) {
            this.ip = ip;
            return this;
        }

        public Builder withPoints(int points) {
            this.points = points;
            return this;
        }

        public Builder withAllUserInputs(String allUserInputs) {
            this.allUserInputs = allUserInputs;
            return this;
        }

        public SnakeGameResultDto build() {
            return new SnakeGameResultDto(this);
        }
    }

}
