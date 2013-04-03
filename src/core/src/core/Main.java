package core.src.core;

import core.src.player.AIPlayer;

/**
 * documentation
 * User: davidrusu
 * Date: 26/03/13
 * Time: 10:44 PM
 */
public class Main {
    private final TicTacToe ticTacToe = new TicTacToe();

    public Main() {
        ticTacToe.setPlayer1(new AIPlayer());
        ticTacToe.setPlayer2(new AIPlayer());
    }

    public void start() {
        while (true) {
            ticTacToe.newGame();
        }
    }

    public static void main(String[] args) {
        Main game = new Main();
        game.start();
    }
}
