package com.chess.tests;

import com.chess.model.GameState;
import com.chess.model.board.Board;
import com.chess.model.board.Square;
import com.chess.model.move.Move;
import com.chess.model.pieces.Piece;
import com.chess.model.pieces.impl.Rook;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OperaGameTest {
    private Board board;

    @BeforeEach
    void setUp() {
        board = new Board();
    }

    @Test
    void testMorphyVsDukeOfBrunswick() {
        // 1. e4 e5
        play("e2", "e4");
        play("e7", "e5");

        // 2. Nf3 d6
        play("g1", "f3");
        play("d7", "d6");

        // 3. d4 Bg4
        play("d2", "d4");
        play("c8", "g4");

        // 4. dxe5 Bxf3
        play("d4", "e5");
        play("g4", "f3"); // Взятие

        // 5. Qxf3 dxe5
        play("d1", "f3");
        play("d6", "e5");

        // 6. Bc4 Nf6
        play("f1", "c4");
        play("g8", "f6");

        // 7. Qb3 Qe7
        play("f3", "b3");
        play("d8", "e7");

        // 8. Nc3 c6
        play("b1", "c3");
        play("c7", "c6");

        // 9. Bg5 b5?
        play("c1", "g5");
        play("b7", "b5");

        // 10. Nxb5! cxb5
        play("c3", "b5");
        play("c6", "b5");

        // 11. Bxb5+ Nbd7
        play("c4", "b5");
        assertEquals(GameState.CHECK, board.checkGameState(), "На 11 ходу должен быть Шах черным");
        play("b8", "d7");

        // 12. O-O-O (Длинная рокировка) Rd8
        play("e1", "c1");
        Piece rook = board.getPiece(sq("d1"));
        assertNotNull(rook, "Ладья должна быть на d1 после длинной рокировки");
        assertInstanceOf(Rook.class, rook);
        play("a8", "d8");

        // 13. Rxd7 Rxd7
        play("d1", "d7");
        play("d8", "d7");

        // 14. Rd1 Qe6
        play("h1", "d1");
        play("e7", "e6");

        // 15. Bxd7+ Nxd7
        play("b5", "d7");
        play("f6", "d7");

        // 16. Qb8+!! (Жертва ферзя) Nxb8
        play("b3", "b8");
        play("d7", "b8");

        // 17. Rd8# (Мат)
        play("d1", "d8");

        GameState state = board.checkGameState();
        assertEquals(GameState.CHECKMATE, state, "Партия должна закончиться матом");

        System.out.println("Тест пройден! Пол Морфи победил.");
    }


    /**
     * Делает ход по строковым координатам (например, "e2", "e4").
     * Этот метод ищет легальный ход и применяет его.
     */
    private void play(String from, String to) {
        Square start = sq(from);
        Square end = sq(to);

        List<Move> legalMoves = board.getLegalMovesForPiece(start);

        Move move = legalMoves.stream()
                .filter(m -> m.getEnd().equals(end))
                .findFirst()
                .orElse(null);

        if (move == null) {
            fail("Ход " + from + "-" + to + " считается нелегальным! Доступные ходы: " +
                    legalMoves.stream().map(m -> m.getEnd().toString()).toList());
        }

        move.execute();
    }

    /**
     * Парсит строку типа "e2" в объект Square(4, 1).
     */
    private Square sq(String coord) {
        if (coord.length() != 2) throw new IllegalArgumentException("Invalid coord: " + coord);

        char fileChar = coord.charAt(0);
        char rankChar = coord.charAt(1);

        int file = fileChar - 'a';
        int rank = rankChar - '1';

        return new Square(file, rank);
    }
}
