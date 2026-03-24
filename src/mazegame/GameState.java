package mazegame;

public class GameState {
    private int steps;
    private int level;
    private boolean won;

    public GameState() {
        reset();
        level = 1;
    }

    public void reset() {
        steps = 0;
        won = false;
    }

    public int getSteps() {
        return steps;
    }

    public void increaseSteps() {
        steps++;
    }

    public int getLevel() {
        return level;
    }

    public void nextLevel() {
        level++;
        reset();
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public boolean isWon() {
        return won;
    }

    public void setWon(boolean won) {
        this.won = won;
    }
}