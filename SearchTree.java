/* ------------------------------------------------
 * 8 Tiles UI Program
 *
 * Class: CS 342, Fall 2016
 * System: Windows 10, IntelliJ IDE
 * Author Code Word: CIAO
 * -------------------------------------------------
 */


/* ------------------------------------------------
 * SEARCHTREE CLASS for the program, contains all
 * the necessary code for the automatic solving
 * of the 8 tile game. The class member variables
 * are all self documented.
 *
 * Member functions include:
 *  -a constructor to set the AI
 *  -a driver method
 *  -move validation methods
 *  -Tree growing methods
 *  -unsolvable board/heuristics message
 *      method
 *
 * Most notable aspect: the COMPARATOR for the
 * PRIORITY QUEUE first compares heuristics, and
 * secondarily move number to favor faster solutions.
 * -------------------------------------------------
 */


import java.util.Comparator;
import java.util.HashSet;
import java.util.PriorityQueue;


public class SearchTree {

    private HashSet<Integer> visitedNodes;
    private PriorityQueue<Node> possibleMoves;
    private Board startingBoard;
    private Node closestNode;

    //constructor that initializes the search tree
    SearchTree(Board gameBoard)
    {
        startingBoard = gameBoard;
        Node startNode = new Node(startingBoard);

        possibleMoves = new PriorityQueue(100, nodeComparator);
        possibleMoves.add(startNode);

        visitedNodes = new HashSet<>(200000);
        visitedNodes.add(startNode.getHashKey());
        closestNode = startNode;
    }

    Comparator<Node> nodeComparator = new Comparator<Node>() {
        @Override
        public int compare(Node x, Node y) {
            int diff = x.getBoardHeuristic() - y.getBoardHeuristic();
            if (diff == 0)
                return x.getMoveNumber() - y.getMoveNumber();
            else
                return diff;
        }
    };


    //returns either the solved board Node (tail of linked list) or the closest Node
    public Node beginGUIAutomaticSolution()
    {
        while (!possibleMoves.isEmpty())
        {
            Node bestMoveNode = possibleMoves.remove();
            if (bestMoveNode.getBoardHeuristic() < closestNode.getBoardHeuristic())
                closestNode = bestMoveNode;

            if (bestMoveNode.getBoardHeuristic() == 0)
                return bestMoveNode;

            this.addValidMoves(bestMoveNode);
        }

        return closestNode;
    }

    //adds all valid moves to possibleMoves
    private void addValidMoves(Node bestMoveNode)
    {
        if (bestMoveNode.validMoveLeft())
            addLeftMoveNode(bestMoveNode);
        if (bestMoveNode.validMoveRight())
            addRightMoveNode(bestMoveNode);
        if (bestMoveNode.validMoveUp())
            addUpMoveNode(bestMoveNode);
        if (bestMoveNode.validMoveDown())
            addDownMoveNode(bestMoveNode);
    }

    //adds valid left moves to possibleMoves
    private void addLeftMoveNode(Node bestMoveNode)
    {
        Node leftMoveNode = bestMoveNode.newNodeAfterMoveLeft();
        if (!visitedNodes.contains(leftMoveNode.getHashKey()))
        {
            possibleMoves.add(leftMoveNode);
            visitedNodes.add(leftMoveNode.getHashKey());
        }
    }

    //adds valid right moves to possibleMoves
    private void addRightMoveNode(Node bestMoveNode)
    {
        Node rightMoveNode = bestMoveNode.newNodeAfterMoveRight();
        if (!visitedNodes.contains(rightMoveNode.getHashKey()))
        {
            possibleMoves.add(rightMoveNode);
            visitedNodes.add(rightMoveNode.getHashKey());
        }
    }

    //adds valid up moves to possibleMoves
    private void addUpMoveNode(Node bestMoveNode)
    {
        Node upMoveNode = bestMoveNode.newNodeAfterMoveUp();
        if (!visitedNodes.contains(upMoveNode.getHashKey()))
        {
            possibleMoves.add(upMoveNode);
            visitedNodes.add(upMoveNode.getHashKey());
        }
    }

    //adds valid down moves to possibleMoves
    private void addDownMoveNode(Node bestMoveNode)
    {
        Node downMoveNode = bestMoveNode.newNodeAfterMoveDown();
        if (!visitedNodes.contains(downMoveNode.getHashKey()))
        {
            possibleMoves.add(downMoveNode);
            visitedNodes.add(downMoveNode.getHashKey());
        }
    }

}