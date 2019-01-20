package com.niemiec.battleship.game.logic;

import java.io.Serializable;

import com.niemiec.battleship.game.data.check.CheckData;
import com.niemiec.battleship.game.data.create.automatically.CreatorAutomaticallyData;
import com.niemiec.battleship.game.objects.Board;
import com.niemiec.battleship.game.objects.Coordinates;
import com.niemiec.battleship.game.objects.Player;
import com.niemiec.battleship.game.objects.Ship;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;

@SuppressWarnings("serial")
public class AddShips implements Serializable {
	private Player[] players;
	private int actualPlayer;
	private CreatorAutomaticallyData creatorAutomaticallyData;

	int[] quantityShipsAndMasts;
	int currentQuantityShipsOfGivenType;
	int currentShip;
	int currentMast;
	Ship ship;
	Coordinates coordinates;
	private BorderManagement borderManagement;

	public AddShips() {
		setTheInitialConditions();
		creatorAutomaticallyData = new CreatorAutomaticallyData();
	}
	
	public void addOneRealPlayer(Player realPlayer) {
		players = new Player[2];
		players[Player.SECOND_PLAYER] = realPlayer;
	}

	public void addPlayers(Player realPlayer, Player virtualPlayer) {
		players = new Player[2];
		players[Player.SECOND_PLAYER] = realPlayer;
		players[Player.FIRST_PLAYER] = virtualPlayer;
	}

	public boolean addShipsManually(int typeOfPlayer, ActionEvent event) {
		setInitionalConditionsIfStartAdding();
		setActualTypeOfPlayer(typeOfPlayer);
		getCoordinatesFromButton((Button) event.getSource());
		addShips();
		
		borderManagement.drawBoardInMyBorder(players[typeOfPlayer]);
		
		return conditionOfTheEndOfAddingShips();
	}

	public boolean addShipsAutomatically(int typeOfPlayer) {
		setInitionalConditionsIfStartAdding();
		setActualTypeOfPlayer(typeOfPlayer);
		while (!conditionOfTheEndOfAddingShips()) {
			coordinates = creatorAutomaticallyData.downloadCoordinatesWhenAddShip(ship, currentMast);
			addShips();
		}
		borderManagement.drawBoardInMyBorder(players[typeOfPlayer]);
		return true;
	}

	private void addShips() {
		addMast();
		checkIfTheShipHasBeenAdded();
	}

	private void addMast() {
		if (checkDataAddingTheMast()) {
			fillInTheShipDetails();
		}
	}

	private void checkIfTheShipHasBeenAdded() {
		if (hasAllMastsBeenAdded()) {
			updateData();
		}
	}

	private void updateData() {
		updatePlayer();
		updateVariables();
	}

	private void updateVariables() {
		currentMast = 0;
		updateCurrentShipAndCurrentQuantityShipsOfGivenType();
		ship = new Ship(getActualNumberOfMasts());
	}

	private void updatePlayer() {
		players[actualPlayer].getCollectionShips().addShip(ship);
		players[actualPlayer].getBoard().checkBoxEnterToBoxShip();
	}

	private int getActualNumberOfMasts() {
		if (currentQuantityShipsOfGivenType < quantityShipsAndMasts.length)
			return quantityShipsAndMasts[currentQuantityShipsOfGivenType];
		else
			return 0;
	}

	private boolean hasAllMastsBeenAdded() {
		return currentMast == quantityShipsAndMasts[currentQuantityShipsOfGivenType];
	}

	private void fillInTheShipDetails() {
		currentMast++;
		players[actualPlayer].getBoard().setBox(coordinates, Board.BOX_ENTER);
		ship.setMast(coordinates, currentMast);
	}

	private boolean checkDataAddingTheMast() {
		CheckData.setVariablesToCheckData(players[actualPlayer].getBoard(), ship);
		if (!CheckData.checkIfBoxIsEmpty(coordinates))
			return false;
		if (!CheckData.checkIfAroundOneIsEmpty(coordinates))
			return false;
		if (!CheckData.checkIsThereAPlace(coordinates))
			return false;
		if (!CheckData.checkIfTheNextIsTheGoodWay(coordinates))
			return false;
		return true;
	}

	private void setActualTypeOfPlayer(int typeOfPlayer) {
		actualPlayer = typeOfPlayer;
	}

	private void setInitionalConditionsIfStartAdding() {
		if (conditionOfTheEndOfAddingShips()) {
			setTheInitialConditions();
		}
	}

	private boolean conditionOfTheEndOfAddingShips() {
		if (currentQuantityShipsOfGivenType == quantityShipsAndMasts.length && currentShip == 0)
			return true;
		return false;
	}

	private void setTheInitialConditions() {
		this.quantityShipsAndMasts = new int[] { 4, 3, 2, 1 };
		this.currentQuantityShipsOfGivenType = 0;
		this.currentShip = getCurrentShip();
		this.ship = new Ship(getActualNumberOfMasts());
		this.currentMast = 0;
		this.actualPlayer = Player.FIRST_PLAYER;
	}

	private void updateCurrentShipAndCurrentQuantityShipsOfGivenType() {
		currentShip--;
		if (currentShip == 0) {
			currentQuantityShipsOfGivenType++;
			currentShip = getCurrentShip();
		}
	}

	private int getCurrentShip() {
		if (currentQuantityShipsOfGivenType < quantityShipsAndMasts.length)
			return quantityShipsAndMasts[quantityShipsAndMasts.length - (currentQuantityShipsOfGivenType + 1)];
		else
			return 0;
	}

	private void getCoordinatesFromButton(Button button) {
		coordinates = CheckData.getCoordinatesFromButton(button);
	}
	
	public boolean areShipsAdded() {
		return conditionOfTheEndOfAddingShips();
	}

	public Player getPlayer(int typeOfPlayer) {
		return players[typeOfPlayer];
	}

	public void addBorderManagement(BorderManagement borderManagement) {
		this.borderManagement = borderManagement;
	}
}
