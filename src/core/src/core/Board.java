package core.src.core;

/**
 * Stores the tic tac toe board representation
 * <p/>
 * User: davidrusu
 * Date: 30/03/13
 * Time: 1:34 PM
 */
public class Board {
    private final int length, lengthSq;
    // the first 'lengthSq' bits represent the the 'X''s on the board,
    // the following 'lengthSq' represent the 'O''s
    private final int board;

    /**
     * Constructs a 3 x 3 tic tac toe board
     */
    public Board() {
        this(3);
    }

    /**
     * Constructs a tic tac toe board that is the specified length
     *
     * @param length must be less than 5
     */
    public Board(int length) {
        this(0, length);
    }


    private Board(int board, int length) {
        if (length < 1 || length > 4) {
            throw new IllegalArgumentException("length > 0 && < 5, length: " + length);
        }
        this.length = length;
        this.board = board;
        lengthSq = length * length;
    }

    /**
     * Constructs a tic tac toe board based on the specified board with a the new move added to the board
     *
     * @param board the old board
     * @param move  the move to add to the board
     * @param mark  the mark of the new move to add to the board
     */
    public Board(Board board, Move move, int mark) {
        length = board.length;
        lengthSq = length * length;

        int bitMask = 1 << move.getX() + move.getY() * length;
        if (mark == -1) {
            // the 'X' marks are stored in the first 'lengthSq' bits of the
            // board, the 'O' marks are stored in the next 'lengthSq' bits.
            // since mark == -1 == 'O', we have to shift 'lengthSq' to get into
            // the 'O' region of the board
            bitMask <<= lengthSq;
        }
        this.board = board.board | bitMask;
    }

    public int getLength() {
        return length;
    }

    public int getBoard() {
        return board;
    }

    public int getMark(int x, int y) {
        int shiftAmount = x + y * length;
        int bitMask = 1 << shiftAmount;
        int markX = (board & bitMask) >>> shiftAmount;
        if (markX == 1) {
            return 1;
        }
        int markO = (board & (bitMask << lengthSq)) >>> shiftAmount + lengthSq;
        return -markO; // mark will either be 0 or 1, 'O' == -1
    }

    public boolean isMoveLegal(Move move) {
        int x = move.getX();
        int y = move.getY();
        boolean inBounds = x >= 0 && x < length && y >= 0 && y < length;
        return inBounds && getMark(x, y) == 0;
    }

    public boolean isGameOver() {
        for (int i = 0; i < length; i++) {
            if (isLineEqual(i, 0, 0, 1) || isLineEqual(0, i, 1, 0)) {
                return true;
            }
        }
        if (isLineEqual(0, 0, 1, 1) || isLineEqual(length - 1, 0, -1, 1)) {
            return true;
        }

        return isGameBoardFull();
    }

    private boolean isLineEqual(int startX, int startY, int xInc, int yInc) {
        for (int x = startX + xInc, y = startY + yInc, i = 1; i < length; x += xInc, y += yInc, i++) {
            if (getMark(x, y) == 0 || getMark(x, y) != getMark(x - xInc, y - yInc)) {
                return false;
            }
        }
        return true;
    }

    private boolean isGameBoardFull() {
        for (int i = 0; i < lengthSq; i++) {
            if ((board & 1 << i | (board & 1 << i + lengthSq) >>> lengthSq) >>> i != 1) {
                return false;
            }
        }
        return true;
    }

    public boolean isTie() {
        for (int i = 0; i < length; i++) {
            if (isLineEqual(i, 0, 0, 1) || isLineEqual(0, i, 1, 0)) {
                return false;
            }
        }
        return !(isLineEqual(0, 0, 1, 1) || isLineEqual(length - 1, 0, -1, 1));
    }

    public Board getHorizontallyFlippedBoard() {
        int flippedBoard = 0;
        for (int i = 0; i < length * 2; i++) {
            for (int j = 0; j < length / 2; j++) {
                flippedBoard |= (board & (1 << i * length + j)) << length - j - 1;
                flippedBoard |= (board & (1 << i * length + length - j - 1)) >>> length - j - 1;
            }

            if (length % 2 != 0) {
                flippedBoard |= board & (1 << i * length + length / 2);
            }
        }
        return new Board(flippedBoard, length);
    }

    public Board getRotatedRightBoard() {
        int rotatedBoard = 0;
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < length; j++) {
                int shiftAmount = (length - j - 1) * length + i;
                int mask = 1 << shiftAmount;
                int correctedShiftAmount = shiftAmount - (i * length + j);
                if (correctedShiftAmount < 0) {
                    correctedShiftAmount = -correctedShiftAmount;
                    rotatedBoard |= (board & mask) << correctedShiftAmount;
                    rotatedBoard |= (board & (mask << lengthSq)) << correctedShiftAmount;
                } else {
                    rotatedBoard |= (board & mask) >>> correctedShiftAmount;
                    rotatedBoard |= (board & (mask << lengthSq)) >>> correctedShiftAmount;
                }
            }
        }
        return new Board(rotatedBoard, length);
    }

    private boolean isRotatedBoardEquivalent(Board board) {
        int i = 0;
        Board temp = board;
        while (i < 3) {
            temp = temp.getRotatedRightBoard();
            if (temp.board == this.board) {
                return true;
            }
            i++;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return board;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Board) {
            return ((Board) obj).board == board;
        }
        return false;
    }
}
