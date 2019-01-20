package com.niemiec.battleship.controllers;

import com.niemiec.battleship.logic.BattleshipManagement;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class InformationAndAcceptanceController {

    @FXML
    private Label label;

    @FXML
    private Button okButton;
    
    private BattleshipManagement battleshipManagement;
    private String opponentPlayerNick;

    @FXML
    public void understand() {
    	battleshipManagement.acceptRejectionGameProspalInformation(opponentPlayerNick);
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
