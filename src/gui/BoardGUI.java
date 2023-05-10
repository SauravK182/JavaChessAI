package gui;
import java.awt.*;
import java.net.URL;

import javax.swing.*;

import com.github.bhlangonijr.chesslib.Board;


/**
 * Class designed to draw the actual chess board given some particular board state.
 * @author Saurav Kiri
 * @since May 01 2023
 */
public class BoardGUI {

    private static JFrame frame;
    private static String[] files = {"a", "b", "c", "d", "e", "f", "g", "h"};

    /**
     * Initializes the {@code JFrame} with an 8x8 {@code GridLayout} for drawing a chessboard.
     * Will also set the size to be 540x540 and adds a confirm dialog box to closing the window,
     * which upon confirming, will exit the program.
     */
    private static void initializeFrame() {
        frame = new JFrame("Chess Board");
        frame.setVisible(false);
        frame.setLayout(new GridLayout(8, 8));
        frame.setSize(540, 540);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        /* print a confirmation window for closing the GUI - code from:
         * https://stackoverflow.com/questions/9093448/
         */
        frame.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                if (JOptionPane.showConfirmDialog(frame, 
                                                  "Are you sure you want to close this window? It will terminate the program.",
                                                  "Close Window", JOptionPane.YES_NO_OPTION,
                                                  JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
                                                    System.exit(0);
                                                  }
            }
        });
        
    }

    /**
     * Scales down an image to fit on a 540x540 pixel JFrame chessboard, given its filepath.
     * @param filepath : Path to a valid file.
     * @return : Scaled down image icon (60x60 pixels) that can be used on a {@code JLabel}
     */
    private static ImageIcon scaleDownImage(String filepath) {
        /*
         * Use a URL object to get a link to a resource (in /resources) on the classpath for the image
         * This allows the jar to be standalone from the images so the two don't need to be coupled
         * See https://stackoverflow.com/questions/23628914/
         * https://stackoverflow.com/questions/1096398/
         * https://stackoverflow.com/questions/12488836/
         */
        URL url = BoardGUI.class.getResource(filepath);
        return new ImageIcon(new ImageIcon(url).getImage().getScaledInstance(60, 60, Image.SCALE_DEFAULT));
    }

    /**
     * Draws the current state of a {@code Board} object using Swing, with an 8x8 gridded JFrame
     * and using the pre-defined chess piece .pngs included in this project.
     * @param board : a {@code Board} object from the {@code chesslib} library.
     */
    public static void drawBoard(Board board) {
        if (frame != null) {
            frame.dispose(); // close frame when re-drawing a new one
        }

        initializeFrame();

        /* isolate only the pieces of the board
         * split string based on newlines to recover board positioning
         */
        String[] boardArray = board.toString().replaceAll("Side:.*", "").split("\n");
        
        // build board with JPanels and current state of pieces
        for (int i = 0; i < 8; i++) {
            String rank = boardArray[i];
            for (int j = 0; j < 8; j++) {
                JPanel gridPos = new JPanel();

                // set color
                if ((i + j) % 2 == 0) {
                    gridPos.setBackground(new Color(238, 238, 210, 255));
                } else {
                    gridPos.setBackground(new Color(102, 51, 0, 255));
                }

                // place piece, if one is present
                JLabel lab;
                String position = Character.toString(rank.charAt(j));
                if (ChessImages.PIECES.containsKey(position)) {
                    ImageIcon piece = scaleDownImage(ChessImages.PIECES.get(position));
                    lab = new JLabel(piece, SwingConstants.LEADING);                 
                } else {
                    lab = new JLabel("", SwingConstants.LEADING);
                    gridPos.setLayout(new BorderLayout());
                }
                
                // configure text alignment for JLabel to bottom left to add text
                // see http://www.java2s.com/Code/Java/Swing-JFC/LabelTextPosition.htm
                lab.setHorizontalTextPosition(SwingConstants.LEFT);
                lab.setVerticalTextPosition(SwingConstants.BOTTOM);

                // include rank and file on the ends of the board for increased user accessibility
                if (j == 0 && i != 7) {
                    lab.setText(Integer.toString(8 - i));
                } else if (i == 7 && j != 0) {
                    lab.setText(files[j].toUpperCase());
                } else if (i == 7 && j == 0) {
                    lab.setText("A1");
                }
                
                // add JLabel to JPanel
                gridPos.add(lab, BorderLayout.PAGE_END);

                // add panel to frame
                frame.add(gridPos);
                frame.pack();
            }
        }
        frame.setVisible(true);
    }    
}
