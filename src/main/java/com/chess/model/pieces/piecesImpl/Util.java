package com.chess.model.pieces.piecesImpl;

import com.chess.model.board.Board;
import com.chess.model.board.Square;

public class Util {
    public static boolean noOneBlocksThePath(Board board, Square start, Square end){
        int xDir = Integer.compare(end.file(), start.file());
        int yDir = Integer.compare(end.rank(), start.rank());

        int currentX = start.file() + xDir;
        int currentY = start.rank() + yDir;

        while (currentX != end.file() || currentY != end.rank()) {
            if (board.getPiece(new Square(currentX, currentY)) != null) {
                return false;
            }
            currentX += xDir;
            currentY += yDir;
        }

        return true;
    }

}
