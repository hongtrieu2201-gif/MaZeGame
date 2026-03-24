package mazegame;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.awt.Point;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;
public class GamePanel extends JPanel {
    private Maze maze;
    private Player player;
    private GameState gameState;
private boolean gameRunning = false;
    private static final int TILE_SIZE = 44;
    private static final int INFO_HEIGHT = 70;
    private static final int PADDING = 20;

    private boolean randomMode = false;
    private boolean animating = false;
private List<Enemy> enemies;
private Timer enemyTimer;
    private double renderRow;
    private double renderCol;

    private Timer animationTimer;
    private Image playerImage;

    public GamePanel() {
        setFocusable(true);
        setOpaque(false);

        playerImage = loadPlayerImage();
        gameState = new GameState();

        startSampleFromLevel1();
        setupKeyBindings();
    }

   public void startSampleFromLevel1() {
    randomMode = false;
    gameState.setLevel(1);
    gameRunning = true;

    maze = new Maze();
    maze.loadLevel(1);

    player = new Player(maze.getStart().y, maze.getStart().x);
    renderRow = player.getRow();
    renderCol = player.getCol();

    gameState.reset();
    stopAnimation();

    createEnemies();
    startEnemyTimer();

    updatePanelSize();
    repaint();
    requestFocusInWindow();
}

  public void initSampleLevel() {
    randomMode = false;
    gameRunning = true;

    maze = new Maze();
    maze.loadLevel(gameState.getLevel());

    player = new Player(maze.getStart().y, maze.getStart().x);
    renderRow = player.getRow();
    renderCol = player.getCol();

    gameState.reset();
    stopAnimation();

    createEnemies();
    startEnemyTimer();

    updatePanelSize();
    repaint();
    requestFocusInWindow();
}

  public void initRandomLevel() {
    randomMode = true;
    gameState.setLevel(1);
    gameRunning = true;

    maze = new Maze();
    maze.generateRandomMazeWithValidPath(13, 19);

    player = new Player(maze.getStart().y, maze.getStart().x);
    renderRow = player.getRow();
    renderCol = player.getCol();

    gameState.reset();
    stopAnimation();

    createEnemies();
    startEnemyTimer();

    updatePanelSize();
    repaint();
    requestFocusInWindow();
}

    public void restartCurrent() {
    stopEnemyTimer();

    if (randomMode) {
        initRandomLevel();
    } else {
        initSampleLevel();
    }
}

    private void updatePanelSize() {
        int width = maze.getCols() * TILE_SIZE + PADDING * 2;
        int height = maze.getRows() * TILE_SIZE + INFO_HEIGHT + PADDING * 2;
        setPreferredSize(new Dimension(width, height));
        revalidate();

        Window window = SwingUtilities.getWindowAncestor(this);
        if (window != null) {
            window.pack();
        }
    }

    private void setupKeyBindings() {
        bindKey("UP", -1, 0);
        bindKey("DOWN", 1, 0);
        bindKey("LEFT", 0, -1);
        bindKey("RIGHT", 0, 1);

        bindKey("W", -1, 0);
        bindKey("S", 1, 0);
        bindKey("A", 0, -1);
        bindKey("D", 0, 1);
    }

    private void bindKey(String key, int dRow, int dCol) {
        getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(key), key);
        getActionMap().put(key, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                movePlayer(dRow, dCol);
            }
        });
    }

    private void movePlayer(int dRow, int dCol) {
        if (gameState.isWon() || animating) return;

        int newRow = player.getRow() + dRow;
        int newCol = player.getCol() + dCol;

        if (newRow < 0 || newRow >= maze.getRows() || newCol < 0 || newCol >= maze.getCols()) {
            return;
        }

        if (maze.isWall(newRow, newCol)) {
            return;
        }

        gameState.increaseSteps();
        animateMove(player.getRow(), player.getCol(), newRow, newCol);
    }

    private void animateMove(int oldRow, int oldCol, int newRow, int newCol) {
        animating = true;
        stopAnimation();

        final int totalFrames = 9;
        final int[] frame = {0};

        animationTimer = new Timer(15, e -> {
            frame[0]++;
            double t = frame[0] / (double) totalFrames;
            double smooth = easeOutCubic(t);

            renderRow = oldRow + (newRow - oldRow) * smooth;
            renderCol = oldCol + (newCol - oldCol) * smooth;

            repaint();

            if (frame[0] >= totalFrames) {
                stopAnimation();
                animating = false;

              player.moveTo(newRow, newCol);
renderRow = newRow;
renderCol = newCol;

checkEnemyCollision();
if (!gameState.isWon()) {
    checkWinAfterMove(newRow, newCol);
}

repaint();
            }
        });

        animationTimer.start();
    }

    private double easeOutCubic(double t) {
        return 1 - Math.pow(1 - t, 3);
    }

