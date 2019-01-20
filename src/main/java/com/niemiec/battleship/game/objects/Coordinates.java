package com.niemiec.battleship.game.objects;

import java.io.Serializable;

//współrzędne
@SuppressWarnings("serial")
public class Coordinates implements Serializable  {
	private int x;
	private int y;

	public Coordinates() {
		this.x = 0;
		this.y = 0;
	}

	public Coordinates(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public Coordinates(Coordinates coordinates) {
		this.x = coordinates.getX();
		this.y = coordinates.getY();
	}

	public Coordinates(Coordinates coordinates, int changeX, int changeY) {
		this.x = coordinates.getX() + changeX;
		this.y = coordinates.getY() + changeY;
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

	@Override
	public String toString() {
		return "x = " + x + ", y = " + y;
	}

	public boolean checkIfWithinThePlayingField() {
		return checkIfWithinThePlayingField(0, 0);
	}

	public boolean checkIfWithinThePlayingField(int changeX, int changeY) {
		int x = this.x + changeX;
		int y = this.y + changeY;
		if (x < 1 || x > 10 || y < 1 || y > 10)
			return false;
		return true;
	}
}
