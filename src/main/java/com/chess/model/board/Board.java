package com.chess.model.board;

import com.chess.model.GameState;
import com.chess.model.move.Move;
import com.chess.model.pieces.Piece;
import com.chess.model.pieces.PieceColor;
import com.chess.model.pieces.impl.*;

import java.util.*;

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

    /**
     * ГЛАВНЫЙ МЕТОД ЛОГИКИ.
     * Возвращает только те ходы, которые разрешены правилами (нет шаха королю).
     */
    public List<Move> getLegalMovesForPiece(Square square) {
        Piece piece = getPiece(square);
        if (piece == null) return Collections.emptyList();

        List<Move> pseudoMoves = piece.getPseudoLegalMoves(this, square);
        List<Move> legalMoves = new ArrayList<>();

        for (Move move : pseudoMoves) {
            move.execute();

            PieceColor myColor = movingColor.getOpposite();
            Square kingSquare = findKing(myColor);

            if (!isSquareAttacked(kingSquare, movingColor)) {
                legalMoves.add(move);
            }

            move.undo();
        }
        return legalMoves;
    }
    public GameState checkGameState() {
        PieceColor current = getMovingColor();

        boolean hasLegalMoves = false;

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Piece p = grid[i][j];
                if (p != null && p.getColor() == current) {
                    if (!getLegalMovesForPiece(new Square(i, j)).isEmpty()) {
                        hasLegalMoves = true;
                        break;
                    }
                }
            }
            if (hasLegalMoves) break;
        }

        Square kingPos = findKing(current);
        boolean isCheck = isSquareAttacked(kingPos, current.getOpposite());

        if (isCheck && !hasLegalMoves) {
            return GameState.CHECKMATE;
        }
        if (!isCheck && !hasLegalMoves) {
            return GameState.STALEMATE;
        }
        if (isCheck) {
            return GameState.CHECK;
        }
        return GameState.PLAYING;
    }
}
