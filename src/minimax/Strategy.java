package minimax;

import com.github.bhlangonijr.chesslib.move.*;
import com.github.bhlangonijr.chesslib.Board;

/**
 * Declares the key function {@code findBestMove} which is necessary to have for all AI
 * playing a game of chess
 * @author Saurav Kiri
 * @since May 04 2023
 */
public interface Strategy {
    
    /**
     * Given a particular board state, finds some best calculated move and returns the appropriate
     * {@code Move object}.
     * @param board : a {@code Board} object from the {@ceode chesslib} library.
     * @return A {@code Move} object.
     */
    public Move findBestMove(Board board);
}
