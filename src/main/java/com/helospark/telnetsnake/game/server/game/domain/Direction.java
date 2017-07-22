package com.helospark.telnetsnake.game.server.game.domain;

public enum Direction {
	LEFT(new Coordinate(-1, 0)), RIGHT(new Coordinate(1, 0)), UP(new Coordinate(0, 1)), DOWN(new Coordinate(0, -1));

	private Coordinate directionVector;

	private Direction(Coordinate directionVector) {
		this.directionVector = directionVector;
	}

	public Coordinate getDirectionVector() {
		return directionVector;
	}
}