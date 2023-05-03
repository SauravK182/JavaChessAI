package minimax;

import com.github.bhlangonijr.chesslib.Board;

/**
 * The BoardEvaluator interface defines the core method `evaluationScheme` which must be present
 * in any evaluation function used for the minimax algorithm.
 * @author Saurav Kiri
 * @since May 03, 2023
 * 
 */
public interface BoardEvaluator {

    /**
     * Returns a numerical evaluation of a chess board given a Board object from the chesslib library.
     * @param board : A Board object from the chesslib library
     * @return a double (or compatible type) representing the evaluation.
     * Positive values represent an advantage for white, negative values represent
     * an advantage for black.
     */
    double evaluationScheme(Board board);
}
