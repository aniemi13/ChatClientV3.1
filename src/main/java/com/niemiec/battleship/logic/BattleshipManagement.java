package com.niemiec.battleship.logic;

import com.niemiec.battleship.game.data.check.CheckData;
import com.niemiec.battleship.game.logic.BorderManagement;
import com.niemiec.battleship.game.objects.Board;
import com.niemiec.battleship.game.objects.Coordinates;
import com.niemiec.battleship.game.objects.Player;
import com.niemiec.battleship.manager.BattleshipGame;
import com.niemiec.battleship.manager.BattleshipGamesManager;
import com.niemiec.battleship.view.BattleshipView;
import com.niemiec.chat.objects.Client;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class BattleshipManagement {
	private Client client;
	private String nick;
	private BattleshipGamesManager battleshipGamesManager;

	public BattleshipManagement(Client client) {
		this.client = client;
		this.battleshipGamesManager = new BattleshipGamesManager();
	}

	public void receiveBattleshipGame(BattleshipGame battleshipGame) {
		switch (battleshipGame.getGameStatus()) {
		case BattleshipGame.GAME_PROPOSAL:
			receiveGameProposal(battleshipGame);
			break;
		case BattleshipGame.REJECTION_GAME_PROPOSAL:
			receiveRejectionGameProposal(battleshipGame);
			break;
		case BattleshipGame.ADD_SHIPS:
			receiveAddShips(battleshipGame);
			break;
		case BattleshipGame.START_THE_GAME:
			receiveStartTheGame(battleshipGame);
			break;
		case BattleshipGame.END_GAME:
			receiveEndGame(battleshipGame);
			break;
		}
	}

	private void receiveEndGame(BattleshipGame battleshipGame) {
		System.out.println("Jestem graczem " + nick + ", a wygrał gracz: " + battleshipGame.getWinner());
		updateBorder(battleshipGame);
		battleshipGamesManager.updateBattleshipGame(battleshipGame);
		battleshipGamesManager.getBorderManagement(battleshipGame).setBordersToEndGame();
		BattleshipView battleshipView = battleshipGamesManager
				.getBattleshipView(battleshipGame.getOpponentPlayerNick());
		battleshipView.showEndGameInformationAndAcceptanceWindow("Wygrywa gracz: " + battleshipGame.getWinner());
	}

	private void receiveGameProposal(BattleshipGame battleshipGame) {
		String nick = battleshipGame.getInvitingPlayerNick();
		String opponentPlayerNick = battleshipGame.getOpponentPlayerNick();
		deleteBattleshipGameIfExsistInformationController(opponentPlayerNick);
		BattleshipView battleshipView = new BattleshipView(nick, opponentPlayerNick, client, this);
		battleshipGamesManager.addBattleshipGame(battleshipGame, battleshipView);
		battleshipView.showAcceptanceWindow();
	}

	private void deleteBattleshipGameIfExsistInformationController(String opponentPlayerNick) {
		if (battleshipGamesManager.checkIfExist(opponentPlayerNick)) {
			BattleshipView battleshipView = battleshipGamesManager.getBattleshipView(opponentPlayerNick);
			battleshipView.closeInformationAndAcceptanceWindow();
			battleshipGamesManager.deleteBattleshipGame(opponentPlayerNick);
		}
	}

	private void receiveRejectionGameProposal(BattleshipGame battleshipGame) {
		String opponentPlayerNick = battleshipGame.getOpponentPlayerNick();
		BattleshipView battleshipView = battleshipGamesManager.getBattleshipView(opponentPlayerNick);
		battleshipView.closeWaitingWindow();
		battleshipView.showInformationAndAcceptanceWindow("Użytkownik " + opponentPlayerNick + " nie zaakceptował gry");
	}

	private void receiveAddShips(BattleshipGame battleshipGame) {
		String opponentPlayerNick = battleshipGame.getOpponentPlayerNick();
		BattleshipView battleshipView = battleshipGamesManager.getBattleshipView(opponentPlayerNick);
		battleshipGamesManager.updateBattleshipGame(battleshipGame);
		if (battleshipGamesManager.getBattleshipView(opponentPlayerNick).getWaitingWindowController() != null)
			battleshipView.closeWaitingWindow();
		battleshipView.showBattleshipWindow();
		Platform.runLater(() -> {
			battleshipGamesManager.getBorderManagement(battleshipGame).startNewGameWithVirtualPlayer();
		});
	}

	private void receiveStartTheGame(BattleshipGame battleshipGame) {
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

	public Object playBattleship(String nick, String opponentPlayerNick) {
		BattleshipGame battleshipGame = new BattleshipGame(nick, opponentPlayerNick);
		BattleshipView battleshipView = new BattleshipView(nick, opponentPlayerNick, client, this);
		battleshipGamesManager.addBattleshipGame(battleshipGame, battleshipView);
		battleshipView.showWaitingWindow("Oczekiwanie na akcpetację użytkownika " + opponentPlayerNick);
		return battleshipGame;
	}

	public Object sendBattleshipGame(String opponentPlayerNick, ActionEvent event) {
		BattleshipGame battleshipGame = battleshipGamesManager.getBattleshipGame(opponentPlayerNick);
		Coordinates coordinates = CheckData.getCoordinatesFromButton((Button) event.getSource());
		battleshipGame.setCoordinates(coordinates);
		getBorderManagement(opponentPlayerNick).setBordersToEndGame();

		return battleshipGame;
	}

	public Object sendAcceptTheBattleshipGame(boolean isAccept, String opponentPlayerNick) {
		BattleshipGame battleshipGame = battleshipGamesManager.getBattleshipGame(opponentPlayerNick);
		battleshipGamesManager.getBattleshipView(opponentPlayerNick).closeAcceptanceWindow();

		if (isAccept) {
			battleshipGame.setGameStatus(BattleshipGame.ACCEPTING_THE_GAME);
		} else {
			battleshipGame.setGameStatus(BattleshipGame.REJECTION_GAME_PROPOSAL);
			deleteBattleshipGame(opponentPlayerNick);
		}
		return battleshipGame;
	}

	public boolean whetherTheBattleshipGameExists(String opponentPlayerNick) {
		return battleshipGamesManager.getBattleshipGame(opponentPlayerNick) != null;
	}

	public void deleteBattleshipGame(String opponentPlayerNick) {
		battleshipGamesManager.deleteBattleshipGame(opponentPlayerNick);
	}

	public void acceptRejectionGameProspalInformation(String opponentPlayerNick) {
		BattleshipView battleshipView = battleshipGamesManager.getBattleshipView(opponentPlayerNick);
		battleshipView.closeInformationAndAcceptanceWindow();
		deleteBattleshipGame(opponentPlayerNick);
	}

	public Object sendShipsAdded(String opponentPlayerNick, Player player) {
		BattleshipGame battleshipGame = battleshipGamesManager.getBattleshipGame(opponentPlayerNick);
		battleshipGame.setGameStatus(BattleshipGame.SHIPS_ADDED);
		battleshipGame.setInvitingPlayer(player);

		return battleshipGame;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

	public boolean checkIfTheButtonWasUsed(String opponentPlayerNick, ActionEvent event) {
		BattleshipGame battleshipGame = battleshipGamesManager.getBattleshipGame(opponentPlayerNick);
		Coordinates coordinates = CheckData.getCoordinatesFromButton((Button) event.getSource());

		int box = battleshipGame.getPlayer().getOpponentBoard().getBox(coordinates);
		if (box == Board.BOX_EMPTY)
			return false;
		else
			return true;
	}

	public void acceptEndGame(String opponentPlayerNick) {
		BattleshipView battleshipView = battleshipGamesManager.getBattleshipView(opponentPlayerNick);
		battleshipView.closeBattleshipWindow();
		battleshipView.closeEndGameInformationAndAcceptanceWindow();
		battleshipGamesManager.deleteBattleshipGame(opponentPlayerNick);
	}

	public void setBordersToBorderManagement(VBox myBorder, VBox opponentBorder, String opponentPlayerNick) {
		battleshipGamesManager.getBorderManagement(opponentPlayerNick).setBorders(myBorder, opponentBorder);
	}

	public BorderManagement getBorderManagement(String opponentPlayerNick) {
		return battleshipGamesManager.getBorderManagement(opponentPlayerNick);
	}

	public boolean checkIfBattleshipGameHasBeenCompleted(String opponentPlayerNick) {
		BattleshipGame battleshipGame = battleshipGamesManager.getBattleshipGame(opponentPlayerNick);
		return battleshipGame.getGameStatus() == BattleshipGame.END_GAME;
	}

	public Object sendResignationFromTheGame(String opponentPlayerNick) {
		BattleshipGame battleshipGame = battleshipGamesManager.getBattleshipGame(opponentPlayerNick);
		battleshipGame.setGameStatus(BattleshipGame.END_GAME);
		battleshipGame.setWinnerNick(opponentPlayerNick);

		return battleshipGame;
	}

	public void closeBattleshipMainScreen(String opponentPlayerNick) {
		// TODO Auto-generated method stub

	}
}
