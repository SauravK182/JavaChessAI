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

    private class PositionEval implements Comparable<PositionEval> {
        private double moveScore;
        private Move move;

        public PositionEval(double moveScore, Move move) {
            this.moveScore = moveScore;
            this.move = move;
        }
        
        public double getMoveScore() {
            return this.moveScore;
        }

        public Move getMove() {
            return this.move;
        }

        public int compareTo(PositionEval o2) {
            if (this.moveScore < o2.moveScore) {
                return -1;
            } else if (this.moveScore > o2.moveScore) {
                return 1;
            } else {
                return 0;
            }
        }

        public static PositionEval min(PositionEval e1, PositionEval e2) {
            if (e1.compareTo(e2) < 0) {
                return e1;
            }
            return e2;
        }

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
