package com.chess.model.pieces;

import com.chess.model.board.Board;
import com.chess.model.board.Square;
import com.chess.model.move.*;

import java.util.List;

public abstract class Piece {
    protected final PieceColor color;
    private boolean hasMoved = false;

    public boolean hasMoved() {
        return hasMoved;
    }

    public void setHasMoved(boolean hasMoved) {
        this.hasMoved = hasMoved;
    }

    public Piece(PieceColor color) {
        this.color = color;
    }

    public PieceColor getColor() {
        return this.color;
    }

    public abstract boolean canMove(Board board, Square start, Square end);

    protected boolean isTargetSquareInvalid(Board board, Square end) {
        Piece piece = board.getPiece(end);
        return piece != null && piece.color == this.color;
    }

    public boolean isMoveInvalid(Board board, Square start, Square end){
        return isTargetSquareInvalid(board, end) || start.equals(end);
    }

    /**
     * Возвращает список возможных ходов БЕЗ проверки на шах собственному королю.
     */
    public abstract List<Move> getPseudoLegalMoves(Board board, Square currentSquare);

    protected void addMoveIfValid(Board board, Square start, Square end, List<Move> moves) {
        if (!end.isValid()) return;
        Piece target = board.getPiece(end);

        if (!isTargetSquareInvalid(board, end)) {
            moves.add(new NormalMove(board, start, end));
        }
    }
}
