package com.niemiec.battleship.game.data.create.automatically;

import java.io.Serializable;
import java.util.Random;

import com.niemiec.battleship.game.data.check.CheckData;
import com.niemiec.battleship.game.objects.Coordinates;
import com.niemiec.battleship.game.objects.Ship;

@SuppressWarnings("serial")
public class CreatorAutomaticallyData implements Serializable  {
	protected Random random;
	private final int firstMast = 1;
	protected final int directionNotSelected = 0;
	protected final int directionX = 1;
	protected final int directionY = 2;

	protected final int rightAndDown = 0;
	protected final int leftAndTop = 1;

	

	public CreatorAutomaticallyData() {
		random = new Random();
	}

	public Coordinates downloadCoordinatesWhenAddShip(Ship ship, int currentMast) {
		Coordinates coordinates;

		while (true) {
			switch (currentMast) {
			case 0:
				coordinates = randomTheFirstMast();
				break;
			case 1:
				coordinates = randomTheSecondMast(ship);
				break;
			default:
				coordinates = randomTheReamainingMasts(ship, currentMast);
				break;
			}

			if (CheckData.checkIfWithinThePlayingField(coordinates))
				return coordinates;
		}
	}

	protected Coordinates randomTheReamainingMasts(Ship ship, int currentMast) {
		int side = random.nextInt(2);
		Coordinates coordinates = new Coordinates();
		int x = ship.getCoordinates(1).getX();
		int y = ship.getCoordinates(1).getY();

		if (ship.getDirection() == 1) {
			coordinates.setY(y);
			if (side == 1 && (x - 1) > 0) {
				coordinates.setX(x - 1);
			} else if (side == 0 && (x + ship.getCurrentNumberOfMasts()) <= 10) {
				coordinates.setX(x + ship.getCurrentNumberOfMasts());
			}
		} else if (ship.getDirection() == 2) {
			coordinates.setX(x);
			if (side == 1 && (y - 1) > 0) {
				coordinates.setY(y - 1);
			} else if (side == 0 && (y + ship.getCurrentNumberOfMasts()) <= 10) {
				coordinates.setY(y + ship.getCurrentNumberOfMasts());
			}
		}
		return coordinates;
	}

	private Coordinates randomTheSecondMast(Ship ship) {
		int direction = random.nextInt(2) + 1;
		return assignCoordinates(direction, ship);
	}


	private Coordinates assignCoordinates(int direction, Ship ship) {
		int x = ship.getCoordinates(firstMast).getX();
		int y = ship.getCoordinates(firstMast).getY();
		Coordinates coordinates = new Coordinates();
		int side = random.nextInt(2);

		if (direction == directionY) {
			coordinates = followWhenDirectionItIsY(side, x, y);
		} else if (direction == directionX) {
			coordinates = followWhenDirectionItIsX(side, x, y);
		}

		return coordinates;
	}

	private Coordinates followWhenDirectionItIsY(int side, int x, int y) {
		Coordinates coordinates = new Coordinates();
		coordinates.setX(x);
		if (side == leftAndTop && CheckData.checkIfWithinThePlayingField(new Coordinates(x, y - 1)))
			coordinates.setY(y - 1);
		else if (side == rightAndDown && CheckData.checkIfWithinThePlayingField(new Coordinates(x, y + 1)))
			coordinates.setY(y + 1);

		return coordinates;
	}

	private Coordinates followWhenDirectionItIsX(int side, int x, int y) {
		Coordinates coordinates = new Coordinates();
		coordinates.setY(y);
		if (side == leftAndTop && CheckData.checkIfWithinThePlayingField(new Coordinates(x - 1, y)))
			coordinates.setX(x - 1);
		else if (side == rightAndDown && CheckData.checkIfWithinThePlayingField(new Coordinates(x + 1, y)))
			coordinates.setX(x + 1);

		return coordinates;
	}

	protected Coordinates randomTheFirstMast() {
		int x = random.nextInt(10) + 1;
		int y = random.nextInt(10) + 1;
		return new Coordinates(x, y);
	}
}
