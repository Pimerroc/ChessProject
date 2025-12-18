package com.chess.util;

import com.chess.model.board.Board;
import com.chess.model.board.Square;
import com.chess.model.pieces.Piece;
import com.chess.model.pieces.PieceColor;
import com.chess.model.pieces.impl.*;

public class FENUtil {

   public static final String START_FEN = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq -";

    /**
     * Loads a FEN string into the provided Board object.
     */
    public static void loadFen(Board board, String fen) {
        board.clearBoard();

        String[] parts = fen.split(" ");
        
        String position = parts[0];
        int rank = 7;
        int file = 0;

        for (int i = 0; i < position.length(); i++) {
            char c = position.charAt(i);

            if (c == '/') {
                rank--;
                file = 0;
            } else if (Character.isDigit(c)) {
                file += Character.getNumericValue(c);
            } else {
                PieceColor color = Character.isUpperCase(c) ? PieceColor.WHITE : PieceColor.BLACK;
                Piece piece = createPieceFromChar(Character.toLowerCase(c), color);
                
                if (piece != null) {
                    board.setPiece(new Square(file, rank), piece);
                }
                file++;
            }
        }

        PieceColor turn = parts[1].equals("w") ? PieceColor.WHITE : PieceColor.BLACK;
        board.setMovingColor(turn);

        String castling = parts[2];
        board.setCastle(castling);

        String enPassant = parts[3];
        if (!enPassant.equals("-")) {
            int epFile = enPassant.charAt(0) - 'a';
            int epRank = Character.getNumericValue(enPassant.charAt(1)) - 1;
            board.setEnPassantTarget(new Square(epFile, epRank));
        } else {
            board.setEnPassantTarget(null);
        }

        // 5 & 6. Half-move clock & Full-move number (im not doing that)
    }

    /**
     * Generates a FEN string from the current Board state.
     */
    public static String getFen(Board board) {
        StringBuilder sb = new StringBuilder();

        // 1. Piece Placement
        for (int rank = 7; rank >= 0; rank--) {
            int emptyCount = 0;
            for (int file = 0; file < 8; file++) {
                Piece piece = board.getPiece(new Square(file, rank));
                
                if (piece == null) {
                    emptyCount++;
                } else {
                    if (emptyCount > 0) {
                        sb.append(emptyCount);
                        emptyCount = 0;
                    }
                    sb.append(piece.getFEN());
                }
            }
            if (emptyCount > 0) {
                sb.append(emptyCount);
            }
            if (rank > 0) {
                sb.append("/");
            }
        }

        sb.append(" ");

        sb.append(board.getMovingColor() == PieceColor.WHITE ? "w" : "b");
        sb.append(" ");

        String castling = board.getCastle(); 
        
        sb.append(castling.length() == 0 ? "-" : castling);
        sb.append(" ");

        Square ep = board.getEnPassantTarget();
        if (ep != null) {
            sb.append((char) ('a' + ep.file()));
            sb.append(ep.rank() + 1);
        } else {
            sb.append("-");
        }

        // 5 & 6. Clocks (Placeholder defaults if not tracked)
        //sb.append(" 0 1"); 

        return sb.toString();
    }

    private static Piece createPieceFromChar(char c, PieceColor color) {
        return switch (c) {
            case 'p' -> new Pawn(color);
            case 'r' -> new Rook(color);
            case 'n' -> new Knight(color);
            case 'b' -> new Bishop(color);
            case 'q' -> new Queen(color);
            case 'k' -> new King(color);
            default -> null;
        };
    }
}
