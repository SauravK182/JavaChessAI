package minimax;

import com.github.bhlangonijr.chesslib.move.Move;
import com.github.bhlangonijr.chesslib.Board;
import com.github.bhlangonijr.chesslib.Side;

import java.lang.Math;
import java.util.*;
import java.util.stream.*;

/**
 * The AdvancedMinimax class implements the Strategy interface and provides
 * an implementation for the findBestMove method using the alpha-beta pruning algorithm.
 * It also provides a numericalMoveCalculator method for recursively calculating the score
 * of a given board state to a specified depth, and a quiescenceSearch method to recursively
 * search for the optimal score when only captures and checks are possible.
 *
 * @author  Marios Petrov
 * @since May 06, 2023
 */

public class AdvancedMinimax implements Strategy
{
    private int depth;
    private BoardEvaluator eval;

    /**
     * Constructs an AdvancedMinimax object with a depth of 4 and a MaterialEvaluator object
     * for evaluating board states.
     */
    public AdvancedMinimax()
    {
        this(4, new MaterialEvaluator());
    }

    /**
     * Constructs an AdvancedMinimax object with the specified depth and a MaterialEvaluator
     * object for evaluating board states.
     * @param depth The maximum depth to search.
     */
    public AdvancedMinimax(int depth)
    {
        this(depth, new MaterialEvaluator());
    }

    public AdvancedMinimax(int depth, BoardEvaluator eval)
    {
        if (depth < 1)
        {
            System.out.println("Invalid depth given. Setting default depth (10).");
            this.depth = 4;
        }

        else
        {
            this.depth = depth;
        }
        this.eval = eval;
    }

    /**
     * Recursively calculates the score of a given board state to the specified depth
     * using the minimax algorithm.
     * @param board The board state to evaluate.
     * @param depth The maximum depth to search.
     * @return The score of the board state.
     */
    private double numericalMoveCalculator(Board board, int depth)
    {
        if (depth == 0)
        {
            return this.eval.evaluationScheme(board);
        }

        if (board.getSideToMove() == Side.WHITE)
        {
            double maxScore = Double.NEGATIVE_INFINITY;
            for (Move move: board.legalMoves())
            {
                board.doMove(move);
                maxScore = Math.max(maxScore, numericalMoveCalculator(board, depth - 1));
                board.undoMove();
            }
            return maxScore;

        }

        else
        {
            double minScore = Double.POSITIVE_INFINITY;
            for (Move move: board.legalMoves())
            {
                board.doMove(move);
                minScore = Math.min(minScore, numericalMoveCalculator(board, depth - 1));
                board.undoMove();
            }
            return minScore;
        }
    }

    /**
     * Finds the best move for the current player using the alpha-beta pruning algorithm.
     * @param board The current board state.
     * @return The best move to make.
     */
    @Override
    public Move findBestMove(Board board)
    {
        // initialize optimal moves
        Move optimalMove = null;
        double optimalScore;
        Side player = board.getSideToMove();
        if (player == Side.WHITE) optimalScore = Double.NEGATIVE_INFINITY; else optimalScore = Double.POSITIVE_INFINITY;

        for (Move move: board.legalMoves())
        {
            board.doMove(move);
            double moveEval = numericalMoveCalculator(board, this.depth - 1);
            board.undoMove();
            if ((player == Side.WHITE && moveEval > optimalScore) || (player == Side.BLACK && moveEval < optimalScore))
            {
                optimalScore = moveEval;
                optimalMove = move;
            }
        }
        return alphaBeta(board, this.depth, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, true).getMove();
    }


