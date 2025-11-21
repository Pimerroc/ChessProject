package com.chess.model.pieces.piecesImpl;

import com.chess.model.board.Board;
import com.chess.model.board.Square;
import com.chess.util.MoveUtil;
import com.chess.model.pieces.Piece;
import com.chess.model.pieces.PieceColor;

public class Rook extends Piece {
    public Rook(PieceColor color) {
        super(color);
    }

    @Override
    public boolean canMove(Board board, Square start, Square end) {
        if (isTargetSquareInvalid(board, end)) {
            return false;
        }
        int dx = start.file() - end.file();
        int dy = start.file() - end.file();
        
        if (dx != 0 && dy != 0) return false;
    
        return MoveUtil.noOneBlocksThePath(board, start, end);
    }
}
