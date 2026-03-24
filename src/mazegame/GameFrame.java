package mazegame;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class GameFrame extends JFrame {
    private final CardLayout cardLayout;
    private final JPanel cards;
    private final GamePanel gamePanel;

    public GameFrame() {
        setTitle("Maze Game - Java Swing");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        cardLayout = new CardLayout();
        cards = new JPanel(cardLayout);
        cards.setBackground(new Color(236, 241, 247));

        gamePanel = new GamePanel();

        cards.add(createStartScreen(), "START");
        cards.add(createGameScreen(), "GAME");

        setContentPane(cards);

        showStartScreen();
        setResizable(false);
        setVisible(true);
    }

    private JPanel createStartScreen() {
        return new StartPanel(
                () -> {
                    gamePanel.startSampleFromLevel1();
                    showGameScreen();
                },
                () -> {
                    gamePanel.initRandomLevel();
                    showGameScreen();
                }
        );
    }

    private JPanel createGameScreen() {
        JPanel root = new JPanel(new BorderLayout(0, 14));
        root.setBackground(new Color(236, 241, 247));
        root.setBorder(new EmptyBorder(14, 14, 14, 14));

        JPanel toolbar = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        toolbar.setOpaque(false);

        ThemeButton btnBack = new ThemeButton("Menu", IconFactory.createBackIcon(), new Color(99, 110, 114));
        ThemeButton btnSample = new ThemeButton("Màn Chơi", IconFactory.createMapIcon(), new Color(52, 152, 219));
        ThemeButton btnRandom = new ThemeButton("Map random", IconFactory.createRandomIcon(), new Color(46, 204, 113));
        ThemeButton btnRestart = new ThemeButton("Restart", IconFactory.createRestartIcon(), new Color(243, 156, 18));
        ThemeButton btnExit = new ThemeButton("Thoát", IconFactory.createExitIcon(), new Color(231, 76, 60));

      btnBack.addActionListener(e -> {
    gamePanel.stopGame();
    showStartScreen();
});

        btnSample.addActionListener(e -> {
            gamePanel.startSampleFromLevel1();
            refreshFrame();
        });

        btnRandom.addActionListener(e -> {
            gamePanel.initRandomLevel();
            refreshFrame();
        });

        btnRestart.addActionListener(e -> {
            gamePanel.restartCurrent();
            refreshFrame();
        });

        btnExit.addActionListener(e -> System.exit(0));

        toolbar.add(btnBack);
        toolbar.add(btnSample);
        toolbar.add(btnRandom);
        toolbar.add(btnRestart);
        toolbar.add(btnExit);

       ShadowPanel shadowCard = new ShadowPanel();

JPanel inner = new JPanel(new BorderLayout(18, 0));
inner.setOpaque(false);
inner.setBorder(new EmptyBorder(10, 10, 10, 10));

inner.add(gamePanel, BorderLayout.CENTER);
inner.add(new SideInfoPanel(gamePanel), BorderLayout.EAST);

shadowCard.add(inner, BorderLayout.CENTER);

root.add(toolbar, BorderLayout.NORTH);
root.add(shadowCard, BorderLayout.CENTER);

        return root;
    }

    private void showStartScreen() {
    gamePanel.stopGame();
    cardLayout.show(cards, "START");
    pack();
    setLocationRelativeTo(null);
}

    private void showGameScreen() {
        cardLayout.show(cards, "GAME");
        refreshFrame();
    }

    private void refreshFrame() {
        pack();
        setLocationRelativeTo(null);
        SwingUtilities.invokeLater(() -> gamePanel.requestFocusInWindow());
    }
}