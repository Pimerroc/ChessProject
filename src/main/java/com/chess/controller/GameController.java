package com.chess.controller;

import com.chess.model.board.Board;
import com.chess.model.board.Square;
import com.chess.model.move.Move;
import com.chess.model.move.PromotionMove;
import com.chess.model.pieces.Piece;
import com.chess.util.FENUtil;

import java.util.List;

public class GameController {
  private final Board board;

  public GameController() {
    this.board = new Board();
  }

  public Board getBoard() {
    return board;
  }

  public void newGame() {
    board.resetBoard();
  }

  public void loadGameFromFEN(String FEN) {
    board.clearBoard();
    FENUtil.loadFen(board, FEN);
  }

  public String getGameFEN(){
    return FENUtil.getFen(board);
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

  public void performPromotionMove(Square start, Square end, Piece tPiece) {
    PromotionMove move = new PromotionMove(board, start, end, tPiece);
    move.execute();
  }

  public boolean validateMove(Square start, Square end) {
    Piece piece = board.getPiece(start);
    if (piece == null || piece.getColor() != board.getMovingColor()) {
      return false;
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
      return false;
    }

    return true;
  }
}
