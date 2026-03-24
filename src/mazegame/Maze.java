package mazegame;

import java.awt.Point;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

public class Maze {
    private char[][] grid;
    private int rows;
    private int cols;
    private Point start;
    private Point exit;

    private final Random random = new Random();

    public void loadLevel(int level) {
        if (level == 1) {
          loadFromStrings(new String[]{
        "###############",
        "#S............#",
        "#.###.#.#####.#",
        "#...#.#.....#.#",
        "###.#.#####.#.#",
        "#...#.....#.#.#",
        "#.#######.#.#.#",
        "#.......#.#...#",
        "#.#####.#.###.#",
        "#.....#.....#E#",
        "###############"
});
        } else if (level == 2) {
            loadFromStrings(new String[]{
                    "#################",
                    "#S....#.....#...#",
                    "###.#.#.###.#.#.#",
                    "#...#.#...#...#.#",
                    "#.###.###.#####.#",
                    "#.....#...#.....#",
                    "#.#####.###.###.#",
                    "#.#.....#...#...#",
                    "#.#.#####.###.#.#",
                    "#...#.......#.#E#",
                    "#################"
            });
        } else {
            generateRandomMazeWithValidPath(13, 19);
        }
    }

    private void loadFromStrings(String[] map) {
        rows = map.length;
        cols = map[0].length();
        grid = new char[rows][cols];

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                grid[r][c] = map[r].charAt(c);

                if (grid[r][c] == 'S') {
                    start = new Point(c, r);
                } else if (grid[r][c] == 'E') {
                    exit = new Point(c, r);
                }
            }
        }
    }

    public void generateRandomMazeWithValidPath(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;

        int maxAttempts = 1000;
        boolean valid = false;

        for (int attempt = 0; attempt < maxAttempts; attempt++) {
            createRandomGrid();

            if (hasPathBFS()) {
                valid = true;
                break;
            }
        }

        // nếu random nhiều lần vẫn lỗi thì tạo map dự phòng
        if (!valid) {
            createFallbackMaze();
        }
    }

    private void createRandomGrid() {
        grid = new char[rows][cols];

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                if (r == 0 || r == rows - 1 || c == 0 || c == cols - 1) {
                    grid[r][c] = '#';
                } else {
                    // khoảng 30% là tường
                    grid[r][c] = random.nextDouble() < 0.30 ? '#' : '.';
                }
            }
        }

        start = new Point(1, 1);
        exit = new Point(cols - 2, rows - 2);

        // đảm bảo vùng start và exit không bị chặn cứng
        grid[start.y][start.x] = 'S';
        grid[exit.y][exit.x] = 'E';

        if (isInside(1, 2)) grid[1][2] = '.';
        if (isInside(2, 1)) grid[2][1] = '.';

        if (isInside(rows - 2, cols - 3)) grid[rows - 2][cols - 3] = '.';
        if (isInside(rows - 3, cols - 2)) grid[rows - 3][cols - 2] = '.';
    }

    private boolean hasPathBFS() {
        boolean[][] visited = new boolean[rows][cols];
        Queue<Point> queue = new LinkedList<>();

        queue.add(new Point(start.x, start.y));
        visited[start.y][start.x] = true;

        int[] dr = {-1, 1, 0, 0};
        int[] dc = {0, 0, -1, 1};

        while (!queue.isEmpty()) {
            Point current = queue.poll();

            if (current.x == exit.x && current.y == exit.y) {
                return true;
            }

            for (int i = 0; i < 4; i++) {
                int newRow = current.y + dr[i];
                int newCol = current.x + dc[i];

                if (!isInside(newRow, newCol)) continue;
                if (visited[newRow][newCol]) continue;
                if (grid[newRow][newCol] == '#') continue;

                visited[newRow][newCol] = true;
                queue.add(new Point(newCol, newRow));
            }
        }

        return false;
    }

    private boolean isInside(int row, int col) {
        return row >= 0 && row < rows && col >= 0 && col < cols;
    }

    private void createFallbackMaze() {
        loadFromStrings(new String[]{
                "###################",
                "#S....#...........#",
                "#.##..#.#####.###.#",
                "#.....#.....#...#.#",
                "#.#########.#.#.#.#",
                "#.........#.#.#...#",
                "#####.###.#.#.###.#",
                "#.....#...#.#...#.#",
                "#.#####.###.###.#.#",
                "#...............#E#",
                "###################"
        });
    }

    public boolean isWall(int row, int col) {
        return grid[row][col] == '#';
    }

    public boolean isExit(int row, int col) {
        return grid[row][col] == 'E';
    }

    public char getCell(int row, int col) {
        return grid[row][col];
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }

    public Point getStart() {
        return start;
    }

    public Point getExit() {
        return exit;
    }
}