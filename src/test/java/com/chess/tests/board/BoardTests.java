package com.chess.tests.board;

import static org.junit.jupiter.api.Assertions.*;

import com.chess.model.board.Board;
import com.chess.model.board.Square;
import com.chess.model.pieces.PieceColor;
import com.chess.model.pieces.impl.King;
import com.chess.model.pieces.impl.Pawn;
import com.chess.model.pieces.impl.Rook;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BoardTests {
  private Board board;

  @BeforeEach
  void setUp() {
    board = new Board();
    for (int i = 0; i < 8; i++) for (int j = 0; j < 8; j++) board.setPiece(new Square(i, j), null);
    board.setPiece(new Square(0, 0), new King(PieceColor.WHITE));
    board.setPiece(new Square(8, 8), new King(PieceColor.BLACK));
  }

  @Test
  void testPiecePlacementUpdatesListsAndPosition() {
    Square e4 = new Square(4, 3);
    Pawn whitePawn = new Pawn(PieceColor.WHITE);
    board.setPiece(e4, whitePawn);
    assertEquals(e4, whitePawn.getPosition(), "Фигура должна знать свои координаты");
    assertTrue(
        board.getPieces(PieceColor.WHITE).contains(whitePawn),
        "Фигура должна появиться в списке белых");
    assertSame(whitePawn, board.getPiece(e4));
  }

  @Test
  void testCaptureOverwriteLists() {
    Square e4 = new Square(4, 3);
    Pawn whitePawn = new Pawn(PieceColor.WHITE);
    Rook blackRook = new Rook(PieceColor.BLACK);

    board.setPiece(e4, blackRook);

    assertTrue(board.getPieces(PieceColor.BLACK).contains(blackRook));

    board.setPiece(e4, whitePawn);

    assertFalse(
        board.getPieces(PieceColor.BLACK).contains(blackRook),
        "Съеденная фигура должна удалиться из списка");
    assertTrue(
        board.getPieces(PieceColor.WHITE).contains(whitePawn), "Новая фигура должна добавиться");
    assertEquals(e4, whitePawn.getPosition());
  }
}
