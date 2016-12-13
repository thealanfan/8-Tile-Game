/* ------------------------------------------------
 * 8 Tiles UI Program
 *
 * Class: CS 342, Fall 2016
 * System: Windows 10, IntelliJ IDE
 * Author Code Word: CIAO
 * -------------------------------------------------
 */


/* ------------------------------------------------
 * BOARD CLASS for the program, contains only one
 * member variable, an ArrayList<Integer> represen-
 * tation of the 8 Tiles game, where the space is
 * coded as 0.
 *
 * Member functions include:
 *  -constructor for random board
 *  -constructor based on a starting config
 *  -methods for checking move validation based on
 *      -piece to move
 *      -move direction
 *  -getting index of piece
 *  -moving pieces
 *  -making new Boards based on move direction
 *  -getting Manhattan distance heuristic
 *  -conversion to 9-digit integer
 *  -conversion to string
 * -------------------------------------------------
 */


import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Board {

    //tiles represented as an ARRAYLIST<INTEGER>'s
    private ArrayList<Integer> boardPieces;

    //default constructor makes random board
    public Board()
    {
        boardPieces = new ArrayList<>();
        for (int i = 0; i < GameConstants.BOARD_SIZE; i++)
            boardPieces.add(i);
        Collections.shuffle(boardPieces, new Random(System.currentTimeMillis()));
    }

    //constructor makes board from given ARRAYLIST<INTEGER>'s
    public Board(ArrayList<Integer> startingConfig)
    {
        boardPieces = startingConfig;
    }

    //copy constructor that makes a DEEP COPY
    public Board(Board otherBoard)
    {
        boardPieces = new ArrayList<>();
        for (int i : otherBoard.boardPieces)
            boardPieces.add(i);
    }

    //returns the index of the space tile
    public int getIndexOfSpace()
    {
        return boardPieces.indexOf(0);
    }

    //returns the validity to move piece i where 0 < i < 9
    public boolean validMoveForPiece(int pieceToMove)
    {
        if (pieceToMove < 1 || pieceToMove > 8)
            return false;

        int indexOfPieceToMove = boardPieces.indexOf(pieceToMove);
        int indexOfZero = boardPieces.indexOf(0);
        int dif = indexOfPieceToMove - indexOfZero;
        if ((dif == GameConstants.MOVE_DOWN && this.validMoveDown()) ||
                (dif == GameConstants.MOVE_UP && this.validMoveUp()) ||
                (dif == GameConstants.MOVE_LEFT && this.validMoveLeft()) ||
                (dif == GameConstants.MOVE_RIGHT && this.validMoveRight()))
            return true;

        return false;
    }

    //returns validity of a move up for the board
    public boolean validMoveUp()
    {
        boolean validity;
        int indexOfZero = boardPieces.indexOf(0);
        if (indexOfZero < 3)
            validity = false;
        else
            validity = true;
        return validity;
    }

    //returns validity of a move down for the board
    public boolean validMoveDown()
    {
        boolean validity;
        int indexOfZero = boardPieces.indexOf(0);
        if (indexOfZero > 5)
            validity = false;
        else
            validity = true;
        return validity;
    }

    //returns validity of a move left for the board
    public boolean validMoveLeft()
    {
        boolean validity;
        int indexOfZero = boardPieces.indexOf(0);
        if (indexOfZero == 0 || indexOfZero == 3 || indexOfZero == 6)
            validity = false;
        else
            validity = true;
        return validity;
    }

    //returns validity of a move right for the board
    public boolean validMoveRight()
    {
        boolean validity;
        int indexOfZero = boardPieces.indexOf(0);
        if (indexOfZero == 2 || indexOfZero == 5 || indexOfZero == 8)
            validity = false;
        else
            validity = true;
        return validity;
    }

    //returns the piece at the given index
    public int getPieceAt(int index)
    {
        return boardPieces.get(index);
    }

    //swpas two board pieces
    public void swap(int piece1, int piece2)
    {
        int index1 = boardPieces.indexOf(piece1);
        int index2 = boardPieces.indexOf(piece2);
        boardPieces.set(index1, piece2);
        boardPieces.set(index2, piece1);
    }

    //returns the resultant board of making a move right
    public Board newBoardAfterMoveRight()
    {
        if (!validMoveRight())
            return null;
        Board ret = new Board(this);

        int indexOfZero = boardPieces.indexOf(0);
        int swapPiece = getPieceAt(indexOfZero+GameConstants.MOVE_RIGHT);
        ret.swap(0, swapPiece);

        return ret;
    }

    //returns the resultant board of making a move left
    public Board newBoardAfterMoveLeft()
    {
        if (!validMoveLeft())
            return null;
        Board ret = new Board(this);

        int indexOfZero = boardPieces.indexOf(0);
        int swapPiece = getPieceAt(indexOfZero+GameConstants.MOVE_LEFT);
        ret.swap(0, swapPiece);

        return ret;
    }

    //returns the resultant board of making a move up
    public Board newBoardAfterMoveUp()
    {
        if (!validMoveUp())
            return null;
        Board ret = new Board(this);

        int indexOfZero = boardPieces.indexOf(0);
        int swapPiece = getPieceAt(indexOfZero+GameConstants.MOVE_UP);
        ret.swap(0, swapPiece);

        return ret;
    }

    //returns the resultant board of making a move down
    public Board newBoardAfterMoveDown()
    {
        if (!validMoveDown())
            return null;
        Board ret = new Board(this);

        int indexOfZero = boardPieces.indexOf(0);
        int swapPiece = getPieceAt(indexOfZero+GameConstants.MOVE_DOWN);
        ret.swap(0, swapPiece);

        return ret;
    }

    //returns the heuristic value of the board based on total Manhattan distance
    public int getHeuristicValue()
    {
        int totalManhattanDistance = 0;
        for (int piece : boardPieces)
        {
            int index = boardPieces.indexOf(piece);
            switch (piece) {
                case 1:
                    if (index < 3)
                        totalManhattanDistance += index;
                    else if (index < 6)
                        totalManhattanDistance += 1 + index - 3;
                    else
                        totalManhattanDistance += 2 + index - 6;
                    break;
                case 2:
                    if (index < 3)
                        totalManhattanDistance += Math.abs(index - 1);
                    else if (index < 6)
                        totalManhattanDistance += 1 + Math.abs(index - 4);
                    else
                        totalManhattanDistance += 2 + Math.abs(index - 7);
                    break;
                case 3:
                    if (index < 3)
                        totalManhattanDistance += 2 - index;
                    else if (index < 6)
                        totalManhattanDistance += 1 + 5 - index;
                    else
                        totalManhattanDistance += 2 + 8 - index;
                    break;
                case 4:
                    if (index < 3)
                        totalManhattanDistance += 1 + index;
                    else if (index < 6)
                        totalManhattanDistance += index - 3;
                    else
                        totalManhattanDistance += 1 + index - 6;
                    break;
                case 5:
                    if (index < 3)
                        totalManhattanDistance += 1 + Math.abs(index - 1);
                    else if (index < 6)
                        totalManhattanDistance += Math.abs(index - 4);
                    else
                        totalManhattanDistance += 1 + Math.abs(index - 7);
                    break;
                case 6:
                    if (index < 3)
                        totalManhattanDistance += 3 - index;
                    else if (index < 6)
                        totalManhattanDistance += 5 - index;
                    else
                        totalManhattanDistance += 9 - index;
                    break;
                case 7:
                    if (index < 3)
                        totalManhattanDistance += 2 + index;
                    else if (index < 6)
                        totalManhattanDistance += 1 + index - 3;
                    else
                        totalManhattanDistance += index - 6;
                    break;
                case 8:
                    if (index < 3)
                        totalManhattanDistance += 2 + Math.abs(index - 1);
                    else if (index < 6)
                        totalManhattanDistance += 1 + Math.abs(index - 4);
                    else
                        totalManhattanDistance += Math.abs(index - 7);
                    break;
                case 0:
                    if (index < 3)
                        totalManhattanDistance += 4 - index;
                    else if (index < 6)
                        totalManhattanDistance += 6 - index;
                    else
                        totalManhattanDistance += 8 - index;
                    break;
            }
        }
        return totalManhattanDistance;
    }

    //returns a unique integer for the board with space as 0
    public int toInt()
    {
        int ret = 0;
        for (int i = 0; i < GameConstants.BOARD_SIZE; i++)
            ret = 10*ret + boardPieces.get(i);
        return ret;
    }

    //returns the board as a string
    public String toString()
    {
        String ret = "";
        for (int i = 0; i < GameConstants.BOARD_SIZE; i++)
        {
            int piece = boardPieces.get(i);
            if (piece == 0)
                ret += " ";
            else
                ret += Integer.toString(piece);
        }
        return  ret;
    }

}