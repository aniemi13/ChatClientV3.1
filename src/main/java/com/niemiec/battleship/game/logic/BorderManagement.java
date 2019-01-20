package com.niemiec.battleship.game.logic;

import com.niemiec.battleship.game.objects.Board;
import com.niemiec.battleship.game.objects.Coordinates;
import com.niemiec.battleship.game.objects.Player;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class BorderManagement {
	
	private  VBox myBorder;
	private  VBox opponentBorder;

//	public BorderManagement(VBox myBorder, VBox opponentBorder) {
//		this.myBorder = myBorder;
//		this.opponentBorder = opponentBorder;
//	}

	public void setBorders(VBox myBorder, VBox opponentBorder) {
		this.myBorder = myBorder;
		this.opponentBorder = opponentBorder;
	}

	public void startNewGameWithVirtualPlayer() {
		cleanBorders();
		myBorder.setDisable(false);
		opponentBorder.setDisable(true);
	}

	private void cleanBorders() {
		drawInBorderButton(myBorder.getChildren(), null);
		drawInBorderButton(opponentBorder.getChildren(), null);
	}
	
	public void drawOpponentBoardInMyBorder(Player player) {
		drawInBorderButton(myBorder.getChildren(), player.getOpponentBoard());
	}
	
	public void drawOpponentBoardInOpponentBorder(Player player) {
		drawInBorderButton(opponentBorder.getChildren(), player.getOpponentBoard());
	}
	
	public void drawBoardInMyBorder(Player player) {
		drawInBorderButton(myBorder.getChildren(), player.getBoard());
	}
	
	public void drawBoardInOpponentBorder(Player player) {
		drawInBorderButton(opponentBorder.getChildren(), player.getBoard());
	}
	
	private void drawInBorderButton(ObservableList<Node> obervableList, Board board) {
		for (int y = 0; y < 10; y++) {	
			HBox hbox = (HBox) obervableList.get(y);
			drawInBorderXAxis(y, hbox, board);		
		}
	}

	private void drawInBorderXAxis(int y, HBox hbox, Board board) {
		for (int x = 0; x < 10; x++) {
			Coordinates coordinates = new Coordinates(x + 1, y + 1);
			Button button = (Button) hbox.getChildren().get(x);
			drawInButton(button, board, coordinates);		
		}
	}

	private void drawInButton(Button button, Board board, Coordinates coordinates) {
		if (board == null) {
			button.setText("");
		} else {
			int box = board.getBox(coordinates);
			button.setText((box != 0) ? Integer.toString(box) : "");
		}
	}

	public void setBordersToStartShot() {
		myBorder.setDisable(true);
		opponentBorder.setDisable(false);
	}

	public void realPlayerAddedShipsAutomatically() {
		myBorder.setDisable(true);
	}

	public void setBordersToEndGame() {
		myBorder.setDisable(true);
		opponentBorder.setDisable(true);
	}
	
}
