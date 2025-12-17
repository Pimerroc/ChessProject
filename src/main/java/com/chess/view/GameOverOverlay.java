package com.chess.view;


import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class GameOverOverlay extends VBox {

    private final Label titleLabel;
    private final Label subTitleLabel;
    private final Button restartBtn;
    private final Button exitBtn;

    public GameOverOverlay(EventHandler<ActionEvent> onRestart, EventHandler<ActionEvent> onExit) {
      
        this.setAlignment(Pos.CENTER);
        this.setSpacing(20);
        this.setStyle("-fx-background-color: rgba(0, 0, 0, 0.85);");
        this.setVisible(false); 
        this.setOnMouseClicked(e -> e.consume());

        titleLabel = new Label("GAME OVER");
        titleLabel.setTextFill(Color.RED);
        titleLabel.setFont(Font.font("Segoe UI", FontWeight.BOLD, 50));

        subTitleLabel = new Label();
        subTitleLabel.setTextFill(Color.WHITE);
        subTitleLabel.setFont(Font.font("Segoe UI", FontWeight.NORMAL, 24));

        restartBtn = createStyledButton("New Game");
        restartBtn.setOnAction(onRestart);

        exitBtn = createStyledButton("Exit to Menu");
        exitBtn.setOnAction(onExit);

        this.getChildren().addAll(titleLabel, subTitleLabel, restartBtn, exitBtn);
    }

    public void showGameOver(String title, String message) {
        titleLabel.setText(title);
        subTitleLabel.setText(message);
        this.setVisible(true);
    }

    private Button createStyledButton(String text) {
        Button btn = new Button(text);
        btn.setPrefWidth(200);
        btn.setPrefHeight(40);
        btn.setStyle("-fx-background-color: #d4a017; -fx-text-fill: black; -fx-font-size: 16px; -fx-cursor: hand;");
        return btn;
    }
}
