package minimax;

import com.github.bhlangonijr.chesslib.move.Move;
import com.github.bhlangonijr.chesslib.Board;
import com.github.bhlangonijr.chesslib.Side;

import java.lang.Math;


public class AdvancedMinimax extends SimpleMinimax {

    public AdvancedMinimax() {
        super();
    }

    public AdvancedMinimax(int depth) {
        super(depth);
    }

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


