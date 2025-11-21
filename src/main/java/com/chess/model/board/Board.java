package com.chess.model.board;

import com.chess.model.move.Move;
import com.chess.model.pieces.Piece;
import com.chess.model.pieces.PieceColor;
import com.chess.model.pieces.impl.*;

public class Board {
    private final Piece[][] grid = new Piece[8][8];

    private PieceColor movingColor;

    private Square enPassantTarget;

    public Board() {
        resetBoard();
    }

    public void resetBoard() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                grid[i][j] = null;
            }
        }
        movingColor = PieceColor.WHITE;
        enPassantTarget = null;

        placeRowOfPawns(1, PieceColor.WHITE);
        placeMajorPieces(0, PieceColor.WHITE);

        placeRowOfPawns(6, PieceColor.BLACK);
        placeMajorPieces(7, PieceColor.BLACK);
    }

    private void placeRowOfPawns(int rank, PieceColor color) {
        for (int i = 0; i < 8; i++) {
            setPiece(new Square(i, rank), new Pawn(color));
        }
    }

    private void placeMajorPieces(int rank, PieceColor color) {
        setPiece(new Square(0, rank), new Rook(color));
        setPiece(new Square(1, rank), new Knight(color));
        setPiece(new Square(2, rank), new Bishop(color));
        setPiece(new Square(3, rank), new Queen(color));
        setPiece(new Square(4, rank), new King(color));
        setPiece(new Square(5, rank), new Bishop(color));
        setPiece(new Square(6, rank), new Knight(color));
        setPiece(new Square(7, rank), new Rook(color));
    }


    public Piece getPiece(Square square) {
        if (!square.isValid()) return null;
        return grid[square.file()][square.rank()];
    }

    public void setPiece(Square square, Piece piece) {
        if (square.isValid()){
            grid[square.file()][square.rank()] = piece;
        }
    }

    public Square getEnPassantTarget() {
        return enPassantTarget;
    }

    public PieceColor getMovingColor() {
        return movingColor;
    }

    public boolean isSquareAttacked(Square target, PieceColor attackerColor) {
        for (int file = 0; file < 8; file++) {
            for (int rank = 0; rank < 8; rank++) {
                Square attackerPos = new Square(file, rank);
                Piece attacker = getPiece(attackerPos);

                if (attacker != null && attacker.getColor() == attackerColor) {
                    if (attacker.canMove(this, attackerPos, target)) {
                        return true;
                    }
                }

            }// for rank
        } //for file
        return false;
    }
    public void executeMove(Move move) {
        move.execute();
    }

    public void undoMove(Move move) {
        move.undo();
    }
    /**
     * Находит координаты короля заданного цвета.
     * Нужно для проверки шаха.
     */
    public Square findKing(PieceColor color) {
        for (int file = 0; file < 8; file++) {
            for (int rank = 0; rank < 8; rank++) {
                Piece p = grid[file][rank];
                if (p instanceof King && p.getColor() == color) {
                    return new Square(file, rank);
                }
            }
        }
        throw new IllegalStateException("King not found! Board is invalid.");
    }

    public void updateEnPassantState(Piece movedPiece, Square start, Square end) {
        enPassantTarget = null;
        if (movedPiece instanceof Pawn && Math.abs(start.rank() - end.rank()) == 2) {
            enPassantTarget = new Square(start.file(), (start.rank() + end.rank()) / 2);
        }
    }

    public void switchTurn() {
        movingColor = movingColor.getOpposite();
    }

    public void setEnPassantTarget(Square sq) {
        enPassantTarget = sq;
    }
}
