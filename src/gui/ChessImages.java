package gui;
import java.util.HashMap;

/**
 * Defines the image locations for all chess pieces to draw the chess board.
 * @author Saurav Kiri
 * @since May 01 2023
 */
public class ChessImages {
    
    public static final HashMap<String, String> PIECES;

    static {
        PIECES = new HashMap<>();
        PIECES.put("p", "./images/pawnblack.png");
        PIECES.put("r", "./images/blackrook.png");
        PIECES.put("n", "./images/blackknight.png");
        PIECES.put("b", "./images/blackbishop.png");
        PIECES.put("q", "./images/blackqueen.png");
        PIECES.put("k", "./images/blackking.png");
        PIECES.put("P", "./images/pawnwhite.png");
        PIECES.put("R", "./images/whiterook.png");
        PIECES.put("N", "./images/whiteknight.png");
        PIECES.put("B", "./images/whitebishop.png");
        PIECES.put("Q", "./images/whitequeen.png");
        PIECES.put("K", "./images/whiteking.png");
    }

    private ChessImages() { }
}
