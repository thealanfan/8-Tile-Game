/* ------------------------------------------------
 * 8 Tiles UI Program
 *
 * Class: CS 342, Fall 2016
 * System: Windows 10, IntelliJ IDE
 * Author Code Word: CIAO
 * -------------------------------------------------
 */


/* ------------------------------------------------
 * TILESGUIDRIVER CLASS for the program, contains
 * only the main method to drive the program.
 *
 *  Main creates a TilesGUI instance, and gives
 *  control over to the event-driven queue.
 * -------------------------------------------------
 */

import javax.swing.*;

public class TilesGUIDriver {


    public static void main(String[] args)
    {
        TilesGUI gameGui = new TilesGUI();
        gameGui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameGui.setSize(500, 500);
        gameGui.setLocationRelativeTo(null); //centered
        gameGui.setVisible(true);
        JOptionPane.showMessageDialog(null, GameConstants.WELCOME_PROMPT);
    }



}
