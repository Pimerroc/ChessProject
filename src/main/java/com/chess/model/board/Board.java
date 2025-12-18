package com.chess.model.board;

import com.chess.model.GameState;
import com.chess.model.move.Move;
import com.chess.model.pieces.Piece;
import com.chess.model.pieces.PieceColor;
import com.chess.model.pieces.impl.*;
import java.util.*;

public class Board {
  private final Piece[][] grid = new Piece[8][8];

  private final List<Piece> whitePieces = new ArrayList<>();
  private final List<Piece> blackPieces = new ArrayList<>();

  private PieceColor movingColor;

  private Square enPassantTarget;

  public Board() {
    resetBoard();
  }

  public void clearBoard() {
    whitePieces.clear();
    blackPieces.clear();
    for (int i = 0; i < 8; i++) {
      for (int j = 0; j < 8; j++) {
        grid[i][j] = null;
      }
    }
    movingColor = PieceColor.WHITE;
    enPassantTarget = null;
  }

  public void resetBoard() {
    clearBoard();

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
    if (!square.isValid()) return;

    Piece oldPiece = grid[square.file()][square.rank()];

    if (oldPiece != null) {
      getPieces(oldPiece.getColor()).remove(oldPiece);
    }

    grid[square.file()][square.rank()] = piece;

    if (piece != null) {
      piece.setPosition(square);
      getPieces(piece.getColor()).add(piece);
    }
  }

  public List<Piece> getPieces(PieceColor color) {
    return (color == PieceColor.WHITE) ? whitePieces : blackPieces;
  }

  public Square getEnPassantTarget() {
    return enPassantTarget;
  }

  public void setEnPassantTarget(Square sq) {
    enPassantTarget = sq;
  }

  public void updateEnPassantState(Piece movedPiece, Square start, Square end) {
    enPassantTarget = null;
    if (movedPiece instanceof Pawn && Math.abs(start.rank() - end.rank()) == 2) {
      enPassantTarget = new Square(start.file(), (start.rank() + end.rank()) / 2);
    }
  }

  public void setMovingColor(PieceColor movingColor) {
    this.movingColor = movingColor;
  }

  public PieceColor getMovingColor() {
    return movingColor;
  }

  public void switchTurn() {
    movingColor = movingColor.getOpposite();
  }

  public boolean isSquareAttacked(Square target, PieceColor attackerColor) {
    for (Piece attacker : getPieces(attackerColor)) {
      Square attackerPos = attacker.getPosition();
      if (attacker.canMove(this, attackerPos, target)) {
        return true;
      }
    }
    return false;
  }

  public Square findKing(PieceColor color) {
    for (Piece p : getPieces(color)) {
      if (p instanceof King && p.getColor() == color) {
        return p.getPosition();
      }
    }
    throw new IllegalStateException("King not found! Board is invalid.");
  }

  public boolean hasAnyLegalMoves(PieceColor color) {
    List<Piece> pieces = new ArrayList<>(getPieces(color));
    for (Piece piece : pieces) {
      Square pos = piece.getPosition();
      if (!getLegalMovesForPiece(pos).isEmpty()) {
        return true;
      }
    }
    return false;
  }

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

  public String getCastle() {
    StringBuilder sb = new StringBuilder();

    Piece wKing = getPiece(new Square(4, 0));
    if (wKing instanceof King && !wKing.hasMoved()) {
      Piece wRookK = getPiece(new Square(7, 0));
      if (wRookK instanceof Rook && !wRookK.hasMoved()) {
        sb.append("K");
      }
      Piece wRookQ = getPiece(new Square(0, 0));
      if (wRookQ instanceof Rook && !wRookQ.hasMoved()) {
        sb.append("Q");
      }
    }

    Piece bKing = getPiece(new Square(4, 7));
    if (bKing instanceof King && !bKing.hasMoved()) {
      Piece bRookK = getPiece(new Square(7, 7));
      if (bRookK instanceof Rook && !bRookK.hasMoved()) {
        sb.append("k");
      }
      Piece bRookQ = getPiece(new Square(0, 7));
      if (bRookQ instanceof Rook && !bRookQ.hasMoved()) {
        sb.append("q");
      }
    }

    return sb.length() == 0 ? "-" : sb.toString();
  }

  public void setCastle(String castleString) {
    for (Piece p : whitePieces) {
      if (p instanceof King || p instanceof Rook) p.setHasMoved(true);
    }
    for (Piece p : blackPieces) {
      if (p instanceof King || p instanceof Rook) p.setHasMoved(true);
    }

    if (castleString == null || castleString.equals("-")) return;

    if (castleString.contains("K")) {
      enableCastle(new Square(4, 0), new Square(7, 0));
    }
    if (castleString.contains("Q")) {
      enableCastle(new Square(4, 0), new Square(0, 0));
    }
    if (castleString.contains("k")) {
      enableCastle(new Square(4, 7), new Square(7, 7));
    }
    if (castleString.contains("q")) {
      enableCastle(new Square(4, 7), new Square(0, 7));
    }
  }

  private void enableCastle(Square kingPos, Square rookPos) {
    Piece king = getPiece(kingPos);
    Piece rook = getPiece(rookPos);
    if (king instanceof King) king.setHasMoved(false);
    if (rook instanceof Rook) rook.setHasMoved(false);
  }

  public GameState checkGameState() {
    PieceColor current = movingColor;

    boolean hasLegalMoves = hasAnyLegalMoves(movingColor);

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

  public int calculateScore(PieceColor color) {
    int score = 0;
    for (Piece p : getPieces(color)) {
      score += p.getValue();
    }
    return score;
  }
}
