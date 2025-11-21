package com.chess.tests.board;

import com.chess.model.board.Board;
import com.chess.model.board.Square;
import com.chess.model.move.Move;
import com.chess.model.pieces.PieceColor;
import com.chess.model.pieces.impl.King;
import com.chess.model.pieces.impl.Rook;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PinTest {
    private Board board;

    @BeforeEach
    void setUp() {
        board = new Board();
        for(int i=0; i<8; i++) for(int j=0; j<8; j++) board.setPiece(new Square(i,j), null);
    }

    @Test
    void testAbsolutePin() {
        // Ситуация:
        // Белый Король на e1
        // Белая Ладья на e2 (связана!)
        // Черная Ладья на e8 (атакует по вертикали e)

        Square kingPos = new Square(4, 0); // e1
        Square pinnedRookPos = new Square(4, 1); // e2
        Square enemyRookPos = new Square(4, 7); // e8

        board.setPiece(kingPos, new King(PieceColor.WHITE));
        board.setPiece(pinnedRookPos, new Rook(PieceColor.WHITE));
        board.setPiece(enemyRookPos, new Rook(PieceColor.BLACK));

        List<Move> moves = board.getLegalMovesForPiece(pinnedRookPos);

        for (Move m : moves) {
            Square end = m.getEnd();
            assertEquals(4, end.file(), "Связанная ладья может двигаться только по вертикали E");
        }

        assertFalse(moves.isEmpty(), "Ладья должна иметь возможность пойти вперед или съесть врага");
    }
}