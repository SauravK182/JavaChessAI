package game;

import com.github.bhlangonijr.chesslib.Board;
import com.github.bhlangonijr.chesslib.move.Move;
import com.github.bhlangonijr.chesslib.Side;
import gui.BoardGUI;
import minimax.*;

import java.util.Scanner;

/**
 * Main driver class for playing a chess game with an AI.
 * @author Marios Petrov and Saurav Kiri
 * @since May 08 2023
 */
public class ChessGame {
    private Board board;
    private Strategy ai;

    /**
     * Constructs a chess game object, which possesses the main driver play function
     * that initiates a chess game with the computer.
     * @param ai : An object of type {@code Strategy}, which has some move finder function to act as the computer.
     */
    public ChessGame(Strategy ai) {
        this.board = new Board();
        this.ai = ai;
    }

    /**
     * Main function through which a chess game will be played.
     * Given a user side, the user will type moves in the terminal and play back-and-forth with an AI
     * until an end state is reached (checkmate/stalement), or the user quits, which can be done
     * by closing the GUI at any point during the game.
     * @param userSide : a {@code Side} object from the {@code chesslib} library, which represents
     * which side the user will play as (should be {@code Side.WHITE} or {@code Side.BLACK})
     */
    public void play(Side userSide) {
        Scanner scanner = new Scanner(System.in);
        BoardGUI.drawBoard(board);

        while (!board.isMated() && !board.isDraw()) {
            if (board.getSideToMove() == userSide) {
                if (board.isKingAttacked()) {
                    System.out.println("Heads up! You're in check!");
                }
                System.out.print("Enter your move: ");
                /* convert to format such as: a2a4
                 * remove all whitespace by matching the regex whitespace char \s
                 * convert string to lower
                 */
                String move = scanner.nextLine().toLowerCase().replaceAll("\\s", "");
                while (! board.legalMoves().contains(new Move(move, userSide))) {
                    // query user for moves until they provide a legal move
                    System.out.print("Sorry, I was unable to parse your move, or it was illegal. Please try again:");
                    move = scanner.nextLine().toLowerCase().replaceAll("\\s", "");
                }
                try {
                    board.doMove(move);
                    BoardGUI.drawBoard(board);
                } catch (Exception e) {
                    System.out.println("Invalid move. Try again.");
                }
            } else {
                System.out.println("The opponent is thinking...");
                Move aiMove = ai.findBestMove(board);
                board.doMove(aiMove);
                System.out.println("The opponent has moved: " + aiMove.toString());
                BoardGUI.drawBoard(board);
            }
        }

        if (board.isMated()) {
            System.out.println("Checkmate! " + board.getSideToMove().flip() + " wins!");
        } else {
            System.out.println("It's a draw!");
        }

        scanner.close();
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("To input a move, use the following format: e2e4");
        System.out.println("(Press any key to begin)");
        scanner.nextLine();

        System.out.print("Choose AI difficulty (0 for easy, 1 for hard): ");
        int aiDifficulty = scanner.nextInt();

        System.out.print("Choose your side (white or black): ");
        String side = scanner.next().toLowerCase();
        Side userSide = side.equals("white") ? Side.WHITE : Side.BLACK;

        Strategy ai;
        if (aiDifficulty == 0) {
            ai = new SimpleMinimax();
        } else {
            ai = new AdvancedMinimax();
        }

        ChessGame game = new ChessGame(ai);
        game.play(userSide);

        scanner.close();
    }
}
