package com.chess.util;

import javafx.scene.control.Button;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class UIUtil {

  public static Button createStyledButtonForMainMenu(String text) {
    Button btn = defaultButton(text);

    String normalStyle =
        "-fx-background-color: #664933; -fx-text-fill: white; -fx-background-radius: 10;"
            + " -fx-cursor: hand;";
    String hoverStyle =
        "-fx-background-color: #855e42; -fx-text-fill: white; -fx-background-radius: 10;"
            + " -fx-cursor: hand;";

    btn.setStyle(normalStyle);

    btn.setOnMouseEntered(e -> btn.setStyle(hoverStyle));
    btn.setOnMouseExited(e -> btn.setStyle(normalStyle));

    return btn;
  }

  public static Button defaultButton(String text) {
    Button btn = new Button(text);
    btn.setPrefWidth(300);
    btn.setPrefHeight(40);
    btn.setFont(Font.font("Segoe UI", FontWeight.BOLD, 16));
    return btn;
  }


}
