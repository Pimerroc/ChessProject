package com.chess;

import com.chess.controller.GameController;
import com.chess.util.UIUtil;
import com.chess.view.GameWindow;
import com.chess.view.SettingsOverlay;
import java.nio.file.Files;
import java.nio.file.Path;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class Main extends Application {
  private static final int WIDTH = 800;
  private static final int HEIGHT = 800;

  private final StackPane contentLayer = new StackPane();
  private final SettingsOverlay settingsOverlay = new SettingsOverlay();

  @Override
  public void start(Stage stage) {
    createMenuScene(stage);
    stage.setScene(new Scene(contentLayer, WIDTH, HEIGHT));
    stage.setFullScreen(true);
    stage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
    stage.setFullScreenExitHint("");
    stage.setResizable(true);
    stage.show();
  }

  void createMenuScene(Stage stage) {
    stage.setTitle("Chess Game");

    VBox layout = new VBox(20);
    layout.setAlignment(Pos.CENTER);
    layout.setStyle("-fx-background-color: #997950;");

    Label titleLabel = new Label("CHESS");
    titleLabel.setTextFill(Color.web("#e0e0e0"));
    titleLabel.setFont(Font.font("Segoe UI", FontWeight.BOLD, 50));
    titleLabel.setEffect(new DropShadow(10, Color.BLACK));

    Button continueButton = UIUtil.createStyledButtonForMainMenu("Continue");
    Button startButton = UIUtil.createStyledButtonForMainMenu("New Game");
    Button settingsButton = UIUtil.createStyledButtonForMainMenu("Settings");
    Button exitButton = UIUtil.createStyledButtonForMainMenu("Exit");
    Path savePath =
        Path.of("/home/max/IdeaProjects/ChessProject/src/main/java/com/chess/chess_safe.fen");
    boolean hasSave = Files.exists(savePath);

    startButton.setOnAction(
        e -> {
          runGameWithData(stage, null);
        });
    settingsButton.setOnAction(
        e -> {
          settingsOverlay.showSettingsOverlay();
        });

    exitButton.setOnAction(
        e -> {
          Platform.exit();
        });

    if (hasSave) {
      continueButton.setDisable(false);
      continueButton.setOnAction(
          e -> {
            try {
              String fen = Files.readString(savePath);
              runGameWithData(stage, fen);
            } catch (Exception ex) {
              ex.printStackTrace();
            }
          });

      layout
          .getChildren()
          .addAll(
              titleLabel,
              createSpacer(20),
              continueButton,
              startButton,
              settingsButton,
              exitButton);
    } else {
      layout
          .getChildren()
          .addAll(titleLabel, createSpacer(20), startButton, settingsButton, exitButton);
    }
    contentLayer.getChildren().setAll(layout, settingsOverlay);
  }

  private void runGameWithData(Stage stage, String fen) {
    GameController gameController = new GameController();
    if (fen != null) {
      gameController.loadGameFromFEN(fen);
    }
    GameWindow gameWindow = new GameWindow(gameController, settingsOverlay);
    boolean isFullScreen = stage.isFullScreen();
    gameWindow.setButtonListener(
        () -> {
          createMenuScene(stage);
        });
    stage.setTitle("Playing");
    contentLayer.getChildren().setAll(gameWindow);
  }

  private Region createSpacer(double height) {
    Region spacer = new Region();
    spacer.setPrefHeight(height);
    return spacer;
  }

  public static void main(String[] args) {
    launch();
  }
}
