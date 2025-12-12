package com.chess.model.pieces.impl;

import com.chess.model.board.Board;
import com.chess.model.board.Square;
import com.chess.model.move.Move;
import com.chess.model.pieces.Piece;
import com.chess.model.pieces.PieceColor;
import com.chess.util.MoveUtil;
import java.util.ArrayList;
import java.util.List;

public class Bishop extends Piece {
  private static final int[][] OFFSETS = {{1, 1}, {1, -1}, {-1, 1}, {-1, -1}};
  public static int value = 3;

  public Bishop(PieceColor color) {
    super(color);
  }

  @Override
  public boolean canMove(Board board, Square start, Square end) {
    if (isMoveInvalid(board, start, end)) {
      return false;
    }

    int dx = Math.abs(start.file() - end.file());
    int dy = Math.abs(start.rank() - end.rank());
    if (dx != dy) {
      return false;
    }
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
}