private void checkWinAfterMove(int row, int col) {
    if (!maze.isExit(row, col)) return;

    gameRunning = false;
    stopEnemyTimer();
    gameState.setWon(true);

    if (randomMode) {
        JOptionPane.showMessageDialog(this,
                "Bạn đã thoát mê cung random!\nSố bước: " + gameState.getSteps());
        requestFocusInWindow();
        return;
    }

    int currentLevel = gameState.getLevel();
    JOptionPane.showMessageDialog(this,
            "Hoàn thành level " + currentLevel + "!\nSố bước: " + gameState.getSteps());

    gameState.nextLevel();
    initSampleLevel();

    Window window = SwingUtilities.getWindowAncestor(this);
    if (window != null) {
        window.pack();
        window.setLocationRelativeTo(null);
    }

    requestFocusInWindow();
}

    private void stopAnimation() {
        if (animationTimer != null && animationTimer.isRunning()) {
            animationTimer.stop();
        }
    }

private Image loadPlayerImage() {
    try {
        java.io.File file = new java.io.File("src/image/player.png");

        System.out.println("Dang tim anh tai: " + file.getAbsolutePath());

        if (file.exists()) {
            BufferedImage img = ImageIO.read(file);
            System.out.println("Load anh thanh cong!");
            return img;
        } else {
            System.out.println("Khong tim thay file player.png");
        }
    } catch (Exception e) {
        e.printStackTrace();
    }

    System.out.println("Dang dung fallback player");
    return createFallbackPlayerImage();
}

    private Image createFallbackPlayerImage() {
        BufferedImage img = new BufferedImage(96, 96, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = img.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setColor(new Color(52, 120, 246));
        g2.fillOval(10, 8, 76, 76);

        g2.setColor(Color.WHITE);
        g2.fillOval(24, 22, 12, 12);
        g2.fillOval(60, 22, 12, 12);

        g2.setColor(new Color(30, 60, 120));
        g2.fillOval(28, 26, 5, 5);
        g2.fillOval(64, 26, 5, 5);

        g2.setStroke(new BasicStroke(4f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        g2.setColor(Color.WHITE);
        g2.drawArc(28, 36, 40, 22, 180, 180);

        g2.setColor(new Color(255, 219, 172));
        g2.fillRoundRect(28, 56, 40, 26, 12, 12);

        g2.dispose();
        return img;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (maze == null || player == null) return;

        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int boardX = PADDING;
        int boardY = INFO_HEIGHT;
        int boardWidth = maze.getCols() * TILE_SIZE;
        int boardHeight = maze.getRows() * TILE_SIZE;

        drawInfoPanel(g2, boardX, 16, boardWidth);
        drawBoardShadow(g2, boardX, boardY, boardWidth, boardHeight);

        for (int r = 0; r < maze.getRows(); r++) {
            for (int c = 0; c < maze.getCols(); c++) {
                char cell = maze.getCell(r, c);
                int x = boardX + c * TILE_SIZE;
                int y = boardY + r * TILE_SIZE;

                if (cell == '#') {
                    g2.setColor(new Color(58, 66, 77));
                    g2.fillRoundRect(x, y, TILE_SIZE - 2, TILE_SIZE - 2, 12, 12);

                    g2.setColor(new Color(86, 96, 108));
                    g2.drawRoundRect(x, y, TILE_SIZE - 2, TILE_SIZE - 2, 12, 12);
                } else if (cell == 'S') {
                    g2.setColor(new Color(204, 246, 214));
                    g2.fillRoundRect(x, y, TILE_SIZE - 2, TILE_SIZE - 2, 10, 10);

                    g2.setColor(new Color(39, 174, 96));
                    g2.setFont(new Font("Segoe UI", Font.BOLD, 14));
                    g2.drawString("S", x + 16, y + 26);
                } else if (cell == 'E') {
                    g2.setColor(new Color(255, 223, 223));
                    g2.fillRoundRect(x, y, TILE_SIZE - 2, TILE_SIZE - 2, 10, 10);

                    g2.setColor(new Color(192, 57, 43));
                    g2.setFont(new Font("Segoe UI", Font.BOLD, 14));
                    g2.drawString("E", x + 16, y + 26);
                } else {
                    g2.setColor(new Color(248, 250, 252));
                    g2.fillRoundRect(x, y, TILE_SIZE - 2, TILE_SIZE - 2, 10, 10);

                    g2.setColor(new Color(228, 233, 239));
                    g2.drawRoundRect(x, y, TILE_SIZE - 2, TILE_SIZE - 2, 10, 10);
                }
            }
        }
drawEnemies(g2, boardX, boardY);
        drawPlayer(g2, boardX, boardY);
        g2.dispose();
    }

    private void drawInfoPanel(Graphics2D g2, int x, int y, int width) {
        g2.setColor(new Color(244, 247, 251));
        g2.fillRoundRect(x, y, width, 40, 18, 18);

        g2.setColor(new Color(223, 229, 236));
        g2.drawRoundRect(x, y, width, 40, 18, 18);

        String modeText = randomMode ? "Random" : "Sample";
        String levelText = randomMode ? "-" : String.valueOf(gameState.getLevel());

        g2.setFont(new Font("Segoe UI", Font.BOLD, 14));
        g2.setColor(new Color(44, 62, 80));
        g2.drawString(
                "Level: " + levelText + "   |   Steps: " + gameState.getSteps() + "   |   Mode: " + modeText,
                x + 14, y + 25
        );

        g2.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        g2.setColor(new Color(115, 127, 141));
        g2.drawString("Điều khiển: WASD hoặc phím mũi tên", x + width - 250, y + 25);
    }

    private void drawBoardShadow(Graphics2D g2, int x, int y, int width, int height) {
        for (int i = 0; i < 6; i++) {
            g2.setColor(new Color(0, 0, 0, 12 - i));
            g2.fillRoundRect(x + i, y + i, width, height, 22, 22);
        }

        g2.setColor(Color.WHITE);
        g2.fillRoundRect(x, y, width, height, 22, 22);
    }

 private void drawPlayer(Graphics2D g2, int boardX, int boardY) {
    int px = boardX + (int) Math.round(renderCol * TILE_SIZE);
    int py = boardY + (int) Math.round(renderRow * TILE_SIZE);

    int drawSize = TILE_SIZE - 6;   // ảnh to gần full ô
    int offsetX = (TILE_SIZE - drawSize) / 2;
    int offsetY = (TILE_SIZE - drawSize) / 2;

    // bóng dưới chân
    g2.setColor(new Color(0, 0, 0, 30));
    g2.fillOval(px + 10, py + TILE_SIZE - 10, TILE_SIZE - 20, 10);

    // vẽ ảnh player
    g2.drawImage(
            playerImage,
            px + offsetX,
            py + offsetY,
            drawSize,
            drawSize,
            null
    );
}
private void createEnemies() {
    enemies = new ArrayList<>();

    Set<Point> safePath = findShortestPathCells();

    int enemyCount;
    if (randomMode) {
        enemyCount = 2;
    } else {
        if (gameState.getLevel() == 1) {
            enemyCount = 2;
        } else if (gameState.getLevel() == 2) {
            enemyCount = 3;
        } else {
            enemyCount = 4;
        }
    }

    int maxAttempts = 500;
    int attempts = 0;

    while (enemies.size() < enemyCount && attempts < maxAttempts) {
        attempts++;

        int row = 1 + (int) (Math.random() * (maze.getRows() - 2));
        int col = 1 + (int) (Math.random() * (maze.getCols() - 2));

        int[][] dirs = {
            {-1, 0},
            {1, 0},
            {0, -1},
            {0, 1}
        };

        int dirIndex = (int) (Math.random() * dirs.length);
        int dirRow = dirs[dirIndex][0];
        int dirCol = dirs[dirIndex][1];

        if (!isValidEnemySpawn(row, col, dirRow, dirCol, safePath)) {
            continue;
        }

        enemies.add(new Enemy(row, col, dirRow, dirCol));
    }
}
private boolean isValidEnemySpawn(int row, int col, int dirRow, int dirCol, Set<Point> safePath) {
    
    if (maze.isWall(row, col)) {
        return false;
    }

  
    Point start = maze.getStartPoint();
    Point exit = maze.getExitPoint();

    if ((row == start.y && col == start.x) || (row == exit.y && col == exit.x)) {
        return false;
    }

    
    if (safePath.contains(new Point(col, row))) {
        return false;
    }

    
    if (Math.abs(row - start.y) + Math.abs(col - start.x) <= 2) {
        return false;
    }

   
    if (Math.abs(row - exit.y) + Math.abs(col - exit.x) <= 1) {
        return false;
    }

    
    int nextRow = row + dirRow;
    int nextCol = col + dirCol;

    if (nextRow < 0 || nextRow >= maze.getRows() || nextCol < 0 || nextCol >= maze.getCols()) {
        return false;
    }

    if (maze.isWall(nextRow, nextCol)) {
        return false;
    }

    // Không đứng đè hoặc quá sát quái khác
    for (Enemy enemy : enemies) {
        int dist = Math.abs(enemy.getRow() - row) + Math.abs(enemy.getCol() - col);
        if (dist <= 2) {
            return false;
        }
    }

    return true;
}
 private void addEnemyIfSafe(int row, int col, int dirRow, int dirCol, Set<Point> safePath) {
    Point p = new Point(col, row);

    if (!maze.isWall(row, col) && !safePath.contains(p)) {
        enemies.add(new Enemy(row, col, dirRow, dirCol));
    }
}

 private void stopEnemyTimer() {
    if (enemyTimer != null && enemyTimer.isRunning()) {
        enemyTimer.stop();
    }
}
private void startEnemyTimer() {
    stopEnemyTimer();

    enemyTimer = new Timer(350, e -> {
        if (!gameRunning) return;
        if (gameState.isWon() || animating) return;

        if (enemies != null) {
            for (Enemy enemy : enemies) {
                enemy.move(maze);
            }
        }

        checkEnemyCollision();
        repaint();
    });

    enemyTimer.start();
}
 private void checkEnemyCollision() {
     if (gameState.isWon()) return;
    if (enemies == null || player == null) return;

    for (Enemy enemy : enemies) {
        if (enemy.getRow() == player.getRow() && enemy.getCol() == player.getCol()) {
            stopEnemyTimer();
            stopAnimation();

            JOptionPane.showMessageDialog(this, "Bạn đã bị quái bắt!");
            restartCurrent();
            return;
        }
    }
}
 private void drawEnemies(Graphics2D g2, int boardX, int boardY) {
    if (enemies == null) return;

    for (Enemy enemy : enemies) {
        int ex = boardX + enemy.getCol() * TILE_SIZE;
        int ey = boardY + enemy.getRow() * TILE_SIZE;

        g2.setColor(new Color(0, 0, 0, 25));
        g2.fillOval(ex + 10, ey + TILE_SIZE - 10, TILE_SIZE - 20, 10);

        g2.setColor(new Color(231, 76, 60));
        g2.fillOval(ex + 7, ey + 7, TILE_SIZE - 14, TILE_SIZE - 14);

        g2.setColor(Color.WHITE);
        g2.fillOval(ex + 13, ey + 14, 7, 7);
        g2.fillOval(ex + 24, ey + 14, 7, 7);

        g2.setColor(Color.BLACK);
        g2.fillOval(ex + 15, ey + 16, 3, 3);
        g2.fillOval(ex + 26, ey + 16, 3, 3);

        g2.setColor(new Color(120, 0, 0));
        g2.fillArc(ex + 13, ey + 22, 18, 10, 180, 180);
    }
}
 private Set<Point> findShortestPathCells() {
    int rows = maze.getRows();
    int cols = maze.getCols();

    boolean[][] visited = new boolean[rows][cols];
    Point[][] parent = new Point[rows][cols];
    Queue<Point> queue = new LinkedList<>();

    Point start = maze.getStartPoint();
    Point exit = maze.getExitPoint();

    queue.add(new Point(start.x, start.y));
    visited[start.y][start.x] = true;

    int[] dr = {-1, 1, 0, 0};
    int[] dc = {0, 0, -1, 1};

    while (!queue.isEmpty()) {
        Point cur = queue.poll();

        if (cur.x == exit.x && cur.y == exit.y) {
            break;
        }

        for (int i = 0; i < 4; i++) {
            int nr = cur.y + dr[i];
            int nc = cur.x + dc[i];

            if (nr < 0 || nr >= rows || nc < 0 || nc >= cols) continue;
            if (visited[nr][nc]) continue;
            if (maze.isWall(nr, nc)) continue;

            visited[nr][nc] = true;
            parent[nr][nc] = cur;
            queue.add(new Point(nc, nr));
        }
    }

    Set<Point> pathCells = new HashSet<>();
    Point cur = new Point(exit.x, exit.y);

    while (cur != null) {
        pathCells.add(new Point(cur.x, cur.y));
        cur = parent[cur.y][cur.x];
    }

    return pathCells;
}
public void stopGame() {
    gameRunning = false;
    stopEnemyTimer();
    stopAnimation();
}
}