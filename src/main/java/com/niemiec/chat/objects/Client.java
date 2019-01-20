package com.niemiec.chat.objects;

import com.niemiec.battleship.game.objects.Player;
import com.niemiec.chat.connection.Connection;
import com.niemiec.chat.controllers.ChatController;
import com.niemiec.chat.controllers.GetNickController;
import com.niemiec.chat.logic.MessagesManagement;

import javafx.event.ActionEvent;

public class Client {
	private Connection connection;
	private MessagesManagement messagesManagement;

	public Client(String host, int port) {
		messagesManagement = new MessagesManagement(this);
		connection = new Connection(messagesManagement, host, port);
		connection.start();
	}

	public void setUserNickToPrivateMessage(String actualInterlocutor) {
		messagesManagement.setActualInterlocutor(actualInterlocutor);
	}

	public void sendToGeneralChat(String message) {
		connection.sendTheObject(messagesManagement.sendToGeneralChat(message));
	}

	public void sendToPrivateChat(String message) {
		connection.sendTheObject(messagesManagement.sendToPrivateChat(message));
	}

	public void exit() {
		connection.sendTheObject(messagesManagement.exit());
		connection.interrupt();
	}

	public void setNick(String nick) {
		messagesManagement.setNick(nick);
	}

	public void readyToWork() {
		connection.sendTheObject(messagesManagement.sendReadyToWork());
	}

	public void setGetNickController(GetNickController getNickController) {
		messagesManagement.setGetNickController(getNickController);
	}

	public void setChatController(ChatController chatController) {
		messagesManagement.setChatController(chatController);
	}

	public void sendNickToCheck(String nick) {
		connection.sendTheObject(messagesManagement.sendNickToCheck(nick));
	}

	public void playBattleships() {
		if (!messagesManagement.whetherTheBattleshipGameExists())
			connection.sendTheObject(messagesManagement.playBattleship());
	}

	public void sendBattleshipGame(String opponentPlayerNick, ActionEvent event) {
		connection.sendTheObject(messagesManagement.sendBattleshipGame(opponentPlayerNick, event));
	}

	public void acceptTheBattleshipGame(boolean isAccept, String opponentPlayerNick) {
		connection.sendTheObject(messagesManagement.sendAcceptTheBattleshipGame(isAccept, opponentPlayerNick));
	}

	public void sendShipsAdded(String opponentPlayerNick, Player player) {
		connection.sendTheObject(messagesManagement.sendShipsAdded(opponentPlayerNick, player));
	}

	public boolean checkIfTheButtonWasUsed(String opponentPlayerNick, ActionEvent event) {
		return messagesManagement.checkIfTheButtonWasUsed(opponentPlayerNick, event);
	}

	public boolean checkIfBattleshipGameHasBeenCompleted(String opponentPlayerNick) {
		return messagesManagement.checkIfBattleshipGameHasBeenCompleted(opponentPlayerNick);
	}

	public void sendResignationFromTheGame(String opponentPlayerNick) {
		connection.sendTheObject(messagesManagement.sendResignationFromTheGame(opponentPlayerNick));
	}

	public void closeBattleshipMainScreen(String opponentPlayerNick) {
		messagesManagement.closeBattleshipMainScreen(opponentPlayerNick);
	}

}
