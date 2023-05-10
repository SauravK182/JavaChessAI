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
    protected int depth;
    protected BoardEvaluator eval;

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
            System.out.println("Invalid depth given. Setting default depth (4).");
            this.depth = 4;
        } else {
            this.depth = depth;
        }
        this.eval = eval;
    }
    
    /**
     * Inner class that stores both a move and its score as calculated by the Minimax
     * algorithm. Implements the {@code Comparable} interface by comparing move scores.
     */
    private class PositionEval implements Comparable<PositionEval> {
        private double moveScore;
        private Move move;

        /**
         * Constructs a {@code PositionEval} object based on a {@code Move} object
         * and its Minimax score.
         * @param moveScore : Numerical score computed by, e.g., minimax
         * @param move : A {@code move} object from the chesslib library.
         */
        public PositionEval(double moveScore, Move move) {
            this.moveScore = moveScore;
            this.move = move;
        }
        
        /**
         * Returns the move score for the current {@code PositionEval} object.
         * @return : A double, representing the move score.
         */
        public double getMoveScore() {
            return this.moveScore;
        }

        /**
         * Returns the {@code Move} object associated with this {@code PositionEval} object.
         * @return : A {@code Move} object.
         */
        public Move getMove() {
            return this.move;
        }

        /**
         * Compares two {@code PositionEval} objects based on their move score.
         * @return : Returns 1 if the current object has a higher score than {@code e2},
         * -1 if the current object has a lower score than {@code e2},
         * or 0 if they have an equivalent score.
         */
        @Override
        public int compareTo(PositionEval o2) {
            if (this.moveScore < o2.moveScore) {
                return -1;
            } else if (this.moveScore > o2.moveScore) {
                return 1;
            } else {
                return 0;
            }
        }

        /**
         * Using the implemented {@code compareTo} method from the {@code Comparable} interface,
         * finds the {@code PositionEval} object which has the minimum score.
         * @param e1 : A {@code PositionEval} object to compare to {@code e2}.
         * @param e2 : A {@code PositionEval} object
         * @return : Will return {@code e1} if the score for {@code e1} is strictly less than {@code e2},
         * otherwise, simply returns {@code e2}.
         */
        public static PositionEval min(PositionEval e1, PositionEval e2) {
            if (e1.compareTo(e2) < 0) {
                return e1;
            }
            return e2;
        }

        /**
         * Using the implemented {@code compareTo} method from the {@code Comparable} interface,
         * finds the {@code PositionEval} object which has the maximum score.
         * @param e1 : A {@code PositionEval} object to compare to {@code e2}
         * @param e2 : A {@code PositionEval} object.
         * @return : Returns {@code e1} if the score associated with {@code e1} is strictly greater
         * than the score for {@code e2}, otherwise returns {@code e2}.
         */
        public static PositionEval max(PositionEval e1, PositionEval e2) {
            if (e1.compareTo(e2) > 0) {
                return e1;
            }
            return e2;
        }
    }

    /**
     * Finds the best move based on the minimax algorithm at the {@code depth} specified in this
     * {@code Minimax} object.
     * @param board : A {@code board} object
     */

    @Override
    public Move findBestMove(Board board) {
        return numericalMoveCalculator(board, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, this.depth).getMove();
    }

    /**
     * Calculates the evaluation for a particular move path based on minimax with alpha-beta pruning
     * @param board : a {@code Board} object from chesslib
     * @param alpha : maximum score for maximizing player
     * @param beta : minimum score for minimizing player
     * @param depth : number of moves at which to look into the future.
     * @return : an object of {@code PositionEval} which stores move and eval.
     */
    private PositionEval numericalMoveCalculator(Board board, double alpha, double beta, int depth) {
        if (depth == 0) {
            return new PositionEval(this.eval.evaluationScheme(board), null);
        }

        if (board.getSideToMove() == Side.WHITE) {
            PositionEval maxMove = new PositionEval(Double.NEGATIVE_INFINITY, null);
            for (Move move: board.legalMoves()) {
                board.doMove(move);
                double eval = numericalMoveCalculator(board, alpha, beta, depth - 1).getMoveScore();
                board.undoMove();
                maxMove = PositionEval.max(new PositionEval(eval, move), maxMove);
                alpha = Math.max(alpha, eval);
                if (beta <= alpha) {
                    break;
                }
            }
            return maxMove;
        
        } else {
            PositionEval minMove = new PositionEval(Double.POSITIVE_INFINITY, null);
            for (Move move: board.legalMoves()) {
                board.doMove(move);
                double eval = numericalMoveCalculator(board, alpha, beta, depth - 1).getMoveScore();
                board.undoMove();
                minMove = PositionEval.min(new PositionEval(eval, move), minMove);
                beta = Math.min(beta, eval);
                if (beta <= alpha) {
                    break;
                }
            }
            return minMove;
        }
    }
}
