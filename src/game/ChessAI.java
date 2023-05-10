package game;

import com.github.bhlangonijr.chesslib.Board;
import com.github.bhlangonijr.chesslib.move.Move;
import minimax.SimpleMinimax;
import minimax.Strategy;

/**
 * Class for creating ChessAI objects for playing chess.
 * @author Marios Petrov
 * @since May 04, 2023
 */
public class ChessAI {
    private Strategy strategy;

    /**
     * Makes a new ChessAI instance using SimpleMinimax by default.
     */
    public ChessAI() {
        this.strategy = new SimpleMinimax();
    }

    /**
     * Makes a new ChessAI instance using the given strategy (either SimpleMinimax or AdvancedMinimax).
     * @param strategy : an object implementing the Strategy interface
     */
    public ChessAI(Strategy strategy) {
        this.strategy = strategy;
    }

    /**
     * Changes the strategy of the current ChessAI instance to the given strategy (either SimpleMinimax or AdvancedMinimax).
     * @param strategy : an object implementing the Strategy interface
     */
    public void setStrategy(Strategy strategy) {
        this.strategy = strategy;
    }

    /**
     * Finds the best move based on the current strategy set for the AI.
     * @param board : A {@code board} object
     * @return : the best move for the current player
     */
    public Move findBestMove(Board board) {
        return strategy.findBestMove(board);
    }
}
