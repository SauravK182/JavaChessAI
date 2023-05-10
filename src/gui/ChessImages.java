package gui;
import java.util.HashMap;

/**
 * Defines the image locations for all chess pieces to draw the chess board.
 * @author Saurav Kiri
 * @since May 01 2023
 */
public class ChessImages {
    
    /**
     * Defines the mapping between the String representation of pieces and
     * their associated images in the {@code resources} folder.
     * The leading slash indicates the root of the classpath
     */
    public static final HashMap<String, String> PIECES;

    static {
        PIECES = new HashMap<>();
        PIECES.put("p", "/resources/pawnblack.png");
        PIECES.put("r", "/resources/blackrook.png");
        PIECES.put("n", "/resources/blackknight.png");
        PIECES.put("b", "/resources/blackbishop.png");
        PIECES.put("q", "/resources/blackqueen.png");
        PIECES.put("k", "/resources/blackking.png");
        PIECES.put("P", "/resources/pawnwhite.png");
        PIECES.put("R", "/resources/whiterook.png");
        PIECES.put("N", "/resources/whiteknight.png");
        PIECES.put("B", "/resources/whitebishop.png");
        PIECES.put("Q", "/resources/whitequeen.png");
        PIECES.put("K", "/resources/whiteking.png");
    }

    private ChessImages() { }
}
