package com.niemiec.battleship.controllers;

import com.niemiec.battleship.logic.BattleshipManagement;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class EndGameInformationAndAcceptanceController {

    @FXML
    private Label label;

    @FXML
    private Button okButton;
    
    private BattleshipManagement battleshipManagement;
    private String opponentPlayerNick;

    @FXML
    void understand(ActionEvent event) {
    	battleshipManagement.acceptEndGame(opponentPlayerNick);
    }

	public void setOpponentPlayerNick(String opponentPlayerNick) {
		this.opponentPlayerNick = opponentPlayerNick;
	}

	public void setBattleshipManagement(BattleshipManagement battleshipManagement) {
		this.battleshipManagement = battleshipManagement;
	}

	public void setTextLabel(String message) {
		label.setText(message);
	}
}
