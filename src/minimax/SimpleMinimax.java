package minimax;

import com.github.bhlangonijr.chesslib.move.Move;
import com.github.bhlangonijr.chesslib.Board;
import com.github.bhlangonijr.chesslib.Side;

/**
 * Class for creating Minimax objects to predict the best move for an AI playing chess.
 * @author Saurav Kiri
 * @since May 04 2023
 */
public class SimpleMinimax implements Strategy {
    private int depth;
    private BoardEvaluator eval;

    /**
     * Makes a new default Minimax instance with a depth of 10 and
     * the basic {@code MaterialEvaluator}
     */
    public SimpleMinimax() {
        this(10, new MaterialEvaluator());
    }

    /**
     * Makes a new Minimax instance with a depth of {@code depth} and
     * the basic {@code MaterialEvaluator}
     * @param depth : integer, representing the number of moves into the future to look into
     */
    public SimpleMinimax(int depth) {
        this(depth, new MaterialEvaluator());
    }

    /**
     * Makes a new Minimax instance with a depth of {@code depth} and some BoardEvaluator {@code eval}
     * @param depth : integer, representing number of moves into the future to look into
     * @param eval : an object of class {@code BoardEvaluator}
     */
    public SimpleMinimax(int depth, BoardEvaluator eval) {
        if (depth < 0) {
            System.out.println("Invalid depth given. Setting default depth (10).");
            this.depth = 10;
        } else {
            this.depth = depth;
        }
        this.eval = eval;
    }

    public 

    public static void main(String[] args) {
        Board board = new Board();
        String side = board.getSideToMove().toString();
        System.out.println(side);
        Side newSide = Side.BLACK;
    }



    
}
