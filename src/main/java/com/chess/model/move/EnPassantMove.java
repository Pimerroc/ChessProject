package com.chess.model.move;

import com.chess.model.board.Board;
import com.chess.model.board.Square;
import com.chess.model.pieces.Piece;

public class EnPassantMove extends Move {
    private final Square victimSquare;

    public EnPassantMove(Board board, Square start, Square end, Square victimSquare) {
        super(board, start, end);
        this.victimSquare = victimSquare;
    }

    @Override
    public void execute() {
        super.commonExecuteInit();

        this.capturedPiece = board.getPiece(victimSquare);

        board.setPiece(end, movedPiece);
        board.setPiece(start, null);

        board.setPiece(victimSquare, null);

        board.setEnPassantTarget(null);
        board.switchTurn();
    }

    @Override
    public void undo() {
        board.setPiece(start, movedPiece);
        board.setPiece(end, null);

        board.setPiece(victimSquare, capturedPiece);

        super.restoreState();
        board.switchTurn();
    }
}