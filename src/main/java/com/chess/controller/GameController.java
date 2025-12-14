package com.chess.controller;

import com.chess.model.board.Board;
import com.chess.model.board.Square;
import com.chess.model.move.Move;
import com.chess.model.pieces.Piece;
import java.util.List;

public class GameController {
  private final Board board;

  public GameController(Board board) {
    this.board = board;
  }

  public Board getBoard() {
    return board;
  }

  public void newGame() {
    board.resetBoard();
  }

  public void performMove(Square start, Square end) {
    Piece piece = board.getPiece(start);
    if (piece == null || piece.getColor() != board.getMovingColor()) {
      return;
    }

    List<Move> legalMoves = board.getLegalMovesForPiece(start);

    Move move = null; 

    for (Move mv : legalMoves) {
      if (mv.getEnd().equals(end)) { 
        move = mv;
        break; 
      }
    }
    if (move == null) {
      return;
    }

    move.execute();
  }
}
