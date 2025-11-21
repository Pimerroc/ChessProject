package com.chess.model.move;

import com.chess.model.board.Board;
import com.chess.model.board.Square;
import com.chess.model.pieces.Piece;

public class PromotionMove extends Move {
    private final Piece promotionPiece;

    public PromotionMove(Board board, Square start, Square end, Piece promotionPiece) {
        super(board, start, end);
        this.promotionPiece = promotionPiece;
    }

    @Override
    public void execute() {
        this.capturedPiece = board.getPiece(end);
        this.oldEnPassantTarget = board.getEnPassantTarget();

        board.setPiece(start, null);
        board.setPiece(end, promotionPiece);

        board.setEnPassantTarget(null);
        board.switchTurn();
    }

    @Override
    public void undo() {
        board.setPiece(start, movedPiece);
        board.setPiece(end, capturedPiece);

        board.setEnPassantTarget(oldEnPassantTarget);
        board.switchTurn();
    }
}