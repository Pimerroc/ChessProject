package com.chess.view;

import com.chess.controller.GameController;
import com.chess.model.board.Board;
import com.chess.model.board.Square;
import com.chess.model.move.Move;
import com.chess.model.pieces.Piece;
import com.chess.util.ResourceManager;
import java.util.List;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

public class BoardView extends GridPane {

  private final GameController controller;
  private final CellView[][] cells = new CellView[8][8];
  private Square selectedSquare = null;
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
      highlightLegalMoves(clickedSquare);
      cells[file][rank].highlightBackground();
    }

    if (selectedSquare != null) {
      controller.performMove(selectedSquare, clickedSquare);
      refresh();
    }

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
