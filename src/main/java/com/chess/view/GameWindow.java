package com.chess.view;

import com.chess.controller.GameController;
import com.chess.model.GameState;
import com.chess.model.board.Board;
import com.chess.model.board.Square;
import com.chess.model.pieces.PieceColor;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;

public class GameWindow extends StackPane {

  private final GameOverOverlay gameOverOverlay;
  private final PromotionOverlay promotionOverlay;
  private final BoardView boardView;
  private final GameController controller;

  private Square promotionMoveFrom;
  private Square promotinMoveTo;

  public GameWindow(GameController controller) {
    this.controller = controller;
    this.boardView = new BoardView(controller);
    this.promotionOverlay = new PromotionOverlay();

    this.gameOverOverlay =
        new GameOverOverlay(
            e -> {
              restartGame();
            },
            e -> { // to main Menu
            });
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
    boardView.setOnMoveCompletedListener(
        () -> {
          checkGameStatus();
        });
    boardView.setAlignment(Pos.CENTER);
    this.setStyle("-fx-background-color: #754b06");
    BorderPane gameLayer = new BorderPane();

    HBox boardContainer = new HBox(boardView);

    boardContainer.setAlignment(Pos.CENTER);
    gameLayer.setCenter(boardContainer);
    gameLayer.setTop(createOptionPanel());

    this.getChildren().addAll(gameLayer, promotionOverlay, gameOverOverlay);
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

  private void checkGameStatus() {
    Board board = controller.getBoard();
    GameState state = board.checkGameState();

    switch (state) {
      case CHECKMATE:
        if (board.getMovingColor() == PieceColor.WHITE) {
          gameOverOverlay.showGameOver("CHECKMATE", "Black wins!");
        } else {
          gameOverOverlay.showGameOver("CHECKMATE", "White wins!");
        }
        break;
      case STALEMATE:
        gameOverOverlay.showGameOver("DRAW", "Stealmate");
        break;
      default:
        break;
    }
  }

  private void restartGame() {
    controller.newGame();
    boardView.refresh();
    this.gameOverOverlay.setVisible(false);
  }
}
