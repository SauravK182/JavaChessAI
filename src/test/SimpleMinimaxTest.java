package test;

import com.github.bhlangonijr.chesslib.Board;
import com.github.bhlangonijr.chesslib.move.Move;
import com.github.bhlangonijr.chesslib.move.MoveException;

import minimax.SimpleMinimax;
import minimax.MaterialEvaluator;

/**
 * Test class for SimpleMinimax AI.
 *
 * @author Marios Petrov
 * @since 05/06/2023
 */
public class SimpleMinimaxTest 
{

    /**
     * Main method to run the SimpleMinimax test.
     *
     * @param args command line arguments
     * @throws InterruptedException if the sleep operation is interrupted
     */
    public static void main(String[] args) throws InterruptedException 
    {
        testSimpleMinimax();
    }

    /**
     * Method to test SimpleMinimax AI.
     *
     * @throws InterruptedException if the sleep operation is interrupted
     */
    public static void testSimpleMinimax() throws InterruptedException 
    {
        Board board = new Board();
        SimpleMinimax minimax = new SimpleMinimax(4, new MaterialEvaluator());

        // Play 10 moves for each side
        for (int i = 0; i < 10; i++) 
        {
            System.out.println("Move #" + (2 * i + 1));
            
            try 
            {
                Move whiteMove = minimax.findBestMove(board);
                board.doMove(whiteMove);
                gui.BoardGUI.drawBoard(board);
                Thread.sleep(2000);
                System.out.println("White move: " + whiteMove);
                System.out.println(board.toString());
            } 
            
            catch (MoveException e) 
            {
                System.out.println("Error executing white move: " + e.getMessage());
            }

            System.out.println("Move #" + (2 * i + 2));
            
            try 
            {
                Move blackMove = minimax.findBestMove(board);
                board.doMove(blackMove);
                gui.BoardGUI.drawBoard(board);
                Thread.sleep(2000);
                System.out.println("Black move: " + blackMove);
                System.out.println(board.toString());
            } 
            
            catch (MoveException e) 
            {
                System.out.println("Error executing black move: " + e.getMessage());
            }
        }
    }
}
