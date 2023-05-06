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
public class SimpleMinimaxImproved implements Strategy {
    private int depth;
    private BoardEvaluator eval;

    /**
     * Makes a new default Minimax instance with a depth of 10 and
     * the basic {@code MaterialEvaluator}
     */
    public SimpleMinimaxImproved() {
        this(4, new MaterialEvaluator());
    }

    /**
     * Makes a new Minimax instance with a depth of {@code depth} and
     * the basic {@code MaterialEvaluator}
     * @param depth : integer, representing the number of moves into the future to look into
     */
    public SimpleMinimaxImproved(int depth) {
        this(depth, new MaterialEvaluator());
    }

    /**
     * Makes a new Minimax instance with a depth of {@code depth} and some BoardEvaluator {@code eval}
     * @param depth : integer, representing number of moves into the future to look into
     * @param eval : an object of class {@code BoardEvaluator}
     */
    public SimpleMinimaxImproved(int depth, BoardEvaluator eval) {
        if (depth < 1) {
            System.out.println("Invalid depth given. Setting default depth (10).");
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

    @Override
    public Move findBestMove(Board board) {
        return numericalMoveCalculator(board, this.depth).getMove();
    }

    private PositionEval numericalMoveCalculator(Board board, int depth) {
        if (depth == 0) {
            return new PositionEval(this.eval.evaluationScheme(board), null);
        }

        if (board.getSideToMove() == Side.WHITE) {
            PositionEval maxMove = new PositionEval(Double.NEGATIVE_INFINITY, null);
            for (Move move: board.legalMoves()) {
                board.doMove(move);
                maxMove = PositionEval.max(new PositionEval(numericalMoveCalculator(board, depth - 1).getMoveScore(), move), maxMove);
                board.undoMove();
            }
            return maxMove;
        
        } else {
            PositionEval minMove = new PositionEval(Double.POSITIVE_INFINITY, null);
            for (Move move: board.legalMoves()) {
                board.doMove(move);
                minMove = PositionEval.min(new PositionEval(numericalMoveCalculator(board, depth - 1).getMoveScore(), move), minMove);
                board.undoMove();
            }
            return minMove;
        }
    }

    /*
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
            board.undoMove();
            if ((player == Side.WHITE && moveEval > optimalScore) || (player == Side.BLACK && moveEval < optimalScore)) {
                optimalScore = moveEval;
                optimalMove = move;
            }
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
    */
}
