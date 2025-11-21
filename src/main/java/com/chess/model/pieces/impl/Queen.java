package com.chess.model.pieces.impl;

import com.chess.model.board.Board;
import com.chess.model.board.Square;
import com.chess.model.pieces.Piece;
import com.chess.model.pieces.PieceColor;
import com.chess.util.MoveUtil;

public class Queen extends Piece {
    public Queen(PieceColor color) {
        super(color);
    }

    @Override
    public boolean canMove(Board board, Square start, Square end) {
        if (isTargetSquareInvalid(board, end) || start.equals(end)) {
            return false;
        }

        int absDx = Math.abs(start.file() - end.file());
        int absDy = Math.abs(start.rank() - end.rank());

        boolean isDiagonal = absDx == absDy;
        boolean isLinear = (absDx == 0 || absDy == 0);

        if (!isLinear && !isDiagonal) return false;

        return MoveUtil.noOneBlocksThePath(board, start, end);
    }
}
