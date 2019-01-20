package com.niemiec.battleship.game.objects;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@SuppressWarnings("serial")
public class Ship implements Serializable  {
	public static final int SHIP_DIRECTION_NO_SPACE = 0;
	public static final int SHIP_DIRECTION_X = 1;
	public static final int SHIP_DIRECTION_Y = 2;
	public static final int SHIP_DIRECTION_XY = 3;
	
	private List<Integer> pointOnTheAxisX;
	private List<Integer> pointOnTheAxisY;

	private int numberOfMasts; // maksymalna ilo�� maszt�w
	private int currentNumberOfMasts; // aktualna ilo�� zbudowanych maszt�w
	private int currentNumberOfHitMasts; // aktualna ilo�� trafionych maszt�w
	private int direction; // 0 - brak, 1 - wzd�� x, 2 - wzd�� y, 3 - w obie strony
	private boolean sunk; // true, gdy zatopiony

	public Ship(int numberOfMasts) {
		this.pointOnTheAxisX = new ArrayList<Integer>();
		this.pointOnTheAxisY = new ArrayList<Integer>();
		this.currentNumberOfMasts = 0;
		this.currentNumberOfHitMasts = 0;
		this.numberOfMasts = numberOfMasts;
		this.direction = 0;
		this.sunk = false;
	}

	public Coordinates getCoordinates(int numberOfMasts) {
		return new Coordinates(pointOnTheAxisX.get(numberOfMasts -1),  pointOnTheAxisY.get(numberOfMasts -1));
	}

	public void setMast(Coordinates box, int currentNumberOfMasts) {
		this.pointOnTheAxisX.add(box.getX());
		this.pointOnTheAxisY.add(box.getY());
		this.currentNumberOfMasts++;
		if (this.currentNumberOfMasts > 1)
			sortMasts();
	}

	public boolean getSunk() {
		return this.sunk;
	}

	public int getNumberOfMasts() {
		return this.numberOfMasts;
	}

	public int getDirection() {
		return this.direction;
	}

	public void setDirection(int direction) {
		this.direction = direction;
	}

	public int getCurrentNumberOfMasts() {
		return currentNumberOfMasts;
	}

	public void setCurrentNumberOfMasts(int currentNumberOfMasts) {
		this.currentNumberOfMasts = currentNumberOfMasts;
	}

	public void sortMasts() {
		if ((pointOnTheAxisX.get(1) - pointOnTheAxisX.get(0)) == 0)
			Collections.sort(pointOnTheAxisY);
		else if ((pointOnTheAxisY.get(1) - pointOnTheAxisY.get(0)) == 0)
			Collections.sort(pointOnTheAxisX);
	}

	public int getCurrentNumberOfHitMasts() {
		return currentNumberOfHitMasts;
	}
	
	public void increaseCurrentNumberOfHitMasts() {
		this.currentNumberOfHitMasts++;
	}

	public void shipWasHit() {
		this.currentNumberOfHitMasts++;
		setSunk();
	}

	private void setSunk() {
		if (currentNumberOfHitMasts == numberOfMasts)
			this.sunk = true;
	}
}
