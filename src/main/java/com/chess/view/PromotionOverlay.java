package com.chess.view;

import java.util.function.Consumer;

import com.chess.model.pieces.Piece;
import com.chess.model.pieces.PieceColor;
import com.chess.model.pieces.impl.Bishop;
import com.chess.model.pieces.impl.Knight;
import com.chess.model.pieces.impl.Queen;
import com.chess.model.pieces.impl.Rook;
import com.chess.util.ResourceManager;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

public class PromotionOverlay extends HBox {
  
  private final ResourceManager resourceManager = new ResourceManager();

  public PromotionOverlay(){
    this.setAlignment(Pos.CENTER);
    this.setSpacing(20);
    this.setStyle("-fx-background-color: rgba(0, 0, 0, 0.7);");
    this.setVisible(false);

    this.setOnMouseClicked(e -> e.consume());
  }

  void showPromotionOverlay(PieceColor color, Consumer<Piece> onPieceSelected) {
    Piece[] pieces = {new Queen(color), new Rook(color), new Bishop(color), new Knight(color)};

    for (Piece p : pieces) {
      ImageView view = new ImageView(resourceManager.getImage(p));
      view.setFitWidth(60);
      view.setFitHeight(60);

      Button btn = new Button();
      btn.setGraphic(view);

      btn.setStyle("-fx-background-color: transparent; -fx-cursor: hand;");

      btn.setOnMouseEntered(e -> btn.setStyle("-fx-background-color: #cccccc; -fx-cursor: hand;"));
      btn.setOnMouseExited(e -> btn.setStyle("-fx-background-color: transparent;"));

      btn.setOnAction(
          e -> {
            this.setVisible(false);
            onPieceSelected.accept(p);
          });

      this.getChildren().add(btn);
    }
    this.setVisible(true);
  }
}
