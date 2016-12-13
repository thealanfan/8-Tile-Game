/* ------------------------------------------------
 * 8 Tiles UI Program
 *
 * Class: CS 342, Fall 2016
 * System: Windows 10, IntelliJ IDE
 * Author Code Word: CIAO
 * -------------------------------------------------
 */


/* ------------------------------------------------
 * GAMECONSTANTS CLASS for the program, contains
 * only constants associated with this game.
 *
 * Most notable aspect: long prompt STRING is
 * hidden here as an abstraction.
 * -------------------------------------------------
 */


public final class GameConstants {

    private GameConstants()
    { ; }

    public static final int BOARD_SIZE = 9;

    public static final int MOVE_UP = -3;

    public static final int MOVE_DOWN = 3;

    public static final int MOVE_RIGHT = 1;

    public static final int MOVE_LEFT = -1;

    public static final String WELCOME_PROMPT =
        "Welcome to the 8-tiles puzzle.\n" +
        "Place the tiles in ascending numerical order.  For each\n" +
        "move click the piece to be moved into the blank square,\n" +
        "or Exit to quit the program.\n";


}