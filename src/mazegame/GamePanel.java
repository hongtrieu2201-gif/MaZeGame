package mazegame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class GamePanel extends JPanel {
    private Maze maze;
    private Player player;
    private GameState gameState;

    private final int TILE_SIZE = 42;
    private boolean randomMode = false;

    public GamePanel() {
        setFocusable(true);
        setBackground(Color.WHITE);

        gameState = new GameState();
        startSampleFromLevel1();
        setupKeyBindings();
    }

    public void startSampleFromLevel1() {
        randomMode = false;
        gameState.setLevel(1);
        maze = new Maze();
        maze.loadLevel(1);
        player = new Player(maze.getStart().y, maze.getStart().x);
        gameState.reset();

        updatePanelSize();
        repaint();
        requestFocusInWindow();
    }

    public void initSampleLevel() {
        randomMode = false;
        maze = new Maze();
        maze.loadLevel(gameState.getLevel());
        player = new Player(maze.getStart().y, maze.getStart().x);
        gameState.reset();

        updatePanelSize();
        repaint();
        requestFocusInWindow();
    }

    public void initRandomLevel() {
        randomMode = true;
        maze = new Maze();
        maze.generateRandomMazeWithValidPath(13, 19);
        player = new Player(maze.getStart().y, maze.getStart().x);
        gameState.reset();

        updatePanelSize();
        repaint();
        requestFocusInWindow();
    }

    public void restartCurrent() {
        if (randomMode) {
            initRandomLevel();
        } else {
            initSampleLevel();
        }
    }

    private void updatePanelSize() {
        int width = maze.getCols() * TILE_SIZE + 30;
        int height = maze.getRows() * TILE_SIZE + 95;
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
        if (gameState.isWon()) return;

        int newRow = player.getRow() + dRow;
        int newCol = player.getCol() + dCol;

        if (newRow < 0 || newRow >= maze.getRows() || newCol < 0 || newCol >= maze.getCols()) {
            return;
        }

        if (maze.isWall(newRow, newCol)) {
            return;
        }

        player.moveTo(newRow, newCol);
        gameState.increaseSteps();

        if (maze.isExit(newRow, newCol)) {
            gameState.setWon(true);

            if (randomMode) {
                JOptionPane.showMessageDialog(this,
                        "Bạn đã thoát mê cung random!\nSố bước: " + gameState.getSteps());
            } else {
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
                return;
            }
        }

        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (maze == null || player == null) return;

        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int boardX = 15;
        int boardY = 65;

        drawInfoPanel(g2, 15, 12);

        for (int r = 0; r < maze.getRows(); r++) {
            for (int c = 0; c < maze.getCols(); c++) {
                char cell = maze.getCell(r, c);
                int x = boardX + c * TILE_SIZE;
                int y = boardY + r * TILE_SIZE;

                if (cell == '#') {
                    g2.setColor(new Color(60, 70, 82));
                    g2.fillRoundRect(x, y, TILE_SIZE - 2, TILE_SIZE - 2, 12, 12);

                    g2.setColor(new Color(86, 101, 115));
                    g2.drawRoundRect(x, y, TILE_SIZE - 2, TILE_SIZE - 2, 12, 12);
                } else if (cell == 'S') {
                    g2.setColor(new Color(198, 246, 213));
                    g2.fillRoundRect(x, y, TILE_SIZE - 2, TILE_SIZE - 2, 10, 10);

                    g2.setColor(new Color(39, 174, 96));
                    g2.setFont(new Font("Segoe UI", Font.BOLD, 14));
                    g2.drawString("S", x + 15, y + 25);
                } else if (cell == 'E') {
                    g2.setColor(new Color(255, 220, 220));
                    g2.fillRoundRect(x, y, TILE_SIZE - 2, TILE_SIZE - 2, 10, 10);

                    g2.setColor(new Color(192, 57, 43));
                    g2.setFont(new Font("Segoe UI", Font.BOLD, 14));
                    g2.drawString("E", x + 15, y + 25);
                } else {
                    g2.setColor(new Color(247, 249, 252));
                    g2.fillRoundRect(x, y, TILE_SIZE - 2, TILE_SIZE - 2, 10, 10);

                    g2.setColor(new Color(225, 230, 236));
                    g2.drawRoundRect(x, y, TILE_SIZE - 2, TILE_SIZE - 2, 10, 10);
                }
            }
        }

        int px = boardX + player.getCol() * TILE_SIZE;
        int py = boardY + player.getRow() * TILE_SIZE;

        g2.setColor(new Color(52, 120, 246));
        g2.fillOval(px + 7, py + 7, TILE_SIZE - 16, TILE_SIZE - 16);

        g2.setColor(Color.WHITE);
        g2.setStroke(new BasicStroke(2f));
        g2.drawOval(px + 7, py + 7, TILE_SIZE - 16, TILE_SIZE - 16);

        g2.dispose();
    }

    private void drawInfoPanel(Graphics2D g2, int x, int y) {
        g2.setColor(new Color(243, 246, 250));
        g2.fillRoundRect(x, y, 430, 38, 18, 18);

        g2.setColor(new Color(220, 226, 232));
        g2.drawRoundRect(x, y, 430, 38, 18, 18);

        String modeText = randomMode ? "Random" : "Sample";
        String info = "Level: " + gameState.getLevel()
                + "    |    Steps: " + gameState.getSteps()
                + "    |    Mode: " + modeText;

        g2.setFont(new Font("Segoe UI", Font.BOLD, 14));
        g2.setColor(new Color(44, 62, 80));
        g2.drawString(info, x + 14, y + 24);
    }
}