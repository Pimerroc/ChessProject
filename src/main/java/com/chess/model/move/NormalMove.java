package com.chess.model.move;

import com.chess.model.board.Board;
import com.chess.model.board.Square;

public class NormalMove extends Move {

  public NormalMove(Board board, Square start, Square end) {
    super(board, start, end);
  }

  @Override
  public void execute() {
    super.commonExecuteInit();
    this.capturedPiece = board.getPiece(end);

    board.setPiece(end, movedPiece);
    board.setPiece(start, null);

    board.updateEnPassantState(movedPiece, start, end);

    board.switchTurn();
  }

  @Override
  public void undo() {
    board.setPiece(start, movedPiece);
    board.setPiece(end, capturedPiece);

    super.restoreState();
    board.switchTurn();
  }
}
