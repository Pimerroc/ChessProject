package com.chess.view;

import com.chess.controller.GameController;
import com.chess.model.GameState;
import com.chess.model.board.Board;
import com.chess.model.board.Square;
import com.chess.model.pieces.PieceColor;
import java.nio.file.Files;
import java.nio.file.Path;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;

public class GameWindow extends StackPane {

  @FunctionalInterface
  public interface LeaveBackToMenuButton {
    void leaveBackToMenu();
  }

  private LeaveBackToMenuButton exitButtonListener;
  private final GameOverOverlay gameOverOverlay;
  private final PromotionOverlay promotionOverlay;
  private final BoardView boardView;
  private final GameController controller;
  private final SettingsOverlay settingsOverlay;

  private Square promotionMoveFrom;
  private Square promotinMoveTo;

  public void setButtonListener(LeaveBackToMenuButton listener) {
    this.exitButtonListener = listener;
  }

  public void loadGame(String fen){
    controller.loadGameFromFEN(fen);
  }

  public GameWindow(GameController controller, SettingsOverlay settingsOverlay) {
    this.controller = controller;
    this.settingsOverlay = settingsOverlay;
    this.boardView = new BoardView(controller);
    this.promotionOverlay = new PromotionOverlay();

    this.gameOverOverlay =
        new GameOverOverlay(
            e -> {
              restartGame();
            },
            e -> {
              deleteSave();
              exitButtonListener.leaveBackToMenu();
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
          if (settingsOverlay.isAutoFlipEnabled()) {
            boardView.flipBoard();
          }
        });
    boardView.setAlignment(Pos.CENTER);
    this.setStyle("-fx-background-color: #997950");
    BorderPane gameLayer = new BorderPane();

    HBox boardContainer = new HBox(boardView);

    boardContainer.setAlignment(Pos.CENTER);
    gameLayer.setCenter(boardContainer);
    gameLayer.setTop(createOptionPanel());

    this.getChildren().setAll(gameLayer, promotionOverlay, gameOverOverlay, settingsOverlay);
  }

  private HBox createOptionPanel() {
    HBox bottomBar = new HBox(10);
    bottomBar.setStyle("-fx-padding: 15; -fx-background-color:  #754b06 ; -fx-alignment: center;");

    Button settingsBtn = createStyledButton("Settings");
    Button flipBtn = createStyledButton("Flip Board");
    Button exitBtn = createStyledButton("Exit to Menu");
    flipBtn.setOnAction(
        e -> {
          boardView.flipBoard();
        });
    settingsBtn.setOnAction(
        e -> {
          settingsOverlay.showSettingsOverlay();
        });
    exitBtn.setOnAction(
        e -> {
          saveFENToFile();
          exitButtonListener.leaveBackToMenu();
        });
    bottomBar.getChildren().addAll(settingsBtn, flipBtn, exitBtn);
    return bottomBar;
  }

  private Button createStyledButton(String text) {
    Button btn = new Button(text);
    btn.setPrefWidth(250);
    btn.setPrefHeight(40);
    String normalStyle =
        "-fx-background-color: #cccccc; -fx-text-fill: black; -fx-font-size: 16px; -fx-cursor:"
            + " hand;";
    String hoverStyle =
        "-fx-background-color: #ffa617; -fx-text-fill: black; -fx-font-size: 16px; -fx-cursor:"
            + " hand;";
    btn.setStyle(normalStyle);
    btn.setOnMouseEntered(e -> btn.setStyle(hoverStyle));
    btn.setOnMouseExited(e -> btn.setStyle(normalStyle));
    return btn;
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
        deleteSave();
        break;
      case STALEMATE:
        gameOverOverlay.showGameOver("DRAW", "Stealmate");
        deleteSave();
        break;
      default:
        break;
    }
  }

  private void saveFENToFile() {
    try {
      Files.writeString(Path.of("/home/max/IdeaProjects/ChessProject/src/main/java/com/chess/chess_safe.fen"), controller.getGameFEN());
    } catch (Exception e) {
    }
  }

  private void deleteSave() {
    try {
      Files.deleteIfExists(Path.of("/home/max/IdeaProjects/ChessProject/src/main/java/com/chess/chess_safe.fen"));
    } catch (Exception e) {

    }
  }

  private void restartGame() {
    controller.newGame();
    boardView.refresh();
    this.gameOverOverlay.setVisible(false);
  }
}
