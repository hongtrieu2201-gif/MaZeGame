package mazegame;

public class Enemy {
    private int row;
    private int col;
    private int dirRow;
    private int dirCol;

    public Enemy(int row, int col, int dirRow, int dirCol) {
        this.row = row;
        this.col = col;
        this.dirRow = dirRow;
        this.dirCol = dirCol;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public void move(Maze maze) {
        int newRow = row + dirRow;
        int newCol = col + dirCol;

        if (newRow < 0 || newRow >= maze.getRows() || newCol < 0 || newCol >= maze.getCols() || maze.isWall(newRow, newCol)) {
            dirRow = -dirRow;
            dirCol = -dirCol;

            newRow = row + dirRow;
            newCol = col + dirCol;
        }

        if (newRow >= 0 && newRow < maze.getRows() && newCol >= 0 && newCol < maze.getCols() && !maze.isWall(newRow, newCol)) {
            row = newRow;
            col = newCol;
        }
    }
}