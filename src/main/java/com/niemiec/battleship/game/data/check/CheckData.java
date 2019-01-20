package com.niemiec.battleship.game.data.check;


import com.niemiec.battleship.game.objects.Board;
import com.niemiec.battleship.game.objects.Coordinates;
import com.niemiec.battleship.game.objects.Ship;

import javafx.scene.control.Button;

public class CheckData {
	private static final int checkWayX = 1;
	private static final int checkWayY = 2;
	private static Board board;
	private static Ship ship;
	private static int sumCheckDirection;

	public static boolean checkIfBoxIsEmpty(Coordinates coordinates) {
		if (board.getBox(coordinates) == Board.BOX_EMPTY)
			return true;
		return false;
	}

	public static boolean checkIsThereAPlace(Coordinates coordinates) {
		if (shipNotOneMastedAndWithoutDirection()) {
			return checkPlaceStart(coordinates);
		} else {
			return true;
		}

	}

	public static boolean checkIfTheNextIsTheGoodWay(Coordinates coordinates) {
		if (shipNonOneMastedAndHaveAddedMastAndHaveDirection()) {
			return checkWhetherTheChosenDirectionIsAcceptable(coordinates);
		}
		return true;
	}

	private static boolean checkWhetherTheChosenDirectionIsAcceptable(Coordinates coordinates) {
		for (int i = ship.getCurrentNumberOfMasts(); i > 0; i--) {
			int x = ship.getCoordinates(i).getX();
			int y = ship.getCoordinates(i).getY();
			if (checkEveryDirection(coordinates, x, y))
				return true;
		}
		return false;
	}

	private static boolean checkEveryDirection(Coordinates coordinates, int x, int y) {
		if (checkTheDirectionOnTheShip(Ship.SHIP_DIRECTION_X))
			return checkDirection(coordinates.getX(), x, coordinates.getY(), y);
		if (checkTheDirectionOnTheShip(Ship.SHIP_DIRECTION_Y))
			return checkDirection(coordinates.getY(), y, coordinates.getX(), x);
		if (checkTheDirectionOnTheShip(Ship.SHIP_DIRECTION_XY)) {
			return checkDirectionXY(coordinates, x, y);
		}
		return false;
	}

	private static boolean checkDirectionXY(Coordinates coordinates, int x, int y) {
		if (checkDirection(coordinates.getX(), x, coordinates.getY(), y)) {
			ship.setDirection(Ship.SHIP_DIRECTION_X);
			return true;
		}
		if (checkDirection(coordinates.getY(), y, coordinates.getX(), x)) {
			ship.setDirection(Ship.SHIP_DIRECTION_Y);
			return true;
		}
		return false;
	}

	private static boolean checkDirection(int coordinate, int changeCoordinate, int staticCoordinate,
			int changeStaticCoordinate) {
		int rightAndDown = 1;
		int LeftAndTop = -1;
		return (((changeCoordinate - coordinate) == rightAndDown || (changeCoordinate - coordinate) == LeftAndTop)
				&& (changeStaticCoordinate - staticCoordinate) == 0);
	}

	private static boolean checkTheDirectionOnTheShip(int checkedDirection) {
		return ship.getDirection() == checkedDirection;
	}

	private static boolean shipNonOneMastedAndHaveAddedMastAndHaveDirection() {
		return (ship.getNumberOfMasts() != 1 && ship.getCurrentNumberOfMasts() != 0
				&& ship.getDirection() != Ship.SHIP_DIRECTION_NO_SPACE);
	}

	private static boolean shipNotOneMastedAndWithoutDirection() {
		return (ship.getNumberOfMasts() != 1 && ship.getDirection() == Ship.SHIP_DIRECTION_NO_SPACE);
	}

	private static boolean checkPlaceStart(Coordinates coordinates) {
		sumCheckDirection = 0;
		loopToCheckThePlace(coordinates, CheckData.checkWayX);
		loopToCheckThePlace(coordinates, CheckData.checkWayY);
		return checkWay();
	}

	private static void loopToCheckThePlace(Coordinates coordinates, int checkNextXorY) {
		for (int i = 1; i <= ship.getNumberOfMasts(); i++) {
			if (startCheckingFromTheExtremePoint(coordinates, checkNextXorY, i))
				break;
		}
	}

