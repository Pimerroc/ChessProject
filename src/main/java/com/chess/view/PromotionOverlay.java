/*
  private Piece showPromotionDialog(PieceColor pieceColor) {
    Dialog<Piece> dialog = new Dialog<>();
    dialog.setTitle("Promote");
    dialog.setHeaderText(null);

    dialog.initStyle(StageStyle.UTILITY);
    dialog.initModality(Modality.APPLICATION_MODAL);

    HBox box = new HBox(10);
    box.setStyle("-fx-padding: 20; -fx-alignment: center;");

    List<Piece> pieces = new ArrayList<>();
    pieces.add(new Knight(pieceColor));
    pieces.add(new Bishop(pieceColor));
    pieces.add(new Rook(pieceColor));
    pieces.add(new Queen(pieceColor));

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
            dialog.setResult(p);
            dialog.close();
          });

      box.getChildren().add(btn);
    }

    dialog.getDialogPane().setContent(box);

    dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
    dialog.getDialogPane().lookupButton(ButtonType.CANCEL).setVisible(false);

    Optional<Piece> result = dialog.showAndWait();
    return result.orElse(new Queen(pieceColor));
  }
  */
