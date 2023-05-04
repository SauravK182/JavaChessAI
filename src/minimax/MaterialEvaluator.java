package minimax;

import java.util.HashMap;

import com.github.bhlangonijr.chesslib.Board;

/**
 * A simple evaluation class for chess pieces. Defines piece weight using the conventional
 * chess point system (mid-game) for simple material evaluation.
 * @author Saurav Kiri
 * @since May 03, 2023
 */
public class MaterialEvaluator implements BoardEvaluator {

    private static HashMap<String, Double> pieceWeight;

    /* weights are defined as per Dan Heisman's book 'Everyone's 2nd Chess Book':
     * https://www.chess.com/forum/view/general/whats-a-chess-piece-worth
     */
     static {
        pieceWeight = new HashMap<>();
        pieceWeight.put("p", -1d);
        pieceWeight.put("r", -5d);
        pieceWeight.put("n", -3.25d);
        pieceWeight.put("b", -3.25d);
        pieceWeight.put("q", -10d);
        pieceWeight.put("k", -100d);
        
        // add white pieces - simply make chars upper case, add positive values
        HashMap<String, Double> whitePieceWeights = new HashMap<>();
        pieceWeight.forEach((key, value) -> {
            double whitePieceWeight = (double) -1 * value;
            whitePieceWeights.put(key.toUpperCase(), whitePieceWeight);
        });

        // merge maps - do not need to worry about overwriting keys
        pieceWeight.putAll(whitePieceWeights);
    }

    /**
     * Get the piece weighting for this evaluator.
     * @return Hashmap of the single-letter piece representations and their relative scoring.
     */
    public static HashMap<String, Double> getPieceWeight() {
        return pieceWeight;
    }

    /**
     * Evaluates the board by summing all material currently on the board, given the
     * point mappings defined in the {@code pieceWeight} instance variable.
     * @return A double indicating total piece value on the board.
     */
    @Override
    public double evaluationScheme(Board board) {
        double materialSum = 0;
        for (char position: board.toString().replaceAll("Side:.*", "").toCharArray()) {
            String piece = Character.toString(position);
            if (pieceWeight.containsKey(piece)) {
                materialSum+=pieceWeight.get(piece);
            }
        }
        return materialSum;
    }

}

