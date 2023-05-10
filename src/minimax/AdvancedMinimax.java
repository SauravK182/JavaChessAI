package minimax;

import com.github.bhlangonijr.chesslib.move.Move;
import com.github.bhlangonijr.chesslib.Board;
import com.github.bhlangonijr.chesslib.Side;

import java.lang.Math;

/**
 * The AdvancedMinimax class implements the Strategy interface and extends SimpleMinix. AdvancedMinimax provides
 * an implementation for the alpha-beta pruning algorithm. It also provides a numericalMoveCalculator method for recursively calculating the score
 * of a given board state to a specified depth, and a quiescenceSearch method to recursively
 * search for the optimal score when only captures and checks are possible.
 * @author  Marios Petrov
 * @since May 06, 2023
 */

public class AdvancedMinimax extends SimpleMinimax {

    /**
     * Constructs an AdvancedMinimax object with a depth of 4 and a MaterialEvaluator object
     * for evaluating board states.
     */
    
    public AdvancedMinimax() {
        super();
    }

    /**
     * Constructs an AdvancedMinimax object with the specified depth and a MaterialEvaluator
     * object for evaluating board states.
     * @param depth The maximum depth to search.
     */
    
    public AdvancedMinimax(int depth) {
        super(depth);
    }

    /**
     * Constructs an AdvancedMinimax object with the specified depth and BoardEvaluator object.
     * @param depth : An integer representing the maximum depth to search through
     * @param eval : A {@code BoardEvaluator} object.
     */
    public AdvancedMinimax(int depth, BoardEvaluator eval) {
        super(depth, eval);
    }

    @Override
    public Move findBestMove(Board board) {
        Move optimalMove = null;
        double optimalScore;
        Side player = board.getSideToMove();
        if (player == Side.WHITE) optimalScore = Double.NEGATIVE_INFINITY;
        else optimalScore = Double.POSITIVE_INFINITY;

        for (Move move : board.legalMoves()) {
            board.doMove(move);
            double moveEval = alphaBeta(board, this.depth - 1, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, false);
            board.undoMove();

            if ((player == Side.WHITE && moveEval > optimalScore) || (player == Side.BLACK && moveEval < optimalScore)) {
                optimalScore = moveEval;
                optimalMove = move;
            }
        }
        return optimalMove;
    }

    /**
     * Recursively calculates the score of a given board state to the specified depth
     * using the minimax algorithm.
     * @param board The board state to evaluate.
     * @param depth The maximum depth to search.
     * @param alpha : current minimum score for maximizing player
     * @param beta : current maximum score for minimizing player.
     * @return The score of the board state.
     */
    
    private double alphaBeta(Board board, int depth, double alpha, double beta, boolean isMaximizingPlayer) {
        if (depth == 0) {
            return quiescenceSearch(board, alpha, beta, isMaximizingPlayer);
        }

        if (isMaximizingPlayer) {
            double maxScore = Double.NEGATIVE_INFINITY;
            for (Move move : board.legalMoves()) {
                board.doMove(move);
                maxScore = Math.max(maxScore, alphaBeta(board, depth - 1, alpha, beta, false));
                board.undoMove();
                alpha = Math.max(alpha, maxScore);
                if (beta <= alpha) {
                    break;
                }
            }
            return maxScore;
        } else {
            double minScore = Double.POSITIVE_INFINITY;
            for (Move move : board.legalMoves()) {
                board.doMove(move);
                minScore = Math.min(minScore, alphaBeta(board, depth - 1, alpha, beta, true));
                board.undoMove();
                beta = Math.min(beta, minScore);
                if (beta <= alpha) {
                    break;
                }
            }
            return minScore;
        }
    }

    /**
     * Implementation of the quiescent search algorithm, which extends the normal minimax approach
     * if the current board state is considered volatile, as measured by alpha and beta
     * @param board : A {@code Board} object from chesslib
     * @param alpha : Double
     * @param beta : Double
     * @param isMaximizingPlayer : Boolean, representing whether the current player is the maximizing player or not (i.e.,
     * if player is playing white or not).
     * @return
     */
    private double quiescenceSearch(Board board, double alpha, double beta, boolean isMaximizingPlayer) {
        double eval = this.eval.evaluationScheme(board);

        if (isMaximizingPlayer) {
            if (eval >= beta) return beta;
            if (eval > alpha) alpha = eval;
        } else {
            if (eval <= alpha) return alpha;
            if (eval < beta) beta = eval;
        }

        for (Move move : board.legalMoves()) {
            double materialBefore = this.eval.evaluationScheme(board);
            board.doMove(move);
            double materialAfter = this.eval.evaluationScheme(board);

            // Skip non-capture moves
            if (Math.abs(materialAfter - materialBefore) < 0.1) {
                board.undoMove();
                continue;
            }

            double score = quiescenceSearch(board, alpha, beta, !isMaximizingPlayer);
            board.undoMove();

            if (isMaximizingPlayer) {
                if (score >= beta) return beta;
                if (score > alpha) alpha = score;
            } else {
                if (score <= alpha) return alpha;
                if (score < beta) beta = score;
            }
        }
        return isMaximizingPlayer ? alpha : beta;
    }
}


