package main.java.com.chess.model.pieces.piecesImpl;

import main.java.com.chess.model.board.Board;
import main.java.com.chess.model.board.Square;
import main.java.com.chess.model.pieces.Piece;
import main.java.com.chess.model.pieces.PieceColor;

public class Knight extends Piece {

    public Knight(PieceColor color) {
        super(color);
    }

    @Override
    public boolean canMove(Board board, Square start, Square end) {
        if (!isTargetSquareValid(board, end)){
            return false;
        }

        int dx = Math.abs(start.file() - end.file());
        int dy = Math.abs(start.rank() - end.rank());

        return (dx == 1 && dy == 2) || (dx == 2 && dy == 1);
    }
}
