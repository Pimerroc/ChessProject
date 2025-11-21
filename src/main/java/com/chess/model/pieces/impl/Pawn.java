package com.chess.model.pieces.impl;

import com.chess.model.board.*;
import com.chess.model.pieces.*;

public class Pawn extends Piece {

    public Pawn(PieceColor color) {
        super(color);
    }

    @Override
    public boolean canMove(Board board, Square start, Square end) {
        if (start.equals(end)) return false;
        int direction = (color == PieceColor.WHITE) ? 1 : -1;
        int dx = start.file() - end.file();
        int dy = start.rank() - end.rank();
        int diffX = Math.abs(dx);
        int diffY = Math.abs(dy);

        if (diffY > 2 || diffX > 1) return false;
        if (dx == 0 && board.getPiece(end) == null) {
            if (dy == 2 * direction && board.getPiece(new Square(start.file(), start.rank() + direction)) == null) {
                return (start.rank() == 1 && getColor() == PieceColor.WHITE) ||
                        (start.rank() == 6 && getColor() == PieceColor.BLACK);
            } else return dy == direction;
        }
        if (diffX == 1 && dy == direction) {
            Piece target = board.getPiece(end);
            if (target != null && target.getColor() != getColor()) {
                return true;
            }
            if (target == null && end.equals(board.getEnPassantTarget())) {
                return true;
            }
        }
        return false;
    }
}
