package com.niemiec.battleship.game.objects;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

//Zarz�dzanie tablic� statk�w
@SuppressWarnings("serial")
public class CollectionShips  implements Serializable {
	private Ship[] ships;
	private int currentlyNumberOfShips;
	// w tej tablicy przechwywane b�d� indeksy statk�w - numer w tablicy ships
	// z tablicy boardWithShipsIndex pobieramy indeks i od razu dokonujemy zmiany w
	// konkretnym statku
	// bo znamy jego numer w tablicy
	private Board boardWithShipsIndex;
	private int minimalNumberMastsOfNoHitShips = 1;

	public CollectionShips() {
		this.ships = new Ship[10];
		this.currentlyNumberOfShips = 0;
		this.boardWithShipsIndex = new Board();
	}

	public void addShip(Ship ship) {
		ships[currentlyNumberOfShips++] = ship;
		for (int i = 1; i <= ship.getNumberOfMasts(); i++) {
			boardWithShipsIndex.setBox(ship.getCoordinates(i), currentlyNumberOfShips);
		}
//		System.out.println("FUNKCJA COLLECTIONSHIPS, STATEK DODANO! currentlyNumberOfShips: " + currentlyNumberOfShips);
	}

	public void viewStatistic() {
		for (int i = 0; i < 10; i++) {
			Ship ship = ships[i];
			System.out.print("Statek " + ship.getNumberOfMasts() + ", posiada wsp�rz�dne: ");
			for (int j = 1; j <= ship.getNumberOfMasts(); j++) {
				System.out.print("x" + j + ": " + ship.getCoordinates(j).getX() + ", y" + j + ": "
						+ ship.getCoordinates(j).getY() + "; ");
			}
			System.out.println("");
		}
		boardWithShipsIndex.viewBoard();
	}

	public void shipWasHit(Coordinates coordinates) {
		int index = getShipIndexFromBoard(coordinates);
		Ship ship = ships[index - 1];
		ship.shipWasHit();

		updateMinimalNumberMastsOfNoHitShips();
	}

	public boolean checkIfShipIsSunk(Coordinates coordinates) {
		int index = getShipIndexFromBoard(coordinates);
		Ship ship = ships[index - 1];
		return ship.getSunk();
	}

	private int getShipIndexFromBoard(Coordinates coordinates) {
		return boardWithShipsIndex.getBox(coordinates);
	}

	public Ship getShip(Coordinates coordinates) {
		int index = boardWithShipsIndex.getBox(coordinates);
		return ships[index - 1];
	}

	public Ship getShip(int index) {
		return ships[index];
	}

	private void updateMinimalNumberMastsOfNoHitShips() {

		List<Integer> numberOfMastsFromNoSunkShips = new ArrayList<>();
		for (int i = 0; i < 10; i++) {
			if (!ships[i].getSunk()) {
				numberOfMastsFromNoSunkShips.add(ships[i].getNumberOfMasts());
			}
		}
		Collections.sort(numberOfMastsFromNoSunkShips);
		
		if (!numberOfMastsFromNoSunkShips.isEmpty())
			minimalNumberMastsOfNoHitShips = numberOfMastsFromNoSunkShips.get(0);
	}

	public int getMinimalNumberMastsOfNoHitShips() {
		return minimalNumberMastsOfNoHitShips;
	}
	
	
}
