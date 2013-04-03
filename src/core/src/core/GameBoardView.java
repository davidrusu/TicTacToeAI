package core.src.core;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Graphical representation of the tic tac toe board
 * <p/>
 * User: davidrusu
 * Date: 28/03/13
 * Time: 10:28 PM
 */
public class GameBoardView extends JPanel implements MouseListener {
    private final TicTacToe ticTacToe;

    public GameBoardView(TicTacToe ticTacToe) {
        super(true); // true for double buffered
        this.ticTacToe = ticTacToe;
        addMouseListener(this);
    }

    public Move getMoveBasedOnMouseInput(int mouseX, int mouseY) {
        Board board = ticTacToe.getCurrentBoard();
        int x = (int) ((double) mouseX / getWidth() * board.getLength());
        int y = (int) ((double) mouseY / getHeight() * board.getLength());
        return new Move(x, y);

    }

    @Override
    public void paint(Graphics g) {
        Board board = ticTacToe.getCurrentBoard();
        int length = board.getLength();
        int squareWidth = getWidth() / length;
        int squareHeight = getHeight() / length;
        drawGrid(g, length, squareWidth, squareHeight);
        for (int x = 0; x < length; x++) {
            for (int y = 0; y < length; y++) {
                int mark = board.getMark(x, y);
                if (mark == 1) {
                    drawXAt(x, y, g, squareWidth, squareHeight);
                } else if (mark == -1) {
                    drawOAt(x, y, g, squareWidth, squareHeight);
                }
            }
        }
        drawStats(g);
    }

    private void drawStats(Graphics g) {
        int offset = 25;
        g.drawString("1: " + ticTacToe.getPlayer1Score(), offset, offset);
        g.drawString("2: " + ticTacToe.getPlayer2Score(), offset, offset * 2);
        g.drawString("games: " + ticTacToe.getTotalGames(), offset, offset * 3);
    }

    private void drawGrid(Graphics g, int columns, int squareWidth, int squareHeight) {
        for (int i = 0; i < columns - 1; i++) {
            int x = squareWidth * i + squareWidth;
            g.drawLine(x, 0, x, getHeight());
            int y = squareHeight * i + squareHeight;
            g.drawLine(0, y, getWidth(), y);
        }
    }

    private void drawOAt(int x, int y, Graphics g, int squareWidth, int squareHeight) {
        g.drawOval(x * squareWidth, y * squareHeight, squareWidth, squareHeight);
    }

    private void drawXAt(int x, int y, Graphics g, int squareWidth, int squareHeight) {
        int xOffset = x * squareWidth;
        int yOffset = y * squareHeight;
        g.drawLine(xOffset, yOffset, xOffset + squareWidth, yOffset + squareHeight);
        g.drawLine(xOffset + squareWidth, yOffset, xOffset, yOffset + squareHeight);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        ticTacToe.humanInputDetected(getMoveBasedOnMouseInput(e.getX(), e.getY()));
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
}
