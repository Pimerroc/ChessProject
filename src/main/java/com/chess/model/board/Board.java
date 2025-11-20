package main.java.com.chess.model.board;

import main.java.com.chess.model.pieces.Piece;
import main.java.com.chess.model.pieces.PieceColor;

public class Board {
    public Piece getPiece(Square square) {
        return null;
    }

    public Square getEnPassantTarget() {
        return null;
    }

    public boolean isSquareAttacked(Square target, PieceColor attackerColor) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Square attackerPos = new Square(i, j);
                Piece attacker = getPiece(attackerPos);

                if (attacker != null && attacker.getColor() == attackerColor) {
                    if (attacker.canMove(this, attackerPos, target)) {
                        return true;
                    }
                }

            }// for j
        } //for i
        return false;
    }
}
