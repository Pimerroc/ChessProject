package com.chess.model.pieces;

import com.chess.model.board.Board;
import com.chess.model.board.Square;
import com.chess.model.move.*;
import java.util.List;

public abstract class Piece {
  protected final PieceColor color;
  protected Square position;
  private boolean hasMoved = false;

  public boolean hasMoved() {
    return hasMoved;
  }

  public void setHasMoved(boolean hasMoved) {
    this.hasMoved = hasMoved;
  }

  public Piece(PieceColor color) {
    this.color = color;
  }

  public PieceColor getColor() {
    return this.color;
  }

  public abstract boolean canMove(Board board, Square start, Square end);

  protected boolean isTargetSquareInvalid(Board board, Square end) {
    Piece piece = board.getPiece(end);
    return piece != null && piece.color == this.color;
  }

  public boolean isMoveInvalid(Board board, Square start, Square end) {
    return isTargetSquareInvalid(board, end) || start.equals(end);
  }

  /** Возвращает список возможных ходов БЕЗ проверки на шах собственному королю. */
  public abstract List<Move> getPseudoLegalMoves(Board board, Square currentSquare);

  protected void addMoveIfValid(Board board, Square start, Square end, List<Move> moves) {
    if (!end.isValid()) return;
    if (!isMoveInvalid(board, start, end)) {
      moves.add(new NormalMove(board, start, end));
    }
  }

  public void setPosition(Square position) {
    this.position = position;
  }

  public Square getPosition() {
    return position;
  }

  protected void collectSlidingMoves(
      Board board, Square start, List<Move> moves, int[][] directions) {
    for (int[] dir : directions) {
      int dx = dir[0];
      int dy = dir[1];

      int x = start.file() + dx;
      int y = start.rank() + dy;

      while (true) {
        Square target = new Square(x, y);

        if (!target.isValid()) break;

        Piece targetPiece = board.getPiece(target);

        if (targetPiece == null) {
          moves.add(new NormalMove(board, start, target));
        } else {
          if (targetPiece.getColor() != this.color) {
            moves.add(new NormalMove(board, start, target));
          }
          break;
        }

        x += dx;
        y += dy;
      }
    }
  }

  public abstract int getValue();

  public abstract char getFEN();
}
