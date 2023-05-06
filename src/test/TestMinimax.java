package test;

import com.github.bhlangonijr.chesslib.*;

import minimax.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestMinimax {
    Board board;
    SimpleMinimax mini;

    @BeforeEach
    void setUp() {
        board = new Board();
        mini = new SimpleMinimax();
    }

    @Test
    void testMinimaxPreservesBoard() {
        Board board2 = new Board();
        mini.findBestMove(board);
        Assertions.assertTrue(board.equals(board2));
    }

    @Test
    void 
}
