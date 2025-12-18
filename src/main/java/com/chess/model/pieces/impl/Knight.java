package com.chess.model.pieces.impl;

import com.chess.model.board.Board;
import com.chess.model.board.Square;
import com.chess.model.move.Move;
import com.chess.model.pieces.Piece;
import com.chess.model.pieces.PieceColor;
import java.util.ArrayList;
import java.util.List;

public class Knight extends Piece {
  private static final int[][] OFFSETS = {
    {1, 2}, {2, 1}, {2, -1}, {1, -2},
    {-1, -2}, {-2, -1}, {-2, 1}, {-1, 2}
  };
  public static int value = 3;

  public Knight(PieceColor color) {
    super(color);
  }

  @Override
  public boolean canMove(Board board, Square start, Square end) {
    if (isTargetSquareInvalid(board, end)) {
      return false;
    }

    int dx = Math.abs(start.file() - end.file());
    int dy = Math.abs(start.rank() - end.rank());

    return (dx == 1 && dy == 2) || (dx == 2 && dy == 1);
  }

  @Override
  public List<Move> getPseudoLegalMoves(Board board, Square start) {
    List<Move> moves = new ArrayList<>();

    for (int[] offset : OFFSETS) {
      Square target = new Square(start.file() + offset[0], start.rank() + offset[1]);
      addMoveIfValid(board, start, target, moves);
    }

    return moves;
  }

  @Override
  public int getValue() {
    return value;
  }

  @Override
  public char getFEN() {
    return color == PieceColor.WHITE ? 'N' : 'n';
  }
}
