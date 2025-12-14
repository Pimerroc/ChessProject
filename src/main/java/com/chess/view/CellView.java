package com.chess.view;

import com.chess.model.pieces.Piece;
import com.chess.util.ResourceManager;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

public class CellView extends StackPane {

  private final Rectangle background;
  private final ImageView imageView;
  private final Circle moveMarker;
  private final Color defaultColor;

  public CellView(Color color) {
    this.defaultColor = color;

    this.background = new Rectangle(80, 80);
    this.background.setFill(color);

    this.imageView = new ImageView();
    this.imageView.setFitWidth(60);
    this.imageView.setFitHeight(60);

    this.moveMarker = new Circle(11);
    this.moveMarker.setFill(Color.rgb(0, 128, 0, 0.3));
    this.moveMarker.setVisible(false);
    this.moveMarker.setMouseTransparent(true);

    getChildren().addAll(background, imageView, moveMarker);
  }

  public void setPiece(Piece piece, ResourceManager resources) {
    if (piece == null) {
      imageView.setImage(null);
    } else {
      imageView.setImage(resources.getImage(piece));
    }
  }

  public void showMoveMarker() {
    moveMarker.setVisible(true);
  }

  public void highlightBackground() {
    background.setFill(Color.YELLOW.darker());
  }

  public void resetHighlight() {
    background.setFill(defaultColor);
    moveMarker.setVisible(false);
  }
}
