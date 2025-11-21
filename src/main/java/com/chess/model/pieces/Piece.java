package com.chess.model.pieces;

import com.chess.model.board.Board;
import com.chess.model.board.Square;

public abstract class Piece {
    protected final PieceColor color;

    public Piece(PieceColor color) {
        this.color = color;
    }

    public PieceColor getColor() {
        return this.color;
    }

    public abstract boolean canMove(Board board, Square start, Square end);

    protected boolean isTargetSquareValid(Board board, Square end) {
        Piece piece = board.getPiece(end);
        return piece == null || piece.color != this.color;
    }
}
