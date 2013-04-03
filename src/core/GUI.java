package core;

import player.HumanPlayer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * GUI for the tic tac toe game
 * <p/>
 * User: davidrusu
 * Date: 28/03/13
 * Time: 10:17 PM
 */
public class GUI extends JFrame implements ActionListener {
    private final TicTacToe ticTacToe;

    public GUI(TicTacToe ticTacToe) {
        this.ticTacToe = ticTacToe;
        setSize(500, 600);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLayout(new GridBagLayout());
        initComponents();
        setVisible(true);
    }

    private void initComponents() {
        GridBagConstraints c = new GridBagConstraints();

        GameBoardView gameBoardView = new GameBoardView(ticTacToe);
        c.anchor = GridBagConstraints.PAGE_START;
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1;
        c.weighty = 0.9;
        c.gridx = 0;
        c.gridy = 0;
        add(gameBoardView, c);

        JButton newGameButton = new JButton("Play Against AI");
        newGameButton.addActionListener(this);
        c.anchor = GridBagConstraints.PAGE_END;
        c.weightx = 1;
        c.weighty = 0.1;
        c.gridx = 0;
        c.gridy = 1;
        add(newGameButton, c);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
//        ticTacToe.setBoard(ticTacToe.getCurrentBoard().getHorizontallyFlippedBoard());
        ticTacToe.setPlayer1(new HumanPlayer());
    }
}
