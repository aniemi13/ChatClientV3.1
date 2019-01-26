package com.niemiec.battleship.logic;

import com.niemiec.battleship.game.data.check.CheckData;
import com.niemiec.battleship.game.logic.AddShips;
import com.niemiec.battleship.game.logic.BorderManagement;
import com.niemiec.battleship.game.objects.Board;
import com.niemiec.battleship.game.objects.Coordinates;
import com.niemiec.battleship.game.objects.Player;
import com.niemiec.battleship.game.objects.PlayerImpl;
import com.niemiec.battleship.manager.BattleshipGame;
import com.niemiec.battleship.manager.BattleshipGamesManager;
import com.niemiec.battleship.view.BattleshipView;
import com.niemiec.chat.objects.Client;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class BattleshipManagement {
	private Client client;
	private BattleshipGamesManager battleshipGamesManager;
	private ProcessingIncomingBattleshipGameMessage processingIncomingBattleshipGameMessage;

	public BattleshipManagement(Client client) {
		this.client = client;
		this.battleshipGamesManager = new BattleshipGamesManager();
		this.processingIncomingBattleshipGameMessage = new ProcessingIncomingBattleshipGameMessage(client, battleshipGamesManager, this);
	}

	public void receiveBattleshipGame(BattleshipGame battleshipGame) {
		switch (battleshipGame.getGameStatus()) {
		case BattleshipGame.GAME_PROPOSAL:
			processingIncomingBattleshipGameMessage.receiveGameProposal(battleshipGame);
			break;
		case BattleshipGame.REJECTION_GAME_PROPOSAL:
			processingIncomingBattleshipGameMessage.receiveRejectionGameProposal(battleshipGame);
			break;
		case BattleshipGame.ADD_SHIPS:
			processingIncomingBattleshipGameMessage.receiveAddShips(battleshipGame);
			break;
		case BattleshipGame.PLAY_THE_GAME:
			processingIncomingBattleshipGameMessage.receivePlayTheGame(battleshipGame);
			break;
		case BattleshipGame.END_GAME:
			processingIncomingBattleshipGameMessage.receiveEndGame(battleshipGame);
			break;
		}
	}

	public Object playBattleship(String nick, String opponentPlayerNick) {
		BattleshipGame battleshipGame = new BattleshipGame();
		battleshipGame.setPlayer(new PlayerImpl(Player.SECOND_PLAYER, nick));
		battleshipGame.setOpponentPlayerNick(opponentPlayerNick);
		BattleshipView battleshipView = new BattleshipView(opponentPlayerNick, client, this);

		battleshipGamesManager.addBattleshipGame(battleshipGame, battleshipView);
		battleshipView.showWaitingWindow("Oczekiwanie na akcpetację użytkownika " + opponentPlayerNick);
		return battleshipGame;
	}

	public Object sendBattleshipGame(String opponentPlayerNick, ActionEvent event) {
		BattleshipGame battleshipGame = battleshipGamesManager.getBattleshipGame(opponentPlayerNick);
		Coordinates coordinates = CheckData.getCoordinatesFromButton((Button) event.getSource());
		battleshipGame.setShotCoordinates(coordinates);
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

	public Object sendRejectionGameProspalWhenBattleshipGameNotDelete(BattleshipGame battleshipGame) {
		battleshipGame.setGameStatus(BattleshipGame.REJECTION_GAME_PROPOSAL);
		return battleshipGame;
	}

	public Object sendShipsAdded(String opponentPlayerNick) {
		BattleshipGame battleshipGame = battleshipGamesManager.getBattleshipGame(opponentPlayerNick);
		battleshipGame.setGameStatus(BattleshipGame.SHIPS_ADDED);

		Player player = battleshipGamesManager.getAddShip(battleshipGame).getPlayer(Player.SECOND_PLAYER);
		battleshipGame.setPlayer(player);

		battleshipGamesManager.getBorderManagement(battleshipGame).setBordersToEndGame();

		return battleshipGame;
	}

	public void setNick(String nick) {
		processingIncomingBattleshipGameMessage.setNick(nick);
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

	public boolean addShips(String opponentPlayerNick, ActionEvent event) {
		AddShips addShips = battleshipGamesManager.getAddShip(opponentPlayerNick);
		return addShips.addShipsManually(Player.SECOND_PLAYER, event);
	}

	public boolean addShipsAutomatically(String opponentPlayerNick) {
		AddShips addShips = battleshipGamesManager.getAddShip(opponentPlayerNick);
		return addShips.addShipsAutomatically(Player.SECOND_PLAYER);
	}

	public void closeBattleshipGame(String opponentPlayerNick) {
		BattleshipView battleshipView = battleshipGamesManager.getBattleshipView(opponentPlayerNick);
		battleshipView.closeBattleshipWindow();
		battleshipGamesManager.deleteBattleshipGame(opponentPlayerNick);
	}

	public void closeBattleshipGames() {
		for (int i = 0 ; i < battleshipGamesManager.getNumberOfBattleshipGames(); i++) {
			String opponentPlayerNick = battleshipGamesManager.getFirstOpponentPlayer();
			client.closeBattleshipGame(opponentPlayerNick);
			battleshipGamesManager.deleteBattleshipGame(opponentPlayerNick);
		}
	}
}
