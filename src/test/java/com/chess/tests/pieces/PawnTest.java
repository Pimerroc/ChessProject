package com.chess.tests.pieces;

import com.chess.model.board.Board;
import com.chess.model.board.Square;
import com.chess.model.move.Move;
import com.chess.model.pieces.PieceColor;
import com.chess.model.pieces.impl.King;
import com.chess.model.pieces.impl.Pawn;
import org.junit.jupiter.api.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PawnTest {
    private Board board;

    @BeforeEach
    void setUp() {
        board = new Board();
        for(int i=0; i<8; i++) for(int j=0; j<8; j++) board.setPiece(new Square(i,j), null);
        board.setPiece(new Square(0,0), new King(PieceColor.WHITE));
        board.setPiece(new Square(8, 8), new King(PieceColor.BLACK));
    }

    @Test
    void testWhitePawnMovement() {
        Square start = new Square(4, 1); // e2
        Pawn pawn = new Pawn(PieceColor.WHITE);
        board.setPiece(start, pawn);

        List<Move> moves = board.getLegalMovesForPiece(start);
        List<Square> destinations = moves.stream().map(Move::getEnd).toList();

        assertTrue(destinations.contains(new Square(4, 2))); // e3
        assertTrue(destinations.contains(new Square(4, 3))); // e4
        assertEquals(2, destinations.size());
    }

    @Test
    void testPawnBlocked() {
        Square start = new Square(4, 1);
        board.setPiece(start, new Pawn(PieceColor.WHITE));
        board.setPiece(new Square(4, 2), new Pawn(PieceColor.BLACK)); // Блок

        List<Move> moves = board.getLegalMovesForPiece(start);

        assertTrue(moves.isEmpty(), "Пешка заблокирована и не должна иметь ходов");
    }

    @Test
    void testPawnCapture() {
        Square e2 = new Square(4, 1);
        Square d3 = new Square(3, 2);

        board.setPiece(e2, new Pawn(PieceColor.WHITE));
        board.setPiece(d3, new Pawn(PieceColor.BLACK));

        List<Move> moves = board.getLegalMovesForPiece(e2);
        List<Square> destinations = moves.stream().map(Move::getEnd).toList();

        assertTrue(destinations.contains(d3), "Пешка должна уметь рубить по диагонали");
        assertTrue(destinations.contains(new Square(4, 2))); // e3
    }
}