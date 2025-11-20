package main.java.com.chess.model.pieces;

import main.java.com.chess.model.board.Board;
import main.java.com.chess.model.board.Square;

public abstract class Piece {
    final PieceColor color;

    public Piece(PieceColor color) {
        this.color = color;
    }

    public PieceColor getColor() {
        return this.color;
    }

    public abstract boolean canMove(Board board, Square start, Square end);

    boolean isTargetSquareValid(Board board, Square end) {
        Piece piece = board.getPiece(end);
        return piece == null || piece.color != this.color;
    }
}
