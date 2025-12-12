package com.chess.model.move;

import com.chess.model.board.Board;
import com.chess.model.board.Square;
import com.chess.model.pieces.Piece;

public abstract class Move {
  protected final Board board;
  protected final Square start;
  protected final Square end;
  protected final Piece movedPiece;
  protected Piece capturedPiece;
  protected Square oldEnPassantTarget;
  protected boolean oldHasMovedState;

  public Move(Board board, Square start, Square end) {
    this.board = board;
    this.start = start;
    this.end = end;
    this.movedPiece = board.getPiece(start);
  }

  public abstract void execute();

  public abstract void undo();

  public boolean isCapture() {
    return capturedPiece != null;
  }

  public Square getEnd() {
    return end;
  }

  protected void commonExecuteInit() {
    this.oldHasMovedState = movedPiece.hasMoved();
    this.oldEnPassantTarget = board.getEnPassantTarget();
    movedPiece.setHasMoved(true);
  }

  protected void restoreState() {
    movedPiece.setHasMoved(oldHasMovedState);
    board.setEnPassantTarget(oldEnPassantTarget);
  }
}

