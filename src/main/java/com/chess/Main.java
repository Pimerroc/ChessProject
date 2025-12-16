package com.chess;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class Main extends Application {
  private static final int WIDTH = 1920;
  private static final int HEIGHT = 1024;

  @Override
  public void start(Stage stage) {
    Scene scene = createMenuScene(stage);

    stage.setScene(scene);
    stage.setTitle("Chess Game Beta");
    stage.setResizable(false);
    stage.show();
  }

  private Scene createMenuScene(Stage stage) {
    VBox layout = new VBox(10); 
    layout.setAlignment(Pos.CENTER);

    layout.setStyle("-fx-background-color: #997950;"); 

    Label titleLabel = new Label("CHESS");
    titleLabel.setTextFill(Color.web("#e0e0e0"));
    titleLabel.setFont(Font.font("Segoe UI", FontWeight.BOLD, 50));
    titleLabel.setEffect(new DropShadow(10, Color.BLACK));

    Button startButton = createStyledButton("New Game");
    // TODO: if (save is not empty){ Button continueButton = createStyledButton("Continue"); } 
    Button settingsButton = createStyledButton("Settings");
    Button exitButton = createStyledButton("Exit");


    exitButton.setOnAction(
        e -> {
          Platform.exit();
        });

    layout
        .getChildren()
        .addAll(
            titleLabel,
            createSpacer(20),
            startButton,
            settingsButton,
            exitButton);

    return new Scene(layout, WIDTH, HEIGHT);
  }

  private Button createStyledButton(String text) {
    Button btn = defaultButton(text);

    String normalStyle =
        "-fx-background-color: #664933; "
            + "-fx-text-fill: white; "
            + "-fx-background-radius: 10; "
            + "-fx-cursor: hand;";

    String hoverStyle =
        "-fx-background-color: #855e42; "
            + "-fx-text-fill: white; "
            + "-fx-background-radius: 10; "
            + "-fx-cursor: hand;";

    btn.setStyle(normalStyle);

    btn.setOnMouseEntered(e -> btn.setStyle(hoverStyle));
    btn.setOnMouseExited(e -> btn.setStyle(normalStyle));

    return btn;
  }

  private Button defaultButton(String text){
    Button btn = new Button(text);
    btn.setPrefWidth(300);
    btn.setPrefHeight(40);
    btn.setFont(Font.font("Segoe UI", FontWeight.BOLD, 16));
    return btn;
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
