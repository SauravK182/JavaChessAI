package gui;
import java.util.HashMap;

/**
 * Defines the image locations for all chess pieces to draw the chess board.
 * @author Saurav Kiri
 * @since May 01 2023
 */
public class ChessImages {
    
    private static HashMap<String, String> pieces;

    static {
        pieces = new HashMap<>();
        pieces.put("p", "./images/pawnblack.png");
        pieces.put("r", "./images/blackrook.png");
        pieces.put("n", "./images/blackknight.png");
        pieces.put("b", "./images/blackbishop.png");
        pieces.put("q", "./images/blackqueen.png");
        pieces.put("k", "./images/blackking.png");
        pieces.put("P", "./images/pawnwhite.png");
        pieces.put("R", "./images/whiterook.png");
        pieces.put("N", "./images/whiteknight.png");
        pieces.put("B", "./images/whitebishop.png");
        pieces.put("Q", "./images/whitequeen.png");
        pieces.put("K", "./images/whiteking.png");
    }

    private ChessImages() { }

    /**
     * Retrieves the set of chess piece string representations and their associated file locations.
     * @return String/String Hashmap of the pieces and their files.
     */
    public static HashMap<String, String> getPieces() {
        return pieces;
    }
}
