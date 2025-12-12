package com.chess.model.move;

import com.chess.model.board.Board;
import com.chess.model.board.Square;
import com.chess.model.pieces.Piece;

public class CastlingMove extends Move {
  private final Square rookStart;
  private final Square rookEnd;
  private final Piece rook;

  public CastlingMove(
      Board board, Square kingStart, Square kingEnd, Square rookStart, Square rookEnd) {
    super(board, kingStart, kingEnd);
    this.rookStart = rookStart;
    this.rookEnd = rookEnd;
    this.rook = board.getPiece(rookStart);
  }

  @Override
  public void execute() {
    super.commonExecuteInit();

    board.setPiece(end, movedPiece);
    board.setPiece(start, null);

    board.setPiece(rookEnd, rook);
    board.setPiece(rookStart, null);

    board.setEnPassantTarget(null);
    board.switchTurn();
  }

  @Override
  public void undo() {
    board.setPiece(start, movedPiece);
    board.setPiece(end, null);

    board.setPiece(rookStart, rook);
    board.setPiece(rookEnd, null);

    board.setEnPassantTarget(oldEnPassantTarget);
    board.switchTurn();
  }
}
