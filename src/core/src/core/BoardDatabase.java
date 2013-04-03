package core.src.core;

import java.util.HashMap;

/**
 * Stores mappings between {@link Board} and the {@link BoardStats}
 * <p/>
 * User: davidrusu
 * Date: 01/04/13
 * Time: 8:26 PM
 */
public class BoardDatabase {
    private final HashMap<Board, BoardStats> data = new HashMap<>(1000);

    public void updateBoardStats(Board board, int finalMark, boolean tie) {
        assert finalMark != 0;

        BoardStats boardStats = getBoardStats(board);
        boardStats.incrementStats(finalMark, tie);
    }

    public BoardStats getBoardStats(Board board) {
        BoardStats boardStats = getEquivalentBoardStats(board);
        if (boardStats == null) {
            boardStats = new BoardStats();
            data.put(board, boardStats);
        }
        return boardStats;
    }

    private BoardStats getEquivalentBoardStats(Board board) {
        BoardStats boardStats = getRotatedBoardStats(board);
        if (boardStats == null) {
            boardStats = getRotatedBoardStats(board.getHorizontallyFlippedBoard());
        }
        return boardStats;
    }

    private BoardStats getRotatedBoardStats(Board board) {
        Board temp = board;
        int i = 0;
        BoardStats boardStats = data.get(board);
        while (i < 3 && boardStats == null) {
            temp = temp.getRotatedRightBoard();
            boardStats = data.get(temp);
            i++;
        }
        return boardStats;
    }

    public boolean isContained(Board board) {
        return getEquivalentBoardStats(board) != null;
    }

    public int size() {
        return data.size();
    }
}
