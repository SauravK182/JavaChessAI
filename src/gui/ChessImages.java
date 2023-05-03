package gui;
import java.util.HashMap;

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

    public static HashMap<String, String> getPieces() {
        return pieces;
    }
}
