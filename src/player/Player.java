package player;

import core.Board;
import core.Move;
import core.TicTacToe;

/**
 * documentation
 * User: davidrusu
 * Date: 27/03/13
 * Time: 8:25 PM
 */
public interface Player {

    Move getMove(Board board, TicTacToe game, int mark);

    boolean isHuman();

    void youLost(Board board, int finalMark);

    void youWon(Board board, int finalMark);

    void tie(Board board, int finalMark);
}
