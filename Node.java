/* ------------------------------------------------
 * 8 Tiles UI Program
 *
 * Class: CS 342, Fall 2016
 * System: Windows 10, IntelliJ IDE
 * Author Code Word: CIAO
 * -------------------------------------------------
 */


/* ------------------------------------------------
 * NODE CLASS for the program, contains a BOARD,
 * NODE previousNode, and INT moveNumber as member
 * variables.
 *
 * DESIGN NOTE: Did not choose to have NODE extend
 * board as the assignment described a NODE as
 * "having" a board and thus composition.
 *
 * Member functions include:
 *  -constructor for head node of tree
 *  -constructor for non-head node of tree
 *  -getter for move number
 *  -methods to access the BOARD methods (as board
 *      is private class member variable)
 *  -methods to generate new nodes based on move
 *      direction
 *  -method to recursively print the solution
 *  -overloaded toString/printing functions
 *
 * Most notable aspect: NODE's only have pointers
 * to previous NODE's, so it requires non-tail
 * recursion, which is poor stack usage.
 * -------------------------------------------------
 */


public class Node {

    private Board gameBoard;
    private Node previousNode;
    private int moveNumber;

    //copy constructor for head of linked list of Nodes
    public Node(Board boardToUse)
    {
        gameBoard = boardToUse;
        previousNode = null;
        moveNumber = 1;
    }

    //copy constructor for non-head of linked list of Nodes
    public Node(Board boardToUse, Node prev)
    {
        gameBoard = boardToUse;
        previousNode = prev;
        moveNumber = prev.moveNumber + 1;
    }

    public int getBoardHeuristic()
    {
        return gameBoard.getHeuristicValue();
    }

    public Board getGameBoard() { return gameBoard; }

    public int getMoveNumber()
    {
        return moveNumber;
    }

    public int getHashKey()
    {
        return gameBoard.toInt();
    }

    public boolean validMoveUp()
    {
        return gameBoard.validMoveUp();
    }

    public boolean validMoveDown()
    {
        return gameBoard.validMoveDown();
    }

    public boolean validMoveRight()
    {
        return gameBoard.validMoveRight();
    }

    public boolean validMoveLeft()
    {
        return gameBoard.validMoveLeft();
    }

    //returns the resultant Node of making a move UP on the board
    public Node newNodeAfterMoveUp()
    {
        Board newBoard = gameBoard.newBoardAfterMoveUp();
        Node ret = new Node(newBoard, this);
        return ret;
    }

    //returns the resultant Node of making a move DOWN on the board
    public Node newNodeAfterMoveDown()
    {
        Board newBoard = gameBoard.newBoardAfterMoveDown();
        Node ret = new Node(newBoard, this);
        return ret;
    }

    //returns the resultant Node of making a move LEFT on the board
    public Node newNodeAfterMoveLeft()
    {
        Board newBoard = gameBoard.newBoardAfterMoveLeft();
        Node ret = new Node(newBoard, this);
        return ret;
    }

    //returns the resultant Node of making a move RIGHT on the board
    public Node newNodeAfterMoveRight()
    {
        Board newBoard = gameBoard.newBoardAfterMoveRight();
        Node ret = new Node(newBoard, this);
        return ret;
    }

    public Node getPreviousNode()
    {
        return this.previousNode;
    }

    public String toString()
    {
        return gameBoard.toString();
    }
}