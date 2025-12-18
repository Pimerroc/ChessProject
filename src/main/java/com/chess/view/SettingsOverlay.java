package com.chess.view;

import com.chess.util.UIUtil;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import org.controlsfx.control.ToggleSwitch;

public class SettingsOverlay extends VBox {

  private ToggleSwitch autoFlipSwitch;
  private ToggleSwitch fullScreenSwitch;

  public SettingsOverlay() {

    this.setAlignment(Pos.CENTER);
    this.setSpacing(25);
    this.setStyle("-fx-background-color: rgba(0, 0, 0, 0.9);");
    this.setVisible(false);

    Label title = new Label("SETTINGS");
    title.setTextFill(Color.web("#e0e0e0"));
    title.setFont(Font.font("Segoe UI", FontWeight.BOLD, 40));

    Button closeBtn = UIUtil.createStyledButtonForMainMenu("Close");
    closeBtn.setAlignment(Pos.CENTER);
    closeBtn.setOnAction(e -> this.setVisible(false));

    fullScreenSwitch = new ToggleSwitch("Full screen");
    fullScreenSwitch.setFont(Font.font("Segoe UI", FontWeight.BOLD, 16));
    fullScreenSwitch.setStyle("-fx-text-fill: white; -fx-font-size: 16px;");
    fullScreenSwitch
        .selectedProperty()
        .addListener(
            (observer, oldV, newValue) -> {
              if (this.getScene() != null && this.getScene().getWindow() != null){
                Stage stage = (Stage) this.getScene().getWindow();
                stage.setFullScreen(newValue);
              }
            });

    autoFlipSwitch = new ToggleSwitch("Flip board on move");
    autoFlipSwitch.setFont(Font.font("Segoe UI", FontWeight.BOLD, 16));
    this.getChildren().addAll(title, fullScreenSwitch, autoFlipSwitch, closeBtn);
  }

  public void showSettingsOverlay() {
    if (this.getScene() != null) {
      Stage stage = (Stage) this.getScene().getWindow();

      fullScreenSwitch.setSelected(stage.isFullScreen());
    }
    this.setVisible(true);
  }

  public boolean isAutoFlipEnabled() {
    return autoFlipSwitch.isSelected();
  }
}
