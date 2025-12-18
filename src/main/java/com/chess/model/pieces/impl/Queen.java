package com.chess.model.pieces.impl;

import com.chess.model.board.Board;
import com.chess.model.board.Square;
import com.chess.model.move.Move;
import com.chess.model.pieces.Piece;
import com.chess.model.pieces.PieceColor;
import com.chess.util.MoveUtil;
import java.util.ArrayList;
import java.util.List;

public class Queen extends Piece {

  private static final int[][] OFFSETS = {
    {0, 1}, {1, 0}, {0, -1}, {-1, 0},
    {1, 1}, {1, -1}, {-1, 1}, {-1, -1}
  };
  public static int value = 9;

  public Queen(PieceColor color) {
    super(color);
  }

  @Override
  public boolean canMove(Board board, Square start, Square end) {
    if (isMoveInvalid(board, start, end)) {
      return false;
    }

    int absDx = Math.abs(start.file() - end.file());
    int absDy = Math.abs(start.rank() - end.rank());

    boolean isDiagonal = absDx == absDy;
    boolean isLinear = (absDx == 0 || absDy == 0);

    if (!isLinear && !isDiagonal) return false;

    return MoveUtil.noOneBlocksThePath(board, start, end);
  }

  @Override
  public List<Move> getPseudoLegalMoves(Board board, Square start) {
    List<Move> moves = new ArrayList<>();
    collectSlidingMoves(board, start, moves, OFFSETS);
    return moves;
  }

  @Override
  public int getValue() {
    return value;
  }

  @Override
  public char getFEN() {
    return color == PieceColor.WHITE ? 'Q' : 'q';
  }
}
