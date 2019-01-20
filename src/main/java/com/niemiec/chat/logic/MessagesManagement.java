package com.niemiec.chat.logic;

import com.niemiec.battleship.game.objects.Player;
import com.niemiec.battleship.logic.BattleshipManagement;
import com.niemiec.battleship.manager.BattleshipGame;
import com.niemiec.chat.controllers.ChatController;
import com.niemiec.chat.controllers.GetNickController;
import com.niemiec.chat.messages.CheckNickMessage;
import com.niemiec.chat.messages.ExitMessage;
import com.niemiec.chat.messages.GroupMessage;
import com.niemiec.chat.messages.PrivateMessage;
import com.niemiec.chat.messages.ReadyToWorkMessage;
import com.niemiec.chat.messages.UsersListMessage;
import com.niemiec.chat.objects.Client;
import com.niemiec.chat.objects.GeneralChat;
import com.niemiec.chat.objects.managers.InterlocutorsManager;

import javafx.event.ActionEvent;

public class MessagesManagement {
	private ChatController chatController;
	private GetNickController getNickController;
	private InterlocutorsManager interlocutorsManager;
	private GeneralChat generalChat;
	private String nick;
	private String actualInterlocutor;

	private BattleshipManagement battleshipManagement;

	public MessagesManagement(Client client) {
		this.actualInterlocutor = "";
		this.interlocutorsManager = new InterlocutorsManager();
		this.generalChat = new GeneralChat();
		this.battleshipManagement = new BattleshipManagement(client);
	}

	public void receiveTheObject(Object object) {
		if (object instanceof GroupMessage) {
			receiveGroupMessage((GroupMessage) object);
		} else if (object instanceof PrivateMessage) {
			receivePrivateMessage((PrivateMessage) object);
		} else if (object instanceof UsersListMessage) {
			receiveUsersListMessage((UsersListMessage) object);
		} else if (object instanceof BattleshipGame) {
			receiveBattleshipGame((BattleshipGame) object);
		} else if (object instanceof CheckNickMessage) {
			receiveCheckNickMessage((CheckNickMessage) object);
		}
	}

	private void receivePrivateMessage(PrivateMessage privateMessage) {
		String senderNick = privateMessage.getSenderNick();
		addInterlocutorIfNotExist(senderNick);
		interlocutorsManager.addMessage(senderNick, createMessageForShow(senderNick, privateMessage.getMessage()));

		if (senderNick.equals(actualInterlocutor)) {
			interlocutorsManager.setMessageIsRead(senderNick, true);
			addMessageToPrivateChat(privateMessage);
		} else {
			// TODO jeżeli nie to podświetlenie pola
			interlocutorsManager.setMessageIsRead(senderNick, false);
		}
	}

	private void receiveGroupMessage(GroupMessage groupMessage) {
		String senderNick = groupMessage.getSenderNick();
		String m = createMessageForShow(senderNick, groupMessage.getMessage());
		generalChat.addMessage(m);
		chatController.addMessageToGeneralChat(m);
	}

	private void receiveCheckNickMessage(CheckNickMessage checkNickMessage) {
		getNickController.reciveTheInformation(checkNickMessage.isNickNotExist());
	}

	public void receiveUsersListMessage(UsersListMessage usersListMessage) {
		chatController.updateUsersList(usersListMessage.getUsersArrayList());
		// TODO
//		battleshipGamesManager.updateUsersList(usersListMessage.getUsersArrayList());
	}

	public void setActualInterlocutor(String selectedNick) {

		if (selectedNick.equals(this.nick)) {
			chatController.lockPrivateChat();
			actualInterlocutor = "";
		} else if (!selectedNick.equals(actualInterlocutor)) {
			addInterlocutorIfNotExist(selectedNick);
			actualInterlocutor = selectedNick;
			updatePrivateChatListView();
			// TODO WIADOMOŚĆ ODCZYTANA - ZNIKA PODŚWIETLENIE UŻYTKOWNIKA
			interlocutorsManager.setMessageIsRead(selectedNick, true);
			chatController.unlockPrivateChat();
		}

	}

	private void addInterlocutorIfNotExist(String nick) {
		if (!interlocutorsManager.isExist(nick)) {
			interlocutorsManager.addInterlocutor(nick);
		}
	}

	private void updatePrivateChatListView() {
		chatController.updatePrivateChat(interlocutorsManager.getMessages(actualInterlocutor));
	}

	private void addMessageToPrivateChat(PrivateMessage privateMessage) {
		String senderNick = privateMessage.getSenderNick();
		String m = createMessageForShow(senderNick, privateMessage.getMessage());
		chatController.addMessageToPrivateChat(m);
	}

	private String createMessageForShow(String nick, String message) {
		String front = ((nick.equals(this.nick)) ? "TY" : nick);
		return new String(front + "> " + message);
	}

	public Object sendToGeneralChat(String message) {
		return new GroupMessage(nick, message);
	}

	// TODO
	public void highlightTheFieldInListView(String nick) {

	}

	public Object sendToPrivateChat(String message) {
		PrivateMessage privateMessage = new PrivateMessage(nick, actualInterlocutor, message);
		addMessageToPrivateChat(privateMessage);
		interlocutorsManager.addMessage(actualInterlocutor, "Ty> " + message);
		return privateMessage;
	}

	public Object exit() {
		return new ExitMessage(nick);
	}

	public void setNick(String nick) {
		this.nick = nick;
		battleshipManagement.setNick(nick);
	}

	public Object sendReadyToWork() {
		return new ReadyToWorkMessage(nick);
	}

	public void setChatController(ChatController chatController) {
		this.chatController = chatController;
	}

	public void setGetNickController(GetNickController getNickController) {
		this.getNickController = getNickController;
	}

	public Object sendNickToCheck(String nick) {
		return new CheckNickMessage(nick);
	}

	private void receiveBattleshipGame(BattleshipGame battleshipGame) {
		battleshipManagement.receiveBattleshipGame(battleshipGame);
	}

	public Object playBattleship() {
		return battleshipManagement.playBattleship(nick, actualInterlocutor);
	}

	public Object sendBattleshipGame(String opponentPlayerNick, ActionEvent event) {
		return battleshipManagement.sendBattleshipGame(opponentPlayerNick, event);
	}

	public Object sendAcceptTheBattleshipGame(boolean isAccept, String opponentPlayerNick) {
		return battleshipManagement.sendAcceptTheBattleshipGame(isAccept, opponentPlayerNick);
	}

	public boolean whetherTheBattleshipGameExists() {
		return battleshipManagement.whetherTheBattleshipGameExists(actualInterlocutor);
	}

	public Object sendShipsAdded(String opponentPlayerNick, Player player) {
		return battleshipManagement.sendShipsAdded(opponentPlayerNick, player);
	}

	public boolean checkIfTheButtonWasUsed(String opponentPlayerNick, ActionEvent event) {
		return battleshipManagement.checkIfTheButtonWasUsed(opponentPlayerNick, event);
	}

	public boolean checkIfBattleshipGameHasBeenCompleted(String opponentPlayerNick) {
		return battleshipManagement.checkIfBattleshipGameHasBeenCompleted(opponentPlayerNick);
	}

	public Object sendResignationFromTheGame(String opponentPlayerNick) {
		return battleshipManagement.sendResignationFromTheGame(opponentPlayerNick);
	}

	public void closeBattleshipMainScreen(String opponentPlayerNick) {
		battleshipManagement.closeBattleshipMainScreen(opponentPlayerNick);
	}
}
