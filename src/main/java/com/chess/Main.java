package com.chess;

import com.chess.util.UIUtil;
import com.chess.view.SettingsOverlay;
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

  private final StackPane mainMenu = new StackPane();
  private final SettingsOverlay settingsOverlay = new SettingsOverlay();

  @Override
  public void start(Stage stage) {
    createMenuScene();
    stage.setScene(new Scene(mainMenu, WIDTH, HEIGHT));
    stage.setFullScreen(true);
    stage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
    stage.setFullScreenExitHint("");
    stage.setTitle("Chess Game");
    stage.setResizable(true);
    stage.show();
  }

  private void createMenuScene() {
    VBox layout = new VBox(20);
    layout.setAlignment(Pos.CENTER);
    layout.setStyle("-fx-background-color: #997950;");

    Label titleLabel = new Label("CHESS");
    titleLabel.setTextFill(Color.web("#e0e0e0"));
    titleLabel.setFont(Font.font("Segoe UI", FontWeight.BOLD, 50));
    titleLabel.setEffect(new DropShadow(10, Color.BLACK));

    Button startButton = UIUtil.createStyledButtonForMainMenu("New Game");
    // TODO: if (save is not empty){ Button continueButton = createStyledButton("Continue"); }
    Button settingsButton = UIUtil.createStyledButtonForMainMenu("Settings");
    Button exitButton = UIUtil.createStyledButtonForMainMenu("Exit");

    settingsButton.setOnAction(
        e -> {
          settingsOverlay.showSettingsOverlay();
        });

    exitButton.setOnAction(
        e -> {
          Platform.exit();
        });

    layout
        .getChildren()
        .addAll(titleLabel, createSpacer(20), startButton, settingsButton, exitButton);
    mainMenu.getChildren().addAll(layout, settingsOverlay);
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
