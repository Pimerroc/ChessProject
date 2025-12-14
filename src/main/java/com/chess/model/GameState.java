package com.chess.model;

public enum GameState {
  PLAYING,
  CHECK,
  CHECKMATE,
  STALEMATE;

  public boolean isGameOver() {
    return this == CHECKMATE || this == STALEMATE;
  }
}
