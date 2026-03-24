package mazegame;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class GameFrame extends JFrame {
    private GamePanel gamePanel;

    public GameFrame() {
        setTitle("Maze Game - Java Swing");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(236, 240, 245));

        gamePanel = new GamePanel();

        JPanel root = new JPanel(new BorderLayout(12, 12));
        root.setBorder(new EmptyBorder(12, 12, 12, 12));
        root.setBackground(new Color(236, 240, 245));

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setOpaque(false);

        JLabel titleLabel = new JLabel("MAZE GAME");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        titleLabel.setForeground(new Color(35, 47, 62));

        JLabel subTitleLabel = new JLabel("Java Swing - OOP Project");
        subTitleLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        subTitleLabel.setForeground(new Color(110, 120, 135));

        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS));
        titlePanel.setOpaque(false);
        titlePanel.add(titleLabel);
        titlePanel.add(Box.createVerticalStrut(2));
        titlePanel.add(subTitleLabel);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        buttonPanel.setOpaque(false);

        JButton btnSample = createStyledButton("Stave", new Color(52, 152, 219));
        JButton btnRandom = createStyledButton("Map random", new Color(46, 204, 113));
        JButton btnRestart = createStyledButton("Restart", new Color(243, 156, 18));
        JButton btnExit = createStyledButton("Thoát", new Color(231, 76, 60));

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

        buttonPanel.add(btnSample);
        buttonPanel.add(btnRandom);
        buttonPanel.add(btnRestart);
        buttonPanel.add(btnExit);

        topPanel.add(titlePanel, BorderLayout.WEST);
        topPanel.add(buttonPanel, BorderLayout.EAST);

        JPanel centerWrapper = new JPanel(new BorderLayout());
        centerWrapper.setBackground(Color.WHITE);
        centerWrapper.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 225, 232), 1),
                new EmptyBorder(10, 10, 10, 10)
        ));
        centerWrapper.add(gamePanel, BorderLayout.CENTER);

        root.add(topPanel, BorderLayout.NORTH);
        root.add(centerWrapper, BorderLayout.CENTER);

        add(root, BorderLayout.CENTER);

        refreshFrame();
        setResizable(false);
        setVisible(true);
    }

    private JButton createStyledButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setFocusPainted(false);
        button.setFont(new Font("Segoe UI", Font.BOLD, 13));
        button.setForeground(Color.WHITE);
        button.setBackground(bgColor);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setBorder(new EmptyBorder(10, 18, 10, 18));
        return button;
    }

    private void refreshFrame() {
        pack();
        setLocationRelativeTo(null);
        SwingUtilities.invokeLater(() -> gamePanel.requestFocusInWindow());
    }
}