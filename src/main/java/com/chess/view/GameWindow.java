package com.chess.view;

import com.chess.controller.GameController;
import javafx.scene.layout.BorderPane;

public class GameWindow extends BorderPane {
    
    private final BoardView boardView;

    public GameWindow(GameController controller) {
        this.boardView = new BoardView(controller);
        setCenter(boardView);
    }
}