    /**
     * Implements the alpha-beta pruning algorithm to find the best move for the current player.
     * @param board The current board state.
     * @param depth The maximum depth to search.
     * @param alpha The alpha value for pruning.
     * @param beta The beta value for pruning.
     * @param maximizingPlayer Whether the current player is maximizing or minimizing.
     * @return A MoveScore object containing the score and optimal move.
     */
    private MoveScore alphaBeta(Board board, int depth, double alpha, double beta, boolean maximizingPlayer)
    {
        if (depth == 0)
        {
            return new MoveScore(quiescenceSearch(board, alpha, beta, maximizingPlayer), null);
        }

        Move optimalMove = null;
        List<Move> legalMoves = board.legalMoves();
        if (maximizingPlayer)
        {
            double maxScore = Double.NEGATIVE_INFINITY;
            for (Move move : legalMoves)
            {
                board.doMove(move);
                double score = alphaBeta(board, depth - 1, alpha, beta, false).getScore();
                board.undoMove();

                if (score > maxScore)
                {
                    maxScore = score;
                    optimalMove = move;
                }

                alpha = Math.max(alpha, maxScore);
                if (alpha >= beta)
                {
                    break;
                }
            }
            return new MoveScore(maxScore, optimalMove);
        }

        else
        {
            double minScore = Double.POSITIVE_INFINITY;
            for (Move move : legalMoves)
            {
                board.doMove(move);
                double score = alphaBeta(board, depth - 1, alpha, beta, true).getScore();
                board.undoMove();

                if (score < minScore)
                {
                    minScore = score;
                    optimalMove = move;
                }

                beta = Math.min(beta, minScore);
                if (alpha >= beta)
                {
                    break;
                }
            }
            return new MoveScore(minScore, optimalMove);
        }
    }

    /**
     * Recursively searches for the optimal score when only captures and checks are possible.
     * @param board The current board state.
     * @param alpha The alpha value for pruning.
     * @param beta The beta value for pruning.
     * @param maximizingPlayer Whether the current player is maximizing or minimizing.
     * @return The optimal score.
     */
    private double quiescenceSearch(Board board, double alpha, double beta, boolean maximizingPlayer)
    {
        if (board.isMated() || board.isDraw())
        {
            return this.eval.evaluationScheme(board);
        }

        double standPat = this.eval.evaluationScheme(board);
        if (maximizingPlayer)
        {
            if (standPat >= beta)
            {
                return beta;
            }

            if (alpha < standPat)
            {
                alpha = standPat;
            }
        }

        else
        {
            if (standPat <= alpha)
            {
                return alpha;
            }

            if (beta > standPat)
            {
                beta = standPat;
            }
        }

        List<Move> captureMoves = board.legalMoves().stream()
                .filter(move -> board.getPiece(move.getTo()) != null) // check if move is a capture
                .sorted()
                .collect(Collectors.toList());

        for (Move move : captureMoves)
        {
            board.doMove(move);
            double score = -quiescenceSearch(board, -beta, -alpha, !maximizingPlayer);
            board.undoMove();

            if (maximizingPlayer)
            {
                if (score >= beta)
                {
                    return beta;
                }

                if (score > alpha)
                {
                    alpha = score;
                }
            }

            else
            {
                if (score <= alpha)
                {
                    return alpha;
                }

                if (score < beta)
                {
                    beta = score;
                }
            }
        }

        return maximizingPlayer ? alpha : beta;
    }

    /**
     * The MoveScore class represents a move and its corresponding score.
     */
    private static class MoveScore
    {
        private final double score;
        private final Move move;

        /**
         * Constructs a MoveScore object with the given score and move.
         * @param score The score of the move.
         * @param move The move.
         */
        public MoveScore(double score, Move move)
        {
            this.score = score;
            this.move = move;
        }

        /**
         * Gets the score of the move.
         * @return The score of the move.
         */
        public double getScore()
        {
            return score;
        }

        /**
         * Gets the move.
         * @return The move.
         */
        public Move getMove()
        {
            return move;
        }
    }

    /**
     * A simple main method for testing the AdvancedMinimax class.
     * @param args The command line arguments.
     */
    public static void main(String[] args)
    {
        SimpleMinimax test = new SimpleMinimax(4);
        Board board = new Board();
        System.out.println(test.findBestMove(board).toString());

        board.doMove("g1h3");
        System.out.println(test.findBestMove(board));
        System.out.println(board.legalMoves().toString());
    }
}



