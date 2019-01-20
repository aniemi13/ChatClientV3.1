package com.niemiec.battleship.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class WaitingWindowController {
	@FXML
	private Label label;

	@FXML
	void initialize() {
	}

	public void setTextLabel(String string) {
		label.setText(string);
	}
}
