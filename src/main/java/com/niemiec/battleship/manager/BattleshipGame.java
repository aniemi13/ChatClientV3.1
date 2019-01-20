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
	public static final int START_THE_GAME = 15;
	public static final int END_GAME = 16;
	
	private String invitingPlayerNick;
	private String opponentPlayerNick;
	
	private Player player = null;
	private String nickWhoseTourn;

	private Player invitingPlayer;
	private Player opponentPlayer;
	
	private Coordinates shotCoordinates;
	
	private int gameStatus;
	private int index; // numer Battleship w serwerze
	private String winner;
	
	public BattleshipGame(String invitingPlayerNick, String opponentPlayerNick) {
		this.invitingPlayerNick = invitingPlayerNick;
		this.opponentPlayerNick = opponentPlayerNick;
		gameStatus = GAME_PROPOSAL;
	}

	public String getOpponentPlayerNick() {
		return opponentPlayerNick;
	}
	
	public String getInvitingPlayerNick() {
		return invitingPlayerNick;
	}
	
	public void setInvitingPlayerNick(String invitingPlayerNick) {
		this.invitingPlayerNick = invitingPlayerNick;
	}

	public void setOpponentPlayerNick(String opponentPlayerNick) {
		this.opponentPlayerNick = opponentPlayerNick;
	}

	public void delete() {
		// TODO Auto-generated method stub
		
	}

	public int getGameStatus() {
		return gameStatus;
	}

	public void setGameStatus(int gameStatus) {
		this.gameStatus = gameStatus;
	}
	
	public void setGameIndex(int index) {
		this.index = index;
	}
	
	public int getGameIndex() {
		return index;
	}

	public void createBattleshipGameForOpponent() {
		String a = new String(invitingPlayerNick);
		invitingPlayerNick =  new String(opponentPlayerNick);
		opponentPlayerNick = a;
		
		Player p = invitingPlayer;
		invitingPlayer = opponentPlayer;
		opponentPlayer = p;
	}

	public Player getInvitingPlayer() {
		return invitingPlayer;
	}

	public void setInvitingPlayer(Player invitingPlayer) {
		this.invitingPlayer = invitingPlayer;
	}

	public Player getOpponentPlayer() {
		return opponentPlayer;
	}

	public void setOpponentPlayer(Player opponentPlayer) {
		this.opponentPlayer = opponentPlayer;
	}
	
	public void setNickWhoseTourn(String nickWhoseTourn) {
		this.nickWhoseTourn = nickWhoseTourn;
	}
	
	public String getNickWhoseTourn() {
		return nickWhoseTourn;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}
	
	public Player getPlayer() {
		return player;
	}

	public void setCoordinates(Coordinates coordinates) {
		this.shotCoordinates = coordinates;
	}
	
	public Coordinates getCoordinates() {
		return shotCoordinates;
	}

	public void setWinnerNick(String winner) {
		this.winner = winner;
	}

	public String getWinner() {
		return winner;
	}
}
