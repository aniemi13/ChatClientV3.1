package com.niemiec.battleship.controllers;

//import com.niemiec.logic.Exit;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.stage.Stage;

public class ExitWindowController {

	@FXML
	private static Stage stage;

//	private static Exit exitClass;

	public static void setStage(Stage stage) {
		ExitWindowController.stage = stage;
	}

//	public static void setExitClass(Exit exit2) {
//		ExitWindowController.exitClass = exit2;
//	}

	@FXML
	void exit(ActionEvent event) {
//		exitClass.dontSave();
		System.exit(0);
	}

	@FXML
	void noExit(ActionEvent event) {
		ExitWindowController.stage.close();
	}

	@FXML
	void saveAndExit(ActionEvent event) {
//		exitClass.saveGame();
		System.exit(0);
	}

}
