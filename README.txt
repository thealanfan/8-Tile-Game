# 8-Tile-Game
Java Swing Implementation of the 8-puzzle

Author:         ALAN FAN
Coursework for: UIC CS 342, Software Design
                Fall 2016
                Professor Dale Reed
                
** Requires Java 1.6+ for <> syntax **
** Program driver located in TilesGUIDriver class **


**************************************************************************************************
                                       PROGRAM DESCRIPTION
**************************************************************************************************
This is my implementation of the 8-puzzle game.
The starting puzzle can either be random (truly random, including unsolvable boards) or user-set
via the "Set Board" button.  It features an "Auto-solve" button/functionality which uses an
A*search algorithm on a board heuristic of Manhattan-distance-summation.  To solve the game,
the user must click on the tiles adjacent to the empty space to "slide" them into the space
until the board is numerically ordered as such:

_____________
| 1 | 2 | 3 |
| 4 | 5 | 6 |
| 7 | 8 |   |
_____________



**************************************************************************************************
                                       CLASSES DESCRIPTION
**************************************************************************************************
The GameConstants class is a private, final class that holds constants used in the game, notably
the BOARD_SIZE (9), the different corresponding array values of moves (-1, +1, -3, +3), and
strings that serve as prompts thusly hidden from implementation.

The Board class holds the underlying implementation of the board, namely an ArrayList and methods
for manipulating the board (e.g. making a move, getting the heuristic Manhattan-distance-summation,
etc).

The Node class serves as a graph node holding a game state (ergo board) for use in the A*search.
It is uses a previous node pointer to specify the graph.

The SearchTree class implements the A*search when constructed with a Board.

The TilesGUI class extends JFrame and holds the code for the actual GUI.  The ContentPane is
setup as a BoarderLayout containing GridLayouts of buttons.

The TilesGUIDriver class is the driver for the program.  It holds the main method which constructs
a TilesGUI object that starts the game.




**************************************************************************************************
                                       CODE USE INFORMATION
**************************************************************************************************
Feel free to use this code with proper author citation.
I would love to hear any feedback.
You may reach me at alan (dot) w (dot) fan (at) gmail (dot) com
