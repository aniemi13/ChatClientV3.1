package com.niemiec.battleship.view;

import com.niemiec.battleship.controllers.MainScreenController;
import com.niemiec.battleship.controllers.AcceptanceWindowController;
import com.niemiec.battleship.controllers.EndGameInformationAndAcceptanceController;
import com.niemiec.battleship.controllers.InformationAndAcceptanceController;
import com.niemiec.battleship.controllers.WaitingWindowController;
import com.niemiec.battleship.logic.BattleshipManagement;
import com.niemiec.chat.objects.Client;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class BattleshipView {
	private String nick;
	private String opponentPlayerNick;
	private Client client;
	private BattleshipManagement battleshipManagement;

	private MainScreenController mainScreenController;
	private FXMLLoader mainScreenLoader;
	private VBox vBoxMainScreen;
	private Stage stageMainScreen;

	private WaitingWindowController waitingWindowController = null;
	private FXMLLoader waitingWindowLoader;
	private VBox vBoxWaitingWindow;
	private Stage stageWaitingWindows;

	private FXMLLoader acceptanceWindowLoader;
	private AcceptanceWindowController acceptanceWindowController;
	private VBox vBoxAcceptanceWindow;
	private Stage stageAcceptanceWindow;

	private FXMLLoader informationAndAcceptanceWindowLoader;
	private InformationAndAcceptanceController informationAndAcceptanceController;
	private VBox vBoxInformationAndAcceptanceWindow;
	private Stage stageInformationAndAcceptanceWindow;

	private FXMLLoader endGameInformationAndAcceptanceWindowLoader;
	private EndGameInformationAndAcceptanceController endGameInformationAndAcceptanceController;
	private VBox vBoxEndGameInformationAndAcceptanceWindow;
	private Stage stageEndGameInformationAndAcceptanceWindow;

	public BattleshipView(String nick, String opponentNick, Client client, BattleshipManagement battleshipManagement) {
		this.nick = nick;
		this.opponentPlayerNick = opponentNick;
		this.client = client;
		this.battleshipManagement = battleshipManagement;
	}

	private void loadMainScreenFXMLLoaderAndController() {
		mainScreenLoader = new FXMLLoader(this.getClass().getResource("/fxml/battleship/MainScreen.fxml"));
		vBoxMainScreen = null;
		try {
			vBoxMainScreen = mainScreenLoader.load();
		} catch (Exception e) {
			System.out.println("NIE WCZYTANO VBOX " + e);

		}
		mainScreenController = mainScreenLoader.getController();
		mainScreenController.setClient(this.client);
		mainScreenController.setOpponentPlayerNick(opponentPlayerNick);
		mainScreenController.setNick(nick);
		mainScreenController.setBattleshipManagement(battleshipManagement);
		mainScreenController.initializeDataToAddedShips();
	}

	public void showBattleshipWindow() {

		Platform.runLater(() -> {
			loadMainScreenFXMLLoaderAndController();

			stageMainScreen = new Stage();
			Scene scene = new Scene(vBoxMainScreen);
			stageMainScreen.setTitle("Battleship: TY " + " vs " + opponentPlayerNick);
			stageMainScreen.setScene(scene);

			stageMainScreen.show();
		});
	}

	public void showAddedShipsBattleshipWindow() {

	}

	public void closeBattleshipWindow() {
		Platform.runLater(() -> {
			stageMainScreen.close();
		});
	}

	private void loadWaitingWindow() {
		waitingWindowLoader = new FXMLLoader(
				this.getClass().getResource("/fxml/battleship/WaitingForAcceptanceWindow.fxml"));
		vBoxWaitingWindow = null;
		try {
			vBoxWaitingWindow = waitingWindowLoader.load();
		} catch (Exception e) {
		}
		waitingWindowController = waitingWindowLoader.getController();
	}

	public void showWaitingWindow(String message) {
		loadWaitingWindow();

		Platform.runLater(() -> {
			stageWaitingWindows = new Stage();
			Scene scene = new Scene(vBoxWaitingWindow);
			stageWaitingWindows.setScene(scene);
			waitingWindowController.setTextLabel(message);
			stageWaitingWindows.show();
		});
	}

	public void closeWaitingWindow() {
		Platform.runLater(() -> {
			stageWaitingWindows.close();
		});
	}

	public WaitingWindowController getWaitingWindowController() {
		return waitingWindowController;
	}

	private void loadAcceptanceWindow() {
		acceptanceWindowLoader = new FXMLLoader(this.getClass().getResource("/fxml/battleship/AcceptanceWindow.fxml"));
		vBoxAcceptanceWindow = null;
		try {
			vBoxAcceptanceWindow = acceptanceWindowLoader.load();
		} catch (Exception e) {
		}
		acceptanceWindowController = acceptanceWindowLoader.getController();
		acceptanceWindowController.setClient(client);
		acceptanceWindowController.setOpponentPlayerNick(opponentPlayerNick);
	}

	public void showAcceptanceWindow() {
		loadAcceptanceWindow();

		Platform.runLater(() -> {
			stageAcceptanceWindow = new Stage();
			Scene scene = new Scene(vBoxAcceptanceWindow);
			stageAcceptanceWindow.setScene(scene);
			acceptanceWindowController.setTextLabel("Otrzymałeś propozycję gry od użytkownika " + opponentPlayerNick);
			acceptanceWindowController.setOpponentPlayerNick(opponentPlayerNick);
			stageAcceptanceWindow.setOnCloseRequest(e -> acceptanceWindowController.rejectionGameProposal());
			stageAcceptanceWindow.show();
		});
	}

	public void closeAcceptanceWindow() {
		Platform.runLater(() -> {
			stageAcceptanceWindow.close();
		});
	}

	private void loadInformationAndAcceptanceWindow() {
		informationAndAcceptanceWindowLoader = new FXMLLoader(
				this.getClass().getResource("/fxml/battleship/InformationAndAcceptanceWindow.fxml"));
		vBoxInformationAndAcceptanceWindow = null;

		try {
			vBoxInformationAndAcceptanceWindow = informationAndAcceptanceWindowLoader.load();
		} catch (Exception e) {
		}
		informationAndAcceptanceController = informationAndAcceptanceWindowLoader.getController();
		informationAndAcceptanceController.setBattleshipManagement(battleshipManagement);
		informationAndAcceptanceController.setOpponentPlayerNick(opponentPlayerNick);

	}

	public void showInformationAndAcceptanceWindow(String message) {
		loadInformationAndAcceptanceWindow();

		Platform.runLater(() -> {
			stageInformationAndAcceptanceWindow = new Stage();
			Scene scene = new Scene(vBoxInformationAndAcceptanceWindow);
			stageInformationAndAcceptanceWindow.setScene(scene);
			informationAndAcceptanceController.setTextLabel(message);
			stageInformationAndAcceptanceWindow.setOnCloseRequest(e -> informationAndAcceptanceController.understand());
			stageInformationAndAcceptanceWindow.show();
		});
	}

	public void closeInformationAndAcceptanceWindow() {
		Platform.runLater(() -> {
			stageInformationAndAcceptanceWindow.close();
		});
	}

	private void loadEndGameInformationAndAcceptanceWindow() {
		endGameInformationAndAcceptanceWindowLoader = new FXMLLoader(
				this.getClass().getResource("/fxml/battleship/EndGameInformationAndAcceptanceWindow.fxml"));
		vBoxEndGameInformationAndAcceptanceWindow = null;

		try {
			vBoxEndGameInformationAndAcceptanceWindow = endGameInformationAndAcceptanceWindowLoader.load();
		} catch (Exception e) {
		}
		endGameInformationAndAcceptanceController = endGameInformationAndAcceptanceWindowLoader.getController();
		endGameInformationAndAcceptanceController.setBattleshipManagement(battleshipManagement);
		endGameInformationAndAcceptanceController.setOpponentPlayerNick(opponentPlayerNick);
	}

	public void showEndGameInformationAndAcceptanceWindow(String message) {
		Platform.runLater(() -> {
			loadEndGameInformationAndAcceptanceWindow();

			stageEndGameInformationAndAcceptanceWindow = new Stage();
			Scene scene = new Scene(vBoxEndGameInformationAndAcceptanceWindow);
			stageEndGameInformationAndAcceptanceWindow.setScene(scene);
			endGameInformationAndAcceptanceController.setTextLabel(message);
			stageEndGameInformationAndAcceptanceWindow.show();
			mainScreenController.setDisableMenu();
		});
	}

	public void closeEndGameInformationAndAcceptanceWindow() {
		Platform.runLater(() -> {
			stageEndGameInformationAndAcceptanceWindow.close();
		});
	}

	public MainScreenController getMainScreenController() {
		return mainScreenController;
	}
}
