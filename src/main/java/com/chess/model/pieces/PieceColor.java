package com.chess.model.pieces;

public enum PieceColor {
  WHITE,
  BLACK;

  public PieceColor getOpposite() {
    return this == BLACK ? WHITE : BLACK;
  }
}
