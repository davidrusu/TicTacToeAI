package core;

/**
 * Stores stats, used by the {@link BoardDatabase} class
 * <p/>
 * User: davidrusu
 * Date: 31/03/13
 * Time: 8:17 PM
 */
public class BoardStats {
    private int totalGames, wins, losses; // stats are with respect to 'X' aka '1'

    public int getTotalGames() {
        return totalGames;
    }

    public int getWins(int mark) {
        assert mark != 0;

        if (mark == -1) {
            return losses;
        }
        return wins;
    }

    public int getLosses(int mark) {
        assert mark != 0;

        if (mark == -1) {
            return wins;
        }
        return losses;
    }

    public int getTies() {
        return totalGames - wins - losses;
    }

    public void incrementStats(int finalMark, boolean tie) {
        assert finalMark != 0;

        if (!tie) {
            if (finalMark == -1) {
                losses++;
            } else {
                wins++;
            }
        }
        totalGames++;
    }

    public String toString() {
        return "games: " + totalGames + ", wins: " + wins + ", losses: " + losses + " ties " + getTies();
    }
}
