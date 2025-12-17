package com.chess.view;

import com.chess.model.pieces.Piece;
import com.chess.util.ResourceManager;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class CellView extends StackPane {
  // its better to just do this rn, im not in the mood to do color theme's
  private static final Color TEXT_COLOR_LIGHT = Color.BEIGE;
  private static final Color TEXT_COLOR_DARK = Color.SADDLEBROWN;
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

  public void addCoordinateOverlay(String text, boolean isTopLeft, boolean isLightSquared) {
    Label label = new Label(text);

    label.setFont(Font.font("Segoe UI", FontWeight.BOLD, 16));

    Color textColor = isLightSquared ? TEXT_COLOR_DARK : TEXT_COLOR_LIGHT;
    label.setTextFill(textColor);

    label.setPadding(new Insets(2, 4, 2, 4));

    if (isTopLeft) {
      StackPane.setAlignment(label, Pos.TOP_LEFT);
    } else {
      StackPane.setAlignment(label, Pos.BOTTOM_RIGHT);
    }

    this.getChildren().add(label);
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
