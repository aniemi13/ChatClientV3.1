package com.niemiec.battleship.manager;

import java.io.Serializable;

import com.niemiec.battleship.game.objects.Coordinates;
import com.niemiec.battleship.game.objects.Player;

@SuppressWarnings("serial")
public class BattleshipGame implements Serializable {
	public static final int GAME_PROPOSAL = 10;
	public static final int REJECTION_GAME_PROPOSAL = 11;
	public static final int ACCEPTING_THE_GAME = 12; // też tylko do serwera, poten on wysyła już start game
	public static final int ADD_SHIPS = 13;
	public static final int SHIPS_ADDED = 14; //tej informacji będzie potrzebował tylko serwer
	public static final int PLAY_THE_GAME = 15;
	public static final int END_GAME = 16;
	public static final int GIVE_UP = 17;
	
	private Player player;
	private String nickWhoseTourn;
	private String opponentPlayerNick;
	private Coordinates shotCoordinates;
	private int gameStatus;
	private int gameIndex; // numer Battleship w serwerze
	private String winnerNick;
	
	public BattleshipGame() {
		player = null;
		gameStatus = GAME_PROPOSAL;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public String getNickWhoseTourn() {
		return nickWhoseTourn;
	}

	public void setNickWhoseTourn(String nickWhoseTourn) {
		this.nickWhoseTourn = nickWhoseTourn;
	}

	public String getOpponentPlayerNick() {
		return opponentPlayerNick;
	}

	public void setOpponentPlayerNick(String opponentPlayerNick) {
		this.opponentPlayerNick = opponentPlayerNick;
	}

	public Coordinates getShotCoordinates() {
		return shotCoordinates;
	}

	public void setShotCoordinates(Coordinates shotCoordinates) {
		this.shotCoordinates = shotCoordinates;
	}

	public int getGameStatus() {
		return gameStatus;
	}

	public void setGameStatus(int gameStatus) {
		this.gameStatus = gameStatus;
	}

	public int getGameIndex() {
		return gameIndex;
	}

	public void setGameIndex(int gameIndex) {
		this.gameIndex = gameIndex;
	}

	public String getWinnerNick() {
		return winnerNick;
	}

	public void setWinnerNick(String winner) {
		this.winnerNick = winner;
	}
}