package com.chess;

import com.chess.controller.GameController;
import com.chess.view.GameWindow;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

  @Override
  public void start(Stage stage) {
    GameController gameController = new GameController();

    GameWindow gameWindow = new GameWindow(gameController);

    Scene scene = new Scene(gameWindow, 640, 640);

    stage.setScene(scene);
    stage.setTitle("Chess Game Beta");
    stage.setResizable(false);
    stage.show();
  }

  public static void main(String[] args) {
    launch();
  }
}
