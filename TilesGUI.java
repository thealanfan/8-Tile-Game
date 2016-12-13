/* ------------------------------------------------
 * 8 Tiles UI Program
 *
 * Class: CS 342, Fall 2016
 * System: Windows 10, IntelliJ IDE
 * Author Code Word: CIAO
 * -------------------------------------------------
 */


/* ------------------------------------------------
 * TILESGUI CLASS for the program that controls the
 * event-driven functionality of the GUI as well as
 * the design aspects.
 *
 * Most notable aspect: the layout is 2 GRIDLAYOUTS
 * withint a BORDERLAYOUT.
 * -------------------------------------------------
 */


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class TilesGUI extends JFrame {

    private JButton tiles[];
    private Board gameBoard;
    private boolean settingBoardMode; //flag for setting the board
    private int numberOfTilesSet; //counter for setting the board
    private ArrayList<Integer> setOfPlacedTiles; //tiles that have already been set
    private Node autoSolveResult; //either the solution board or a closet board
    private boolean gameSolved; //flag for whether the game is solved or not

    //constructor
    public TilesGUI()
    {
        super("8 Tiles Game");

        //set the class member variables
        gameBoard = new Board();
        numberOfTilesSet = 0;
        setOfPlacedTiles =new ArrayList<>();
        settingBoardMode = false;
        gameSolved = false;

        //set the design GUI
        Container pane = getContentPane();
        pane.setLayout(new BorderLayout(0, 10));
        pane.add(getHeaderArea(), BorderLayout.NORTH);
        pane.add(getButtonArea(), BorderLayout.CENTER);
        pane.setBackground(new Color(174, 175, 175));
    }


    //returns the SET BOARD, AUTO-SOLVE, and EXIT button layout
    protected JComponent getHeaderArea()
    {
        JPanel header = new JPanel();
        header.setLayout(new GridLayout(1, 3, 5, 10));
        header.setBackground(new Color(174, 175, 175));

        //SET BOARD button initialization
        JButton setBoardButton = new JButton("Set Board");
        setBoardButton.setFont(new Font("Arial", Font.PLAIN, 18));
        setBoardButton.setBackground(new Color(13, 96, 104));
        setBoardButton.setForeground(Color.WHITE);
        setBoardButton.addActionListener(new SetBoardEventHandler());
        header.add(setBoardButton);

        //AUTO-SOLVE button initialization
        JButton autoSolveButton = new JButton("Auto-Solve");
        autoSolveButton.setFont(new Font("Arial", Font.PLAIN, 18));
        autoSolveButton.setBackground(new Color(13, 96, 104));
        autoSolveButton.setForeground(Color.WHITE);
        autoSolveButton.addActionListener(new AutoSolveEventHandler());
        header.add(autoSolveButton);

        //EXIT button initialization
        JButton exitButton = new JButton("Exit");
        exitButton.setFont(new Font("Arial", Font.PLAIN, 18));
        exitButton.setBackground(new Color(13, 96, 104));
        exitButton.setForeground(Color.WHITE);
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                JOptionPane.showMessageDialog(null, "Goodbye");
                System.exit(0);
            }
        });
        header.add(exitButton);

        return header;
    }

    //returns the TILES layout
    protected JComponent getButtonArea()
    {
        JPanel buttonArea = new JPanel();
        buttonArea.setLayout(new GridLayout(3, 3, 10, 10));
        buttonArea.setBackground(new Color(174, 175, 175));

        tiles = new JButton[GameConstants.BOARD_SIZE];
        String boardAsString = gameBoard.toString();

        //initializing each separate TILE based on random board
        for (int i = 0; i < GameConstants.BOARD_SIZE; i++) {
            String s = String.valueOf(boardAsString.charAt(i));
            if (s.equals(" ")) {
                tiles[i] = new JButton("");
                tiles[i].setBackground(new Color(174, 175, 175));
            }
            else {
                tiles[i] = new JButton(String.valueOf(boardAsString.charAt(i)));
                tiles[i].setBackground(new Color(72, 195, 206));
            }
            tiles[i].setFont(new Font("Sans Serif", Font.BOLD + Font.ITALIC, 40));
            tiles[i].setForeground(Color.WHITE);
            tiles[i].addActionListener(new TileEventHandler(i));
            buttonArea.add(tiles[i]);
        }

        return buttonArea;
    }

    public void lockTiles() //locks the TILES during auto-solver
    {
        for (int i = 0; i < GameConstants.BOARD_SIZE; i++)
            tiles[i].setEnabled(false);
    }

    public void unlockTiles() //unlocks the TILES after auto-solver
    {
        for (int i = 0; i < GameConstants.BOARD_SIZE; i++)
            tiles[i].setEnabled(true);
    }

    //TILE button click-event handler
    private class TileEventHandler implements ActionListener
    {
        int index; //index of tile for reference

        public TileEventHandler(int i)
        {
            this.index = i;
        }

        public void actionPerformed(ActionEvent event)
        {
            //regular click-play
            if (!settingBoardMode) {
                int pieceToMove = gameBoard.getPieceAt(index);
                if (gameBoard.validMoveForPiece(pieceToMove)) {
                    int spaceIndex = gameBoard.getIndexOfSpace();
                    gameBoard.swap(0, pieceToMove);
                    tiles[index].setText("");
                    tiles[index].setBackground(new Color(174, 175, 175));
                    tiles[spaceIndex].setText(Integer.toString(pieceToMove));
                    tiles[spaceIndex].setBackground(new Color(72, 195, 206));
                } else {
                    JOptionPane.showMessageDialog(null, "That is not a valid move");
                }
            }
            //Setting the board [ settingBoardMode == TRUE ]
            else {
                //already set tile
                if (setOfPlacedTiles.contains(index)) {
                    JOptionPane.showMessageDialog(null, "Cannot set piece on this tile.");
                    return;
                }
                //setting the space tile
                if (numberOfTilesSet == 0) {
                    tiles[index].setText(""); //not displaying zero
                    tiles[index].setBackground(new Color(174, 175, 175));
                }
                //setting the numeric tiles
                else {
                    tiles[index].setText(Integer.toString(numberOfTilesSet));
                    tiles[index].setBackground(new Color(72, 195, 206));
                }

                setOfPlacedTiles.add(index);
                numberOfTilesSet++;

                //done setting the board, converting the tiles to a new BOARD
                if (numberOfTilesSet == 9) 
                {
                    gameSolved = false;
                    settingBoardMode = false;
                    ArrayList<Integer> newBoard = new ArrayList<>();
                    for (int i = 0; i < GameConstants.BOARD_SIZE; i++) {
                        String s = tiles[i].getText();
                        if (s.equals(""))
                            newBoard.add(0);
                        else
                            newBoard.add(Integer.parseInt(s));
                    }
                    gameBoard = new Board(newBoard);
                }
            }
            //completion message
            if (gameSolved == false && gameBoard.getHeuristicValue() == 0)
            {
                gameSovled == true;
                JOptionPane.showMessageDialog(null, "Congratulations, the puzzle is solved!");
            }

        }
    }

    //event handler for setting the board
    private class SetBoardEventHandler implements ActionListener
    {
        public void actionPerformed(ActionEvent event)
        {
            settingBoardMode = true;
            setOfPlacedTiles = new ArrayList<>();
            numberOfTilesSet = 0;
            JOptionPane.showMessageDialog(null, "Please click on the buttons in order from 0-8 to set their location.");
            for (int i = 0; i < GameConstants.BOARD_SIZE; i++) {
                tiles[i].setText("...");
                tiles[i].setBackground(Color.CYAN);
            }
        }
    }

    //event handler for auto solving
    private class AutoSolveEventHandler implements ActionListener
    {
        public void actionPerformed(ActionEvent event)
        {
            //cannot solve in the middle of setting the board
            if (settingBoardMode == true)
            {
                JOptionPane.showMessageDialog(null, "Please finish setting the board before continuing.");
                return;
            }

            lockTiles();

            //running the solving algorithm
            SearchTree autoSolveSearchTree = new SearchTree(gameBoard);
            autoSolveResult = autoSolveSearchTree.beginGUIAutomaticSolution();
            if (autoSolveResult.getBoardHeuristic() == 0)
                JOptionPane.showMessageDialog(null, "The puzzle was successfully solved.");
            else
                JOptionPane.showMessageDialog(null, "The puzzle was impossible to solve. Showing the closest solution.");

            //displaying the automated solution in a separate thread so GUI can update whilst in this event handler
            SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
                @Override
                public Void doInBackground() {
                    Node temp = autoSolveResult;
                    ArrayList<Node> nodeVector = new ArrayList<>();
                    //creating a vector of the Nodes to generate a path
                    while (temp != null)
                    {
                        nodeVector.add(temp);
                        temp = temp.getPreviousNode();
                    }

                    //iterating in reverse to end with the root/solution board
                    for (int i = nodeVector.size()-1; i >=0; i--)
                    {
                        Node currentNode = nodeVector.get(i);
                        Board currBoard = currentNode.getGameBoard();
                        int pieceToSet;
                        //iterating through all the board pieces
                        for (int j = 0; j < GameConstants.BOARD_SIZE; j++)
                        {
                            pieceToSet = currBoard.getPieceAt(j);
                            //setting the space tile
                            if (pieceToSet == 0) {
                                tiles[j].setText("");
                                tiles[j].setBackground(new Color(174, 175, 175));
                            }
                            //setting the numeric tiles
                            else {
                                tiles[j].setText(Integer.toString(pieceToSet));
                                tiles[j].setBackground(new Color(72, 195, 206));
                            }
                        }
                        try {
                            Thread.sleep(300); //pause for "animation"
                        } catch (InterruptedException ex) {
                            JOptionPane.showMessageDialog(null, "Auto-Solver display interrupted.");
                        }
                        gameBoard = currBoard;
                    }
                    return null;
                }
            };
            worker.execute(); //runs the thread

            unlockTiles();
        }
    }

}
