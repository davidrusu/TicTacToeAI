package core;

/**
 * documentation
 * User: davidrusu
 * Date: 27/03/13
 * Time: 8:28 PM
 */
public class Move {
    private final int x, y;

    public Move(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public String toString() {
        return "move x:" + x + " y:" + y;
    }
}
