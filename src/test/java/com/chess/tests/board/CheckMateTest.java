package com.chess.tests.board;

import com.chess.model.GameState;
import com.chess.model.board.Board;
import com.chess.model.board.Square;
import com.chess.model.pieces.PieceColor;
import com.chess.model.pieces.impl.King;
import com.chess.model.pieces.impl.Queen;
import com.chess.model.pieces.impl.Rook;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CheckMateTest {
    private Board board;

    @BeforeEach
    void setUp() {
        board = new Board();
        for(int i=0; i<8; i++) for(int j=0; j<8; j++) board.setPiece(new Square(i,j), null);
    }

    @Test
    void testCheckDetection() {
        // Белый король e1, Черная ладья e8.
        board.setPiece(new Square(4, 0), new King(PieceColor.WHITE));
        board.setPiece(new Square(4, 7), new Rook(PieceColor.BLACK));
        board.setPiece(new Square(0,0), new King(PieceColor.BLACK));

        GameState state = board.checkGameState();
        assertEquals(GameState.CHECK, state, "Должен быть зафиксирован Шах");
    }

    @Test
    void testMate() {

        board.setPiece(new Square(0, 0), new King(PieceColor.WHITE));
        board.setPiece(new Square(0, 7), new Rook(PieceColor.BLACK));
        board.setPiece(new Square(1, 7), new Rook(PieceColor.BLACK));

        GameState state = board.checkGameState();
        assertEquals(GameState.CHECKMATE, state);
    }

    @Test
    void testStalemate() {
        board.setPiece(new Square(0, 0), new King(PieceColor.WHITE));
        board.setPiece(new Square(1, 7), new Rook(PieceColor.BLACK));
        board.setPiece(new Square(7, 1), new Rook(PieceColor.BLACK));
        board.setPiece(new Square(8, 8), new King(PieceColor.BLACK));
        GameState state = board.checkGameState();
        assertEquals(GameState.STALEMATE, state);
    }
}
