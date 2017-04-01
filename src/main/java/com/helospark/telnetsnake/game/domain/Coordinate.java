package com.helospark.telnetsnake.game.domain;


public class Coordinate {
	private int x, y;

	public Coordinate(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public Coordinate(Coordinate coordinate) {
		this.x = coordinate.x;
		this.y = coordinate.y;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public void limitToConstraints(int width, int height) {
		if (x < 0) {
			x = width - 1;
		}
		if (x >= width) {
			x = 0;
		}
		if (y < 0) {
			y = height - 1;
		}
		if (y >= height) {
			y = 0;
		}
	}

	public Coordinate copy() {
		return new Coordinate(x, y);
	}

	public void add(Coordinate coordinate) {
		this.x += coordinate.x;
		this.y += coordinate.y;
	}

	public void set(Coordinate coordinate) {
		this.x = coordinate.x;
		this.y = coordinate.y;
	}

	public void set(int x, int y) {
		this.x = x;
		this.y = y;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + x;
		result = prime * result + y;
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
		Coordinate other = (Coordinate) obj;
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		return true;
	}

}
