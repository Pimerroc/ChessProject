package main.java.com.chess.model.board;

/**
 * @param file - передается число от 0 до 7 соответсвующее символам от a до h.
 * @param rank - передается число от 0 до 7 соответсвующее числам от 1 до 8.
 */
public record Square(int file, int rank) {

    @Override
    public String toString() {
        char file = (char) ('a' + this.file);
        int rank = this.rank + 1;
        return String.format("%c%d", file, rank);
    }

    public boolean isValid() {
        return file >= 0 && file < 8 && rank >= 0 && rank < 8;
    }
}
