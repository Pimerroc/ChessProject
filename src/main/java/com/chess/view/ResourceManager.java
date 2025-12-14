package com.chess.view;

import com.chess.model.pieces.Piece;
import com.chess.model.pieces.PieceColor;
import com.chess.model.pieces.impl.*;
import javafx.scene.image.Image;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ResourceManager {
    private final Map<String, Image> cache = new HashMap<>();

    public Image getImage(Piece piece) {
        String key = getKey(piece);
        if (!cache.containsKey(key)) {
            String path = "/images/" + key + ".png";
            try {
                Image img = new Image(Objects.requireNonNull(getClass().getResourceAsStream(path)));
                cache.put(key, img);
            } catch (NullPointerException e) {
                System.err.println("Image not found: " + path);
                return null;
            }
        }
        return cache.get(key);
    }

    private String getKey(Piece piece) {
        String colorPrefix = (piece.getColor() == PieceColor.WHITE) ? "w" : "b";
        String typeSuffix = "";
        
        if (piece instanceof Pawn) typeSuffix = "Pawn";
        else if (piece instanceof Rook) typeSuffix = "Rook";
        else if (piece instanceof Knight) typeSuffix = "Knight";
        else if (piece instanceof Bishop) typeSuffix = "Bishop";
        else if (piece instanceof Queen) typeSuffix = "Queen";
        else if (piece instanceof King) typeSuffix = "King";
        
        return colorPrefix + typeSuffix;
    }
}
