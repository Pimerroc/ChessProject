package com.chess.view;

import com.chess.controller.GameController;
import com.chess.model.board.Board;
import com.chess.model.board.Square;
import com.chess.model.move.Move;
import com.chess.model.pieces.Piece;
import com.chess.model.pieces.PieceColor;
import com.chess.model.pieces.impl.Pawn;
import com.chess.util.ResourceManager;
import java.util.List;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

public class BoardView extends GridPane {

  @FunctionalInterface
  public interface OnPromotionListener {
    void onPromotionRequired(Square from, Square to, PieceColor color);
  }

  @FunctionalInterface
  public interface OnMoveCompletedListener {
    void checkGameState();
  }

  private OnMoveCompletedListener onMoveCompletedListener;
  private OnPromotionListener promotionListener;
  private final GameController controller;
  private final CellView[][] cells = new CellView[8][8];
  private Square selectedSquare = null;
  private Piece selectedPiece = null;
  private final ResourceManager resourceManager = new ResourceManager();
  private PieceColor currentPerspective = PieceColor.WHITE;

  public void setOnMoveCompletedListener(OnMoveCompletedListener listener) {
    this.onMoveCompletedListener = listener;
  }

  public void setPromotionListener(OnPromotionListener listener) {
    this.promotionListener = listener;
  }

  public BoardView(GameController controller) {
    this.controller = controller;
    createBoard();
    refresh();
  }

  public void setPerspective(PieceColor color) {
    this.currentPerspective = color;
    createBoard();
  }

  public void flipBoard() {
    setPerspective(currentPerspective == PieceColor.WHITE ? PieceColor.BLACK : PieceColor.WHITE);
  }

  private void createBoard() {
    this.getChildren().clear();

    boolean isWhiteBottom = (currentPerspective == PieceColor.WHITE);

    for (int row = 0; row < 8; row++) {
      for (int col = 0; col < 8; col++) {

        int logicalRank = isWhiteBottom ? (7 - row) : row;
        int logicalFile = isWhiteBottom ? col : (7 - col);
        boolean isLight = (logicalRank + logicalFile) % 2 != 0;
        Color color = isLight ? Color.BEIGE : Color.SADDLEBROWN;

        CellView cell = new CellView(color);

        cell.setOnMouseClicked(e -> handleSquareClick(logicalFile, logicalRank));

        cells[logicalFile][logicalRank] = cell;

        if (col == 0) {
          String rankText = String.valueOf(logicalRank + 1);
          cell.addCoordinateOverlay(rankText, true, isLight);
        }
        if (row == 7) {
          char fileText = (char) ('a' + logicalFile);
          cell.addCoordinateOverlay(String.valueOf(fileText), false, isLight);
        }

        this.add(cell, col, row);
      }
    }
    refresh();
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
        if (controller.validateMove(selectedSquare, clickedSquare) && promotionListener != null) {
          promotionListener.onPromotionRequired(
              selectedSquare, clickedSquare, selectedPiece.getColor());
        }
      } else {
        if (controller.validateMove(selectedSquare, clickedSquare)) {
          controller.performMove(selectedSquare, clickedSquare);
          onMoveCompletedListener.checkGameState();
        }
      }
      selectedSquare = null;
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
