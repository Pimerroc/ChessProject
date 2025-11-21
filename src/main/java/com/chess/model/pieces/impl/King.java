package com.chess.model.pieces.impl;

import com.chess.model.board.Board;
import com.chess.model.board.Square;
import com.chess.model.pieces.Piece;
import com.chess.model.pieces.PieceColor;

public class King extends Piece {
    public King(PieceColor color) {
        super(color);
    }

    @Override
    public boolean canMove(Board board, Square start, Square end) {
        if (isTargetSquareInvalid(board, end) || start.equals(end)) return false;

        int absDx = Math.abs(start.file() - end.file());
        int absDy = Math.abs(start.rank() - end.rank());

        return absDx <= 1 && absDy <= 1;
    }
}
