package com.chess.model.pieces.impl;

import com.chess.model.board.*;
import com.chess.model.move.*;
import com.chess.model.pieces.*;

import java.util.*;

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
                return (start.rank() == 1 && getColor() == PieceColor.WHITE) || (start.rank() == 6 && getColor() == PieceColor.BLACK);
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

    @Override
    public List<Move> getPseudoLegalMoves(Board board, Square start) {
        List<Move> moves = new ArrayList<>();
        int direction = (color == PieceColor.WHITE) ? 1 : -1;
        int promotionRank = (color == PieceColor.WHITE) ? 7 : 0;
        int startRank = (color == PieceColor.WHITE) ? 1 : 6;

        Square oneStep = new Square(start.file(), start.rank() + direction);
        if (oneStep.isValid() && board.getPiece(oneStep) == null) {
            addPawnMove(board, start, oneStep, moves, oneStep.rank() == promotionRank);

            Square twoStep = new Square(start.file(), start.rank() + direction * 2);
            if (start.rank() == startRank && board.getPiece(twoStep) == null) {
                moves.add(new NormalMove(board, start, twoStep));
            }
        }

        int[] offsets = {-1, 1};
        for (int offset : offsets) {
            Square attackSq = new Square(start.file() + offset, start.rank() + direction);
            if (attackSq.isValid()) {
                Piece target = board.getPiece(attackSq);

                if (target != null && target.getColor() != color) {
                    addPawnMove(board, start, attackSq, moves, attackSq.rank() == promotionRank);
                }
                else if (target == null && attackSq.equals(board.getEnPassantTarget())) {
                    Square victimSq = new Square(attackSq.file(), start.rank());
                    moves.add(new EnPassantMove(board, start, attackSq, victimSq));
                }
            }
        }
        return moves;
    }

    private void addPawnMove(Board board, Square start, Square end, List<Move> moves, boolean isPromotion) {
        if (isPromotion) {
            moves.add(new PromotionMove(board, start, end, new Queen(color)));
            moves.add(new PromotionMove(board, start, end, new Rook(color)));
            moves.add(new PromotionMove(board, start, end, new Bishop(color)));
            moves.add(new PromotionMove(board, start, end, new Knight(color)));
        } else {
            moves.add(new NormalMove(board, start, end));
        }
    }
}
