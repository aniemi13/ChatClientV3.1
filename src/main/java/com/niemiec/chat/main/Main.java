package com.niemiec.chat.main;

import com.niemiec.chat.controllers.GetNickController;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {
	
	public static void main(String args[]) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/fxml/GetNickWindow.fxml"));
	
		VBox vbox = loader.load();
		Scene scene = new Scene(vbox);
		
		primaryStage.setScene(scene);
		primaryStage.setTitle("Czat");
		primaryStage.sizeToScene();
//		primaryStage.setOnCloseRequest(e -> closeProgram());
		primaryStage.show();
		
		GetNickController controller = loader.getController();
		controller.initData(primaryStage);
	}
}
