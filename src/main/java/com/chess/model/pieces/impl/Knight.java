package com.chess.model.pieces.impl;

import com.chess.model.board.Board;
import com.chess.model.board.Square;
import com.chess.model.pieces.Piece;
import com.chess.model.pieces.PieceColor;

public class Knight extends Piece {

    public Knight(PieceColor color) {
        super(color);
    }

    @Override
    public boolean canMove(Board board, Square start, Square end) {
        if (isTargetSquareInvalid(board, end)){
            return false;
        }

        int dx = Math.abs(start.file() - end.file());
        int dy = Math.abs(start.rank() - end.rank());

        return (dx == 1 && dy == 2) || (dx == 2 && dy == 1);
    }
}
