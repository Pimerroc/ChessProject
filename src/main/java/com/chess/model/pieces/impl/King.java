package com.chess.model.pieces.impl;

import com.chess.model.board.*;
import com.chess.model.move.*;
import com.chess.model.pieces.*;
import java.util.*;

public class King extends Piece {
  public static int value = 100;

  public King(PieceColor color) {
    super(color);
  }

  @Override
  public boolean canMove(Board board, Square start, Square end) {
    if (isTargetSquareInvalid(board, end) || start.equals(end)) return false;

    int absDx = Math.abs(start.file() - end.file());
    int absDy = Math.abs(start.rank() - end.rank());

    return absDx <= 1 && absDy <= 1;
  }

  @Override
  public List<Move> getPseudoLegalMoves(Board board, Square start) {
    List<Move> moves = new ArrayList<>();
    for (int dx = -1; dx < 2; dx++) {
      for (int dy = -1; dy < 2; dy++) {
        Square target = new Square(start.file() + dx, start.rank() + dy);
        addMoveIfValid(board, start, target, moves);
      }
    }

    if (!this.hasMoved()) {
      checkCastling(board, start, 7, 5, 6, moves);
      checkCastling(board, start, 0, 3, 2, moves);
    }
    return moves;
  }

  private void checkCastling(
      Board board, Square kingStart, int rookFile, int empty1, int empty2, List<Move> moves) {
    Square rookSq = new Square(rookFile, kingStart.rank());
    Piece rook = board.getPiece(rookSq);

    if (rook instanceof Rook && !rook.hasMoved()) {
      Square sq1 = new Square(empty1, kingStart.rank());
      Square sq2 = new Square(empty2, kingStart.rank());

      if (board.getPiece(sq1) == null && board.getPiece(sq2) == null) {
        if (!board.isSquareAttacked(kingStart, color.getOpposite())
            && !board.isSquareAttacked(sq1, color.getOpposite())
            && !board.isSquareAttacked(sq2, color.getOpposite())) {

          moves.add(new CastlingMove(board, kingStart, sq2, rookSq, sq1));
        }
      }
    }
  }

  @Override
  public int getValue() {
    return value;
  }
}
