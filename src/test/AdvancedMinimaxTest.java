package test;

import com.github.bhlangonijr.chesslib.Board;
import com.github.bhlangonijr.chesslib.move.Move;
import com.github.bhlangonijr.chesslib.move.MoveException;

import gui.BoardGUI;
import minimax.AdvancedMinimax;
import minimax.MaterialEvaluator;

/**
 * Test class for AdvancedMinimax AI.
 * 
 * @author Marios Petrov
 * @since 05/06/2023
 */
public class AdvancedMinimaxTest 
{

    /**
     * Main method to run the AdvancedMinimax test.
     * 
     * @param args command line arguments
     */
    public static void main(String[] args) 
    {
        Board board = new Board();
        AdvancedMinimax minimax = new AdvancedMinimax(4, new MaterialEvaluator());

        // Play 10 moves for each side
        for (int i = 0; i < 10; i++) 
        {
            System.out.println("Move #" + (2 * i + 1));
            
            try 
            {
                Move whiteMove = minimax.findBestMove(board);
                board.doMove(whiteMove);
                BoardGUI.drawBoard(board);
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
                BoardGUI.drawBoard(board);
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
