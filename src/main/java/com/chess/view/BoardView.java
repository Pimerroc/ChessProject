package com.chess.view;

import com.chess.controller.GameController;
import com.chess.model.board.Board;
import com.chess.model.board.Square;
import com.chess.model.move.Move;
import com.chess.model.pieces.Piece;
import com.chess.model.pieces.PieceColor;
import com.chess.model.pieces.impl.Bishop;
import com.chess.model.pieces.impl.Knight;
import com.chess.model.pieces.impl.Pawn;
import com.chess.model.pieces.impl.Queen;
import com.chess.model.pieces.impl.Rook;
import com.chess.util.ResourceManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.StageStyle;

public class BoardView extends GridPane {

  private final GameController controller;
  private final CellView[][] cells = new CellView[8][8];
  private Square selectedSquare = null;
  private Piece selectedPiece = null;
  private final ResourceManager resourceManager = new ResourceManager();

  public BoardView(GameController controller) {
    this.controller = controller;
    createBoard();
    refresh();
  }

  private void createBoard() {
    for (int rank = 7; rank >= 0; rank--) {
      for (int file = 0; file < 8; file++) {

        boolean isLight = (file + rank) % 2 != 0;
        Color color = isLight ? Color.BEIGE : Color.SADDLEBROWN;

        CellView cell = new CellView(color);

        int f = file;
        int r = rank;
        cell.setOnMouseClicked(e -> handleSquareClick(f, r));

        cells[file][rank] = cell;

        add(cell, file, 7 - rank);
      }
    }
  }

  private void handleSquareClick(int file, int rank) {
    Square clickedSquare = new Square(file, rank);
    Board board = controller.getBoard();
    Piece clickedPiece = board.getPiece(clickedSquare);

    clearHighlights();

    if (clickedPiece != null && clickedPiece.getColor() == board.getMovingColor()) {
      selectedSquare = clickedSquare;
      selectedPiece = board.getPiece(selectedSquare); 
      highlightLegalMoves(clickedSquare);
      cells[file][rank].highlightBackground();
      return;
    }

    if (selectedSquare != null) {
      if ((selectedPiece instanceof Pawn)
          && (clickedSquare.rank() == 0 || clickedSquare.rank() == 7)) {
        if (controller.validateMove(selectedSquare, clickedSquare)) {
          controller.performPromotionMove(
              selectedSquare, clickedSquare, showPromotionDialog(selectedPiece.getColor()));
        }
      } else {
        controller.performMove(selectedSquare, clickedSquare);
      }
      selectedSquare = null;
      refresh();
    }
  }

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

  public void refresh() {
    Board board = controller.getBoard();
    for (int i = 0; i < 8; i++) {
      for (int j = 0; j < 8; j++) {
        Piece piece = board.getPiece(new Square(i, j));
        cells[i][j].setPiece(piece, resourceManager);
      }
    }
  }

  private void highlightLegalMoves(Square start) {
    List<Move> moves = controller.getBoard().getLegalMovesForPiece(start);
    for (Move move : moves) {
      Square end = move.getEnd();
      cells[end.file()][end.rank()].showMoveMarker();
    }
  }

  private void clearHighlights() {
    for (int i = 0; i < 8; i++) {
      for (int j = 0; j < 8; j++) {
        cells[i][j].resetHighlight();
      }
    }
  }
}
