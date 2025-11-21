package com.chess.model.pieces.impl;

import com.chess.model.board.Board;
import com.chess.model.board.Square;
import com.chess.model.move.Move;
import com.chess.util.MoveUtil;
import com.chess.model.pieces.Piece;
import com.chess.model.pieces.PieceColor;

import java.util.ArrayList;
import java.util.List;

public class Rook extends Piece {
    private static final int[][] OFFSETS = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};
    public static int value = 5;

    public Rook(PieceColor color) {
        super(color);
    }

    @Override
    public boolean canMove(Board board, Square start, Square end) {
        if (isMoveInvalid(board, start, end)) {
            return false;
        }
        int dx = start.file() - end.file();
        int dy = start.rank() - end.rank();

        if (dx != 0 && dy != 0) return false;

        return MoveUtil.noOneBlocksThePath(board, start, end);
    }

    @Override
    public List<Move> getPseudoLegalMoves(Board board, Square start) {
        List<Move> moves = new ArrayList<>();
        collectSlidingMoves(board, start, moves, OFFSETS);
        return moves;
    }

    @Override
    public int getValue() {
        return value;
    }
}
