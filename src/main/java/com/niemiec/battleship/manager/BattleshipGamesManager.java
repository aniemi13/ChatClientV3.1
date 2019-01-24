package com.niemiec.battleship.manager;

import java.util.ArrayList;

import com.niemiec.battleship.controllers.MainScreenController;
import com.niemiec.battleship.game.logic.AddShips;
import com.niemiec.battleship.game.logic.BorderManagement;
import com.niemiec.battleship.view.BattleshipView;

public class BattleshipGamesManager {
	private ArrayList<String> opponentPlayers;
	private ArrayList<BattleshipGame> battleshipGames;
	private ArrayList<BattleshipView> battleshipViews;
	private ArrayList<MainScreenController> mainScreenControllers;
	private ArrayList<BorderManagement> borderManagements;
	private ArrayList<AddShips> addShips;
	
	public BattleshipGamesManager() {
		this.opponentPlayers = new ArrayList<>();
		this.battleshipGames = new ArrayList<>();
		this.battleshipViews = new ArrayList<>();
		this.mainScreenControllers = new ArrayList<>();
		this.borderManagements = new ArrayList<>();
		this.addShips = new ArrayList<>();
	}
	

	public void addBattleshipGame(BattleshipGame battleshipGame, BattleshipView battleshipView) {
		opponentPlayers.add(battleshipGame.getOpponentPlayerNick());
		battleshipGames.add(battleshipGame);
		battleshipViews.add(battleshipView);
		mainScreenControllers.add(battleshipView.getMainScreenController());
		
		BorderManagement borderManagement = new BorderManagement();
		borderManagements.add(borderManagement);
		
		AddShips aS = new AddShips();
		aS.addBorderManagement(borderManagement);
		addShips.add(aS);
		
	}
	
	public BattleshipGame getBattleshipGame(String opponentPlayerNick) {
		for (int i = 0; i < opponentPlayers.size(); i++) {
			if (opponentPlayers.get(i).equals(opponentPlayerNick)) {
				return battleshipGames.get(i);
			}
		}
		return null;
	}
	
	public BattleshipGame getBattleshipGame(BattleshipGame battleshipGame) {
		return getBattleshipGame(battleshipGame.getOpponentPlayerNick());
		
	}

	public BattleshipView getBattleshipView(String opponentPlayerNick) {
		for (int i = 0; i < opponentPlayers.size(); i++) {
			if (opponentPlayers.get(i).equals(opponentPlayerNick)) {
				return battleshipViews.get(i);
			}
		}
		return null;
	}
	
	public BattleshipView getBattleshipView(BattleshipGame battleshipGame) {
		return getBattleshipView(battleshipGame.getOpponentPlayerNick());
		
	}
	
	public MainScreenController getMainScreenController(String opponentPlayerNick) {
		for (int i = 0; i < opponentPlayers.size(); i++) {
			if (opponentPlayers.get(i).equals(opponentPlayerNick)) {
				return mainScreenControllers.get(i);
			}
		}
		return null;
	}
	
	public MainScreenController getMainScreenController(BattleshipGame battleshipGame) {
		return getMainScreenController(battleshipGame.getOpponentPlayerNick());
		
	}
	
	public BorderManagement getBorderManagement(String opponentPlayerNick) {
		for (int i = 0; i < opponentPlayers.size(); i++) {
			if (opponentPlayers.get(i).equals(opponentPlayerNick)) {
				return borderManagements.get(i);
			}
		}
		return null;
	}
	
	public BorderManagement getBorderManagement(BattleshipGame battleshipGame) {
		for (int i = 0; i < opponentPlayers.size(); i++) {
			if (battleshipGame.getGameIndex() == battleshipGames.get(i).getGameIndex()) {
				return borderManagements.get(i);
			}
		}
		return null;
	}
	
	public String getOpponentPlayerNick(BattleshipGame battleshipGame) {
		for (int i = 0; i < battleshipGames.size(); i++) {
			if (battleshipGame.getGameIndex() == battleshipGames.get(i).getGameIndex()) {
				return opponentPlayers.get(i);
			}
		}
		return null;
	}
	
	public void deleteBattleshipGame(String opponentPlayerNick) {
		for (int i = 0; i < opponentPlayers.size(); i++) {
			if (opponentPlayers.get(i).equals(opponentPlayerNick)) {
				battleshipGames.remove(i);
				battleshipViews.remove(i);
				mainScreenControllers.remove(i);
				opponentPlayers.remove(i);
				borderManagements.remove(i);
				addShips.remove(i);
				return;
			}
		}
	}

	public void updateBattleshipGame(BattleshipGame battleshipGame) {
		for (int i = 0; i < opponentPlayers.size(); i++) {

			if (opponentPlayers.get(i).equals(battleshipGame.getOpponentPlayerNick())) {
				battleshipGames.set(i, battleshipGame);
				return;
			}
		}
	}

	public AddShips getAddShip(String opponentPlayerNick) {
		for (int i = 0; i < opponentPlayers.size(); i++) {
			if (opponentPlayers.get(i).equals(opponentPlayerNick)) {
				return addShips.get(i);
			}
		}
		return null;
	}
	
	public AddShips getAddShip(BattleshipGame battleshipGame) {
		return getAddShip(battleshipGame.getOpponentPlayerNick());
	}


	public void showStatistic() {
		System.out.println("*************BATTLESHIP GAMES MANAGER STATISTIC***************");
		for (int i = 0; i < opponentPlayers.size(); i++) {
			BattleshipGame b = battleshipGames.get(i);
			System.out.println("INDEX: " + b.getGameIndex() + ", opponent: " + b.getOpponentPlayerNick());
			System.out.println("Nick z ArrayList opponentPlayers: " + opponentPlayers.get(i));
			System.out.println("# # #                                                # # #");
		}
		System.out.println("**************************************************************");
	}


	public boolean checkIfExist(String opponentPlayerNick) {
		for (int i = 0; i < opponentPlayers.size(); i++) {
			if (opponentPlayers.get(i).equals(opponentPlayerNick))
				return true;
		}
		return false;
	}
}