	private static boolean startCheckingFromTheExtremePoint(Coordinates coordinates, int checkNextXorY, int i) {
		int extremePoint = (-ship.getNumberOfMasts()) + i;
		for (; extremePoint <= 0; extremePoint++) {
			if (checkWillTheShipFitFromExtremePoint(coordinates, checkNextXorY, extremePoint)) {
				sumCheckDirection += checkNextXorY;
				return true;
			}
		}

		return false;
	}

	private static boolean checkWillTheShipFitFromExtremePoint(Coordinates coordinates, int checkNextXorY,
			int extremePoint) {
		int counterMasts = 0;
		for (int k = 0; k < ship.getNumberOfMasts(); k++) {
			int mast = k + extremePoint;
			if (checkCanYouPutAMast(coordinates, checkNextXorY, mast))
				counterMasts++;
			else
				break;
		}
		return counterMasts == ship.getNumberOfMasts();
	}

	private static boolean checkCanYouPutAMast(Coordinates coordinates, int checkNextXorY, int shiftOnTheAxis) {
		int addedToX = 0;
		int addedToY = 0;

		if (checkNextXorY == CheckData.checkWayX) {
			addedToX = shiftOnTheAxis;
		} else {
			addedToY = shiftOnTheAxis;
		}

		Coordinates toCheck = new Coordinates(coordinates.getX() + addedToX, coordinates.getY() + addedToY);
		return checkTheBoxesNextIsGood(toCheck);
	}

	private static boolean checkTheBoxesNextIsGood(Coordinates coordinates) {
		return (checkIfWithinThePlayingField(coordinates) && checkIfAroundOneIsEmpty(coordinates)
				&& checkIfTheFieldIsEquals(coordinates, Board.BOX_EMPTY));
	}

	private static boolean checkWay() {
		switch (sumCheckDirection) {
		case 0:
			ship.setDirection(Ship.SHIP_DIRECTION_NO_SPACE);
			return false;
		case 1:
			ship.setDirection(Ship.SHIP_DIRECTION_X);
			return true;
		case 2:
			ship.setDirection(Ship.SHIP_DIRECTION_Y);
			return true;
		case 3:
			ship.setDirection(Ship.SHIP_DIRECTION_XY);
			return true;
		default:
			return false;
		}
	}

	public static boolean checkIfAroundOneIsEmpty(Coordinates coordinates) {
		for (int i = -1; i < 2; i++) {
			for (int j = -1; j < 2; j++) {
				if (itsEmptyNextTo(coordinates, i, j))
					return false;
			}
		}
		return true;
	}

	private static boolean itsEmptyNextTo(Coordinates coordinates, int x, int y) {
		if (!ijEqualsZero(x, y) && (checkIfWithinThePlayingField(coordinates, x, y)
				&& itsEqualsArgNextTo(coordinates, x, y, Board.BOX_SHIP)))
			return true;
		else
			return false;
	}

	private static boolean ijEqualsZero(int i, int j) {
		return (i == 0 && j == 0);
	}

	private static boolean itsEqualsArgNextTo(Coordinates coordinates, int x, int y, int boxForCheck) {
		Coordinates c = new Coordinates(coordinates, x, y);
		return checkIfTheFieldIsEquals(c, boxForCheck);
	}

	private static boolean checkIfTheFieldIsEquals(Coordinates coordinates, int boxForCheck) {
		return (board.getBox(coordinates) == boxForCheck);
	}

	public static boolean checkIfWithinThePlayingField(Coordinates coordinates) {
		int x = coordinates.getX();
		int y = coordinates.getY();

		if (x >= 1 && x <= 10 && y >= 1 && y <= 10) {
			return true;
		}
		return false;
	}

	public static boolean checkIfWithinThePlayingField(Coordinates coordinates, int addedToX, int addedToY) {
		int xPlus = coordinates.getX() + addedToX;
		int yPlus = coordinates.getY() + addedToY;

		if (xPlus >= 1 && xPlus <= 10 && yPlus >= 1 && yPlus <= 10) {
			return true;
		}
		return false;
	}

	public static void setVariablesToCheckData(Board board, Ship ship) {
		CheckData.board = board;
		CheckData.ship = ship;
	}

	public static void setVariablesToCheckData(Board board) {
		CheckData.board = board;
	}

	public static Coordinates getCoordinatesFromButton(Button button) {
		String id = button.getId();
		char[] chars = new char[2];
		id.getChars(2, 4, chars, 0);
		return new Coordinates(Character.getNumericValue(chars[0]) + 1, Character.getNumericValue(chars[1]) + 1);
	}
}
