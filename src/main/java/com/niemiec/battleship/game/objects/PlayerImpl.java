package com.niemiec.battleship.game.objects;

import java.io.Serializable;

@SuppressWarnings("serial")
public class PlayerImpl implements Player, Serializable, Cloneable {
	private String nick;

	public String getNick() {
		return nick;
	}

	private int typeOfPlayer;
	private Board board;
	private Board opponentBoard;
	private int sunkenShips;
	private CollectionShips collectionShips;
	private boolean onHit;
	private Coordinates coordinatesOnHit;
	private int directionOnHit;

	public PlayerImpl(int typeOfPlayer, String nick) {
		this.board = new Board();
		this.opponentBoard = new Board();
		this.collectionShips = new CollectionShips();
		this.typeOfPlayer = typeOfPlayer;
		this.nick = nick;
		this.sunkenShips = 0;
		resetHitData();

	}

	public void resetHitData() {
		onHit = false;
		coordinatesOnHit = new Coordinates();
		directionOnHit = Ship.SHIP_DIRECTION_NO_SPACE;
	}

	public Board getBoard() {
		return board;
	}

	@Override
	public Board getOpponentBoard() {
		return opponentBoard;
	}

	@Override
	public int getSunkenShips() {
		return sunkenShips;
	}

	@Override
	public void increaseSunkenShips() {
		sunkenShips++;
	}

	@Override
	public CollectionShips getCollectionShips() {
		return collectionShips;
	}

	@Override
	public boolean isVirtualPlayer() {
		return (typeOfPlayer == Player.FIRST_PLAYER) ? true : false;
	}

	@Override
	public boolean getInformationInThePlayerIsVirtual() {
		if (typeOfPlayer == Player.FIRST_PLAYER)
			return true;
		return false;
	}

	public void setHitData(Coordinates coordinates) {
		onHit = true;
		coordinatesOnHit = new Coordinates(coordinates);
	}

	public boolean isOnHit() {
		return onHit;
	}

	public int getDirectionOnHit() {
		return directionOnHit;
	}

	public void setDirectionOnHit(int directionOnHit) {
		this.directionOnHit = directionOnHit;
	}

	public Coordinates getCoordinatesOnHit() {
		return this.coordinatesOnHit;
	}

	@Override
	public Object clone() {
		PlayerImpl playerImpl = new PlayerImpl(this.typeOfPlayer, this.nick);
		try {
			playerImpl.setBoard(board.clone());
			playerImpl.setOpponentBoard(opponentBoard.clone());
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		playerImpl.setSunkenShips(sunkenShips);
//		private CollectionShips collectionShips;
//		private boolean onHit;
//		private Coordinates coordinatesOnHit;
//		private int directionOnHit;
		return playerImpl;
	}

	private void setSunkenShips(int sunkenShips2) {
		this.sunkenShips = sunkenShips2;
	}

	private void setOpponentBoard(Object clone) {
		this.opponentBoard = (Board) clone;
	}

	private void setBoard(Object clone) {
		this.board = (Board) clone;
	}
}
