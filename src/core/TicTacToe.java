package core;

import player.Player;

import java.util.ArrayDeque;

/**
 * documentation
 * User: davidrusu
 * Date: 27/03/13
 * Time: 8:20 PM
 */
public class TicTacToe {
    private final BoardDatabase boardDatabase = new BoardDatabase();
    private final ArrayDeque<Board> boardHistoryStack = new ArrayDeque<>();
    private final Object inputLock = new Object();
    private volatile boolean receivedHumanInput;
    private final GUI gui;
    private Move currentPlayerMove;
    private Board board;
    private Player waitingPlayer1, waitingPlayer2, player1, player2, currentPlayer;
    private int currentMark, player1Score, player2Score, totalGames;

    public TicTacToe() {
        gui = new GUI(this);
    }

    public void setPlayer1(Player player1) {
        waitingPlayer1 = player1;
    }

    public void setPlayer2(Player player2) {
        waitingPlayer2 = player2;
    }

    /**
     * Clears the board and starts a new game
     */
    public void newGame() {
        clear();
        assignPlayers();
        do {
            receivedHumanInput = false;
            Move move = currentPlayer.getMove(board, this, currentMark);
            if (move == null) {
                waitForHumanInput();
            } else {
                currentPlayerMove = move;
            }
            if (board.isMoveLegal(currentPlayerMove)) {
                updateBoard();
                finishedTurn();
            }
            gui.repaint();
        } while (!board.isGameOver());
        postmortem();
    }

    private void waitForHumanInput() {
        synchronized (inputLock) {
            while (!receivedHumanInput) {
                try {
                    inputLock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void postmortem() {
        finishedTurn();
        boolean tie = board.isTie();
        if (!tie) {
            Board tempPop = boardHistoryStack.pop();
            if (currentPlayer == player2) {
                player2Score++;
                player2.youWon(board, currentMark);
                player1.youLost(boardHistoryStack.peek(), currentMark);
            } else {
                player1Score++;
                player1.youWon(board, currentMark);
                player2.youLost(boardHistoryStack.peek(), currentMark);
            }
            boardHistoryStack.push(tempPop);
        } else {
            player1.tie(board, currentMark);
            player2.tie(board, currentMark);
        }
        while (!boardHistoryStack.isEmpty()) {
            boardDatabase.updateBoardStats(boardHistoryStack.pop(), currentMark, tie);
        }
        totalGames++;
    }

    private void clear() {
        boardHistoryStack.clear();
        board = new Board();
    }

    private void assignPlayers() {
        if (player1 == waitingPlayer1) {
            player1 = waitingPlayer2;
            player2 = waitingPlayer1;
        } else {
            player1 = waitingPlayer1;
            player2 = waitingPlayer2;
        }
        currentMark = 1;
        currentPlayer = player1;
    }

    private void finishedTurn() {
        if (currentPlayer == player1) {
            currentPlayer = player2;
        } else {
            currentPlayer = player1;
        }
        currentMark = -currentMark;
    }

    private void updateBoard() {
        board = new Board(board, currentPlayerMove, currentMark);
        boardHistoryStack.push(board);
    }

    public Board getCurrentBoard() {
        return board;
    }

    public void humanInputDetected(Move move) {
        if (currentPlayer.isHuman()) {
            synchronized (inputLock) {
                currentPlayerMove = move;
                receivedHumanInput = true;
                inputLock.notify();
            }
        }
    }

    public BoardStats getBoardStats(Board board) {
        return boardDatabase.getBoardStats(board);
    }

    public int getPlayer1Score() {
        return player1Score;
    }

    public int getPlayer2Score() {
        return player2Score;
    }

    public int getTotalGames() {
        return totalGames;
    }
}
