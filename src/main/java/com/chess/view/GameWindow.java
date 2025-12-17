package com.chess.view;

import com.chess.controller.GameController;
import com.chess.model.board.Square;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;

public class GameWindow extends StackPane {

  private final PromotionOverlay promotionOverlay;
  private final BoardView boardView;

  private Square promotionMoveFrom;
  private Square promotinMoveTo;

  public GameWindow(GameController controller) {
    this.boardView = new BoardView(controller);
    this.promotionOverlay = new PromotionOverlay();

    boardView.setPromotionListener(
        (from, to, color) -> {
          this.promotionMoveFrom = from;
          this.promotinMoveTo = to;
          promotionOverlay.showPromotionOverlay(
              color,
              (chosenPiece) -> {
                controller.performPromotionMove(promotionMoveFrom, promotinMoveTo, chosenPiece);
                boardView.refresh();
              });
        });

    boardView.setAlignment(Pos.CENTER);

    BorderPane gameLayer = new BorderPane();

    HBox boardContainer = new HBox(boardView);

    boardContainer.setAlignment(Pos.CENTER);
    gameLayer.setCenter(boardContainer);
    gameLayer.setTop(createOptionPanel());

    this.getChildren().addAll(gameLayer, promotionOverlay);
  }

  private HBox createOptionPanel() {
    HBox bottomBar = new HBox(10);
    bottomBar.setStyle("-fx-padding: 15; -fx-background-color:  #754b06 ; -fx-alignment: left;");

    Button saveBtn = new Button("Save Game");
    Button flipBtn = new Button("Flip Board");
    Button exitBtn = new Button("Exit to Menu");
    flipBtn.setOnAction(
        e -> {
          boardView.flipBoard();
        });
    bottomBar.getChildren().addAll(saveBtn, flipBtn, exitBtn);
    return bottomBar;
  }
}
