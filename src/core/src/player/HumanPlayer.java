package core.src.player;

import core.src.core.Board;
import core.src.core.Move;
import core.src.core.TicTacToe;

/**
 * documentation
 * User: davidrusu
 * Date: 30/03/13
 * Time: 12:20 PM
 */
public class HumanPlayer implements Player {

    @Override
    public Move getMove(Board board, TicTacToe ticTacToe, int mark) {
        return null;
    }

    @Override
    public boolean isHuman() {
        return true;
    }

    @Override
    public void youLost(Board board, int finalMark) {
    }

    @Override
    public void youWon(Board board, int finalMark) {
    }

    @Override
    public void tie(Board board, int finalMark) {
    }
}
