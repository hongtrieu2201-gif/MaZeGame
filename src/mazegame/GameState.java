package mazegame;

public class GameState {
    private int steps;
    private int level;
    private boolean won;

    private int lives;
    private int coinsCollected;
    private int totalCoins;

    public GameState() {
        level = 1;
        reset();
    }

    public void reset() {
        steps = 0;
        won = false;
        lives = 3;
        coinsCollected = 0;
        totalCoins = 0;
    }

    public void resetForNewMapKeepLives() {
        steps = 0;
        won = false;
        coinsCollected = 0;
        totalCoins = 0;
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

    public int getLives() {
        return lives;
    }

    public void loseLife() {
        if (lives > 0) {
            lives--;
        }
    }

    public void setLives(int lives) {
        this.lives = lives;
    }

    public int getCoinsCollected() {
        return coinsCollected;
    }

    public void collectCoin() {
        coinsCollected++;
    }

    public int getTotalCoins() {
        return totalCoins;
    }
    
public int getStars() {
    if (coinsCollected >= 3) return 3;
    return coinsCollected;
}
public String getStarsText() {
    int stars = getStars();
    return "*".repeat(stars) + "-".repeat(3 - stars);
}
    public void setTotalCoins(int totalCoins) {
        this.totalCoins = totalCoins;
    }
}