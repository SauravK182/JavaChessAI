package gui;
import java.awt.*;
import javax.swing.*;

import java.util.HashMap;

import com.github.bhlangonijr.chesslib.Board;


public class BoardGUI {

    private static JFrame frame;

    private static void initializeFrame() {
        frame = new JFrame("Chess Board");
        frame.setVisible(false);
        frame.setLayout(new GridLayout(8, 8));
        frame.setSize(500, 500);
        frame.setLocationRelativeTo(null);

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

    private static ImageIcon scaleDownImage(String filepath) {
        return new ImageIcon(new ImageIcon(filepath).getImage().getScaledInstance(60, 60, Image.SCALE_DEFAULT));
    }

    public static void drawBoard(Board board) {
        initializeFrame();
        HashMap<String, String> pieces = ChessImages.getPieces();

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
                String position = Character.toString(rank.charAt(j));
                if (pieces.containsKey(position)) {
                    ImageIcon piece = scaleDownImage(pieces.get(position));
                    gridPos.add(new JLabel(piece));
                }

                // add panel to frame
                frame.add(gridPos);
                frame.pack();
            }
        }
        frame.setVisible(true);
    }    
}
