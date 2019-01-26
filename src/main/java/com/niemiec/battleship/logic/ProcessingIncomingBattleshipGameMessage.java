package com.niemiec.battleship.logic;

import com.niemiec.battleship.game.logic.AddShips;
import com.niemiec.battleship.game.logic.BorderManagement;
import com.niemiec.battleship.manager.BattleshipGame;
import com.niemiec.battleship.manager.BattleshipGamesManager;
import com.niemiec.battleship.view.BattleshipView;
import com.niemiec.chat.objects.Client;

import javafx.application.Platform;

public class ProcessingIncomingBattleshipGameMessage {
	private Client client;
	private String nick;
	private BattleshipGamesManager battleshipGamesManager;
	private BattleshipManagement battleshipManagement;
	
	public ProcessingIncomingBattleshipGameMessage(Client client, BattleshipGamesManager battleshipGamesManager, BattleshipManagement battleshipManagement) {
		this.client = client;
		this.battleshipGamesManager = battleshipGamesManager;
		this.battleshipManagement = battleshipManagement;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

	public void receiveGameProposal(BattleshipGame battleshipGame) {
		String opponentPlayerNick = battleshipGame.getOpponentPlayerNick();
		if (checkIfTheGameNotExist(battleshipGame)) {
			deleteBattleshipGameIfExsistInformationController(opponentPlayerNick);
			BattleshipView battleshipView = new BattleshipView(opponentPlayerNick, client, battleshipManagement);
			battleshipGamesManager.addBattleshipGame(battleshipGame, battleshipView);
			battleshipView.showAcceptanceWindow();
		} else {
			client.sendRejectionGameProspalWhenBattleshipGameNotDelete(battleshipGame);
		}
	}
	
	private boolean checkIfTheGameNotExist(BattleshipGame battleshipGame) {
		return battleshipGamesManager.getBattleshipGame(battleshipGame) == null;
	}

	private void deleteBattleshipGameIfExsistInformationController(String opponentPlayerNick) {
		if (battleshipGamesManager.checkIfExist(opponentPlayerNick)) {
			BattleshipView battleshipView = battleshipGamesManager.getBattleshipView(opponentPlayerNick);
			battleshipView.closeInformationAndAcceptanceWindow();
			battleshipGamesManager.deleteBattleshipGame(opponentPlayerNick);
		}
	}

	public void receiveRejectionGameProposal(BattleshipGame battleshipGame) {
		BattleshipView battleshipView = battleshipGamesManager.getBattleshipView(battleshipGame);
		battleshipView.closeWaitingWindow();
		battleshipView.showInformationAndAcceptanceWindow(
				"Użytkownik " + battleshipGame.getOpponentPlayerNick() + " nie zaakceptował gry");
	}
	
	public void receiveAddShips(BattleshipGame battleshipGame) {
		BattleshipView battleshipView = battleshipGamesManager.getBattleshipView(battleshipGame);
		battleshipGamesManager.updateBattleshipGame(battleshipGame);
		if (battleshipView.getWaitingWindowController() != null) {
			battleshipView.closeWaitingWindow();
		}
		updateAddShips(battleshipGame);
		battleshipView.showBattleshipWindow();
		Platform.runLater(() -> {
			battleshipGamesManager.getBorderManagement(battleshipGame).startNewGameWithVirtualPlayer();
		});
	}
	
	private void updateAddShips(BattleshipGame battleshipGame) {
		AddShips addShips = battleshipGamesManager.getAddShip(battleshipGame);
		addShips.addOneRealPlayer(battleshipGame.getPlayer());
	}
	
	public void receivePlayTheGame(BattleshipGame battleshipGame) {
		updateBorder(battleshipGame);
		battleshipGamesManager.updateBattleshipGame(battleshipGame);
		if (battleshipGame.getNickWhoseTourn().equals(nick)) {
			battleshipGamesManager.getBorderManagement(battleshipGame).setBordersToStartShot();
		}
	}
	
	private void updateBorder(BattleshipGame battleshipGame) {
		BorderManagement b = battleshipGamesManager.getBorderManagement(battleshipGame);
		if (battleshipGame.getPlayer() != null) {
			Platform.runLater(() -> {
				b.drawBoardInMyBorder(battleshipGame.getPlayer());
				b.drawOpponentBoardInOpponentBorder(battleshipGame.getPlayer());
			});
		}
	}
	
	public void receiveEndGame(BattleshipGame battleshipGame) {
		updateBorder(battleshipGame);
		//sprawdzić czy istnieje mainscreen, jak tak to wyświetlić to co niżej
		//jeżeli nie to pozamykać inne okna i wyświetlić komunikat o końcu gry
		battleshipGamesManager.updateBattleshipGame(battleshipGame);
		battleshipGamesManager.getBorderManagement(battleshipGame).setBordersToEndGame();
		BattleshipView battleshipView = battleshipGamesManager.getBattleshipView(battleshipGame);
		battleshipView.showEndGameInformationAndAcceptanceWindow("Wygrywa gracz: " + battleshipGame.getWinnerNick());
	}
}
