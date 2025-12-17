package com.chess.view;

import com.chess.controller.GameController;

import javafx.geometry.Pos;
import javafx.scene.layout.StackPane;

public class GameWindow extends StackPane {
    
    private final BoardView boardView;

    public GameWindow(GameController controller) {
        this.boardView = new BoardView(controller);
        boardView.setAlignment(Pos.CENTER);
        this.getChildren().addAll(boardView);
    }
}
