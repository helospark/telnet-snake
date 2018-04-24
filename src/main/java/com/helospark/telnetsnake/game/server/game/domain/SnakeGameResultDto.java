package com.helospark.telnetsnake.game.server.game.domain;

import java.time.LocalDateTime;

import javax.annotation.Generated;

public class SnakeGameResultDto {
    private final String ip;
    private final int points;
    private final LocalDateTime localDateTime;
    private String allUserInputs;

    @Generated("SparkTools")
    private SnakeGameResultDto(Builder builder) {
        this.ip = builder.ip;
        this.points = builder.points;
        this.localDateTime = builder.localDateTime;
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

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public String getAllUserInputs() {
        return allUserInputs;
    }

    @Generated("SparkTools")
    public static Builder builder() {
        return new Builder();
    }

    @Override
    public String toString() {
        return "SnakeGameResultDto [ip=" + ip + ", points=" + points + ", localDateTime=" + localDateTime + ", allUserInputs=" + allUserInputs + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((allUserInputs == null) ? 0 : allUserInputs.hashCode());
        result = prime * result + ((ip == null) ? 0 : ip.hashCode());
        result = prime * result + ((localDateTime == null) ? 0 : localDateTime.hashCode());
        result = prime * result + points;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        SnakeGameResultDto other = (SnakeGameResultDto) obj;
        if (allUserInputs == null) {
            if (other.allUserInputs != null)
                return false;
        } else if (!allUserInputs.equals(other.allUserInputs))
            return false;
        if (ip == null) {
            if (other.ip != null)
                return false;
        } else if (!ip.equals(other.ip))
            return false;
        if (localDateTime == null) {
            if (other.localDateTime != null)
                return false;
        } else if (!localDateTime.equals(other.localDateTime))
            return false;
        if (points != other.points)
            return false;
        return true;
    }

    @Generated("SparkTools")
    public static final class Builder {
        private String ip;
        private int points;
        private LocalDateTime localDateTime;
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

        public Builder withLocalDateTime(LocalDateTime localDateTime) {
            this.localDateTime = localDateTime;
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
