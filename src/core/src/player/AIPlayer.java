package core.src.player;

import core.src.core.*;

/**
 * documentation
 * User: davidrusu
 * Date: 31/03/13
 * Time: 10:50 PM
 */
public class AIPlayer implements Player {
    private final BoardDatabase winDatabase = new BoardDatabase();
    private final BoardDatabase loseDatabase = new BoardDatabase();
    private Board lastBoard;

    @Override
    public final Move getMove(Board board, TicTacToe game, int mark) {
        Move bestMove = null;
        BoardStats bestStats = null;
        Board bestBoard = null;
        double bestWeight = -Double.MAX_VALUE;
        for (int x = 0; x < board.getLength(); x++) {
            for (int y = 0; y < board.getLength(); y++) {
                Move tempMove = new Move(x, y);
                if (board.isMoveLegal(tempMove)) {
                    Board tempBoard = new Board(board, tempMove, mark);
                    if (bestMove == null) {
                        bestMove = tempMove;
                        bestBoard = tempBoard;
                    }
                    if (winDatabase.isContained(tempBoard)) {
                        return tempMove;
                    } else if (loseDatabase.isContained(tempBoard)) {
                    } else {
                        BoardStats boardStats = game.getBoardStats(tempBoard);
                        double weight = getWeight(boardStats, mark);
                        if (weight > bestWeight) {
                            bestMove = tempMove;
                            bestWeight = weight;
                            bestStats = boardStats;
                            bestBoard = tempBoard;
                        }
                    }
                }
            }
        }
        if (bestWeight == -Double.MAX_VALUE) {
            loseDatabase.updateBoardStats(lastBoard, 1, false);
        }
        lastBoard = bestBoard;
        return bestMove;
    }

    private double getWeight(BoardStats stats, int mark) {
        if (stats.getTotalGames() == 0) {
            return Double.MAX_VALUE;
        }
        return (double) (stats.getLosses(mark) - stats.getTotalGames() + stats.getWins(mark));
//        return (stats.getWins(mark) * 50 - stats.getLosses(mark) * 10.0 - stats.getTies() * 5) / stats.getTotalGames();
    }

    @Override
    public final boolean isHuman() {
        return false;
    }

    @Override
    public final void youLost(Board board, int finalMark) {
        loseDatabase.updateBoardStats(board, finalMark, false);
    }

    @Override
    public final void youWon(Board board, int finalMark) {
        winDatabase.updateBoardStats(board, finalMark, false);
//        if (!winDatabase.isContained(board)) {
//            System.out.println("oops");
//        }
    }

    @Override
    public void tie(Board board, int finalMark) {
    }
}
