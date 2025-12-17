package com.chess.view;

import com.chess.controller.GameController;
import com.chess.model.board.Square;
import com.chess.model.pieces.Piece;
import com.chess.model.pieces.PieceColor;
import com.chess.model.pieces.impl.Bishop;
import com.chess.model.pieces.impl.Knight;
import com.chess.model.pieces.impl.Queen;
import com.chess.model.pieces.impl.Rook;
import com.chess.util.ResourceManager;

import javafx.geometry.Pos;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.control.Button;
public class GameWindow extends StackPane {

  private final GameController controller;
  private final BoardView boardView;
  private final HBox promotionOverlay;
  private final ResourceManager resourceManager = new ResourceManager();

  private Square from;
  private Square to;
  public GameWindow(GameController controller) {
    this.controller = controller;
    this.boardView = new BoardView(controller);

    promotionOverlay = createPromotionOverlay();
    
    boardView.setPromotionListener(
      (from, to, color) -> {
        this.from = from;
        this.to = to;
        showPromotionOverlay(color);
      }
          
    );      
    
    boardView.setAlignment(Pos.CENTER);
    this.getChildren().addAll(boardView, promotionOverlay);
  }

  private HBox createPromotionOverlay() {
    HBox overlay = new HBox(20);

    overlay.setAlignment(Pos.CENTER);
    overlay.setStyle("-fx-background-color: rgba(0, 0, 0, 0.7);");
    overlay.setVisible(false);

    return overlay;
  }

  private void showPromotionOverlay(PieceColor color) {
    Piece[] pieces = {new Queen(color), new Rook(color), new Bishop(color), new Knight(color)};

    for (Piece p : pieces) {
      ImageView view = new ImageView(resourceManager.getImage(p));
      view.setFitWidth(60);
      view.setFitHeight(60);

      Button btn = new Button();
      btn.setGraphic(view);

      btn.setStyle("-fx-background-color: rgba(0, 0, 0, 0.7); -fx-cursor: hand;");

      btn.setOnMouseEntered(e -> btn.setStyle("-fx-background-color: #cccccc; -fx-cursor: hand;"));
      btn.setOnMouseExited(e -> btn.setStyle("-fx-background-color: transparent;"));

      btn.setOnAction(
          e -> {
            controller.performPromotionMove(from, to, p);

            promotionOverlay.setVisible(false);

            boardView.refresh();
          });

      promotionOverlay.getChildren().add(btn);
    }
    promotionOverlay.setVisible(true);
  }
}
