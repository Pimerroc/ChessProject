package com.chess.model.move;

import com.chess.model.board.Board;
import com.chess.model.board.Square;

public class NormalMove extends Move {

    public NormalMove(Board board, Square start, Square end) {
        super(board, start, end);
    }

    @Override
    public void execute() {
        this.oldHasMovedState = movedPiece.hasMoved();
        this.capturedPiece = board.getPiece(end);
        this.oldEnPassantTarget = board.getEnPassantTarget();

        board.setPiece(end, movedPiece);
        board.setPiece(start, null);

        board.updateEnPassantState(movedPiece, start, end);

        movedPiece.setHasMoved(true);
        board.switchTurn();
    }

    @Override
    public void undo() {
        board.setPiece(start, movedPiece);
        board.setPiece(end, capturedPiece);
        movedPiece.setHasMoved(oldHasMovedState);
        board.setEnPassantTarget(oldEnPassantTarget);
        board.switchTurn();
    }
}