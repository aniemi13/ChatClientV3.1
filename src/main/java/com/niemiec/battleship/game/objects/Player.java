package com.niemiec.battleship.game.objects;

public interface Player extends Cloneable {
	public final static int FIRST_PLAYER = 0;
	public final static int SECOND_PLAYER = 1;
	
	public Board getBoard();
	public Board getOpponentBoard();
	
	public int getSunkenShips();
	public void increaseSunkenShips();
	
	public CollectionShips getCollectionShips();
	
	public boolean getInformationInThePlayerIsVirtual();
	public boolean isVirtualPlayer();
	
	public String getNick();
	Object clone();
}
