package minimax;

import com.github.bhlangonijr.chesslib.move.Move;
import com.github.bhlangonijr.chesslib.Board;
import com.github.bhlangonijr.chesslib.Side;

import java.lang.Math;
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
        this(4, new MaterialEvaluator());
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
        if (depth < 1) {
            System.out.println("Invalid depth given. Setting default depth (10).");
            this.depth = 4;
        } else {
            this.depth = depth;
        }
        this.eval = eval;
    }

    @Override
    public Move findBestMove(Board board) {
        // initialize optimal moves
        Move optimalMove = null;
        double optimalScore;
        Side player = board.getSideToMove();
        if (player == Side.WHITE) optimalScore = Double.NEGATIVE_INFINITY; else optimalScore = Double.POSITIVE_INFINITY;

        for (Move move: board.legalMoves()) {
            board.doMove(move);
            double moveEval = numericalMoveCalculator(board, this.depth - 1);
            if ((player == Side.WHITE && moveEval > optimalScore) || (player == Side.BLACK && moveEval < optimalScore)) {
                optimalScore = moveEval;
                optimalMove = move;
            }
            board.undoMove();
        }
        return optimalMove;
    }

    private double numericalMoveCalculator(Board board, int depth) {
        if (depth == 0) {
            return this.eval.evaluationScheme(board);
        }

        if (board.getSideToMove() == Side.WHITE) {
            double maxScore = Double.NEGATIVE_INFINITY;
            for (Move move: board.legalMoves()) {
                board.doMove(move);
                maxScore = Math.max(maxScore, numericalMoveCalculator(board, depth - 1));
                board.undoMove();
            }
            return maxScore;

        } else {
            double minScore = Double.POSITIVE_INFINITY;
            for (Move move: board.legalMoves()) {
                board.doMove(move);
                minScore = Math.min(minScore, numericalMoveCalculator(board, depth - 1));
                board.undoMove();
            }
            return minScore;
        }
    }    
}