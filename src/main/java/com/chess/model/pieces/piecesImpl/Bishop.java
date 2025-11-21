package com.chess.model.pieces.piecesImpl;

import com.chess.model.board.Board;
import com.chess.model.board.Square;
import com.chess.model.pieces.Piece;
import com.chess.model.pieces.PieceColor;
import com.chess.util.MoveUtil;

public class Bishop extends Piece {
    public Bishop(PieceColor color) {
        super(color);
    }

    @Override
    public boolean canMove(Board board, Square start, Square end) {
        if (isTargetSquareInvalid(board, end) || start.equals(end)){
            return false;
        }

        int dx = start.file() - end.file();
        int dy = start.rank() - end.rank();
        if (dx != dy){
            return false;
        }
        return MoveUtil.noOneBlocksThePath(board, start, end);
    }
}
