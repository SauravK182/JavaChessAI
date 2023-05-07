package test;

import com.github.bhlangonijr.chesslib.*;

import minimax.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


/**
 * Testing the basic behaviors of the Minimax algorithms, mostly ensuring
 * that they do not alter board state and behave as predicted
 * under forcing circumstances.
 * @author Saurav Kiri
 * @since May 06 2023
 */
public class TestMinimax {
    Board board;
    SimpleMinimax mini;
    AdvancedMinimax advMini;

    /**
     * Sets up test environment before each run.
     */
    @BeforeEach
    void setUp() {
        board = new Board();
        mini = new SimpleMinimax();
        advMini = new AdvancedMinimax();
    }

    /**
     * Checks to ensure that the SimpleMinimax algorithm doesn't change board state.
     * I.e., checks that it properly undoes all moves when looking into the future.
     */
    @Test
    void testMinimaxPreservesBoard() {
        Board board2 = new Board();
        mini.findBestMove(board);
        Assertions.assertTrue(board.equals(board2));
    }

    /**
     * Checks to ensure that the AdvancedMinimax algorithm doesn't change board state.
     * I.e., that it properly undoes all performed moves.
     */
    @Test
    void testAdvMiniPreservesBoard() {
        Board board2 = new Board();
        advMini.findBestMove(board);
        Assertions.assertTrue(board.equals(board2));
    }

    /**
     * Tests to ensure that the Minimax algos can find mate in 1.
     */
    @Test
    void testFindMate() {
        /* this fen represents mate in 1 with Rook d8*/
        String fen = "6k1/5ppp/p7/P7/5b2/7P/1r3PP1/3R2K1 w - - 0 1";
        board.loadFromFen(fen);
        board.doMove(mini.findBestMove(board));
        Assertions.assertTrue(board.isMated());

        board.undoMove();
        Assertions.assertFalse(board.isMated());
        board.doMove(advMini.findBestMove(board));
        Assertions.assertTrue(board.isMated());
    }

    /**
     * Another test for finding mate in 1
     */
    @Test
    void testFindMate2() {
        double start, stop, time;
        /* fen for mate in 1 with queen captures f7*/
        String fen = "r1bqkb1r/pppp1ppp/2n2n2/3Q4/2B1P3/8/PB3PPP/RN2K1NR w KQkq - 0 1";
        board.loadFromFen(fen);
        start = System.nanoTime();
        board.doMove(mini.findBestMove(board));
        stop = System.nanoTime();
        time = (stop - start) / 1e9;
        System.out.println("Seconds taken for simple minimax: " + String.format("%, .2f", time));
        Assertions.assertTrue(board.isMated());

        board.undoMove();
        Assertions.assertFalse(board.isMated());

        start = System.nanoTime();
        board.doMove(advMini.findBestMove(board));
        stop = System.nanoTime();
        time = (stop - start) / 1e9;
        System.out.println("Seconds taken for advanced minimax: " + String.format("%, .2f", time));
        Assertions.assertTrue(board.isMated());
    }
}
