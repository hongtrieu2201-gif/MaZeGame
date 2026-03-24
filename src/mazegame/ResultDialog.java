package mazegame;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class ResultDialog extends JDialog {

    public ResultDialog(
            Window owner,
            String title,
            String message,
            String stepsText,
            String coinsText,
            String starsText,
            String buttonText,
            Color accentColor
    ) {
        super(owner);
        setModal(true);
        setUndecorated(true);

        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(new Color(248, 250, 252));
        root.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(222, 228, 235), 1),
                new EmptyBorder(18, 18, 18, 18)
        ));

        JPanel content = new JPanel(new BorderLayout(14, 0));
        content.setOpaque(false);

        JPanel iconPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                g2.setColor(accentColor);
                g2.fillOval(8, 8, 44, 44);

                g2.setColor(Color.WHITE);
                g2.setFont(new Font("Segoe UI", Font.BOLD, 26));
                g2.drawString("★", 19, 40);

                g2.dispose();
            }
        };
        iconPanel.setOpaque(false);
        iconPanel.setPreferredSize(new Dimension(60, 60));

        JPanel textPanel = new JPanel();
        textPanel.setOpaque(false);
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        titleLabel.setForeground(new Color(34, 49, 63));

        JLabel messageLabel = new JLabel(message);
        messageLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        messageLabel.setForeground(new Color(97, 109, 126));

        JPanel statsPanel = new JPanel(new GridLayout(3, 1, 0, 8));
        statsPanel.setOpaque(false);
        statsPanel.setBorder(new EmptyBorder(14, 0, 14, 0));

        statsPanel.add(createStatCard("Steps", stepsText));
        statsPanel.add(createStatCard("Coins", coinsText));
        statsPanel.add(createStatCard("Stars", starsText));

        ThemeButton okButton = new ThemeButton(buttonText, IconFactory.createPlayIcon(), accentColor);
        okButton.setPreferredSize(new Dimension(150, 44));
        okButton.setMaximumSize(new Dimension(150, 44));
        okButton.addActionListener(e -> dispose());

        JPanel btnWrap = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        btnWrap.setOpaque(false);
        btnWrap.add(okButton);

        textPanel.add(titleLabel);
        textPanel.add(Box.createVerticalStrut(6));
        textPanel.add(messageLabel);
        textPanel.add(statsPanel);
        textPanel.add(btnWrap);

        content.add(iconPanel, BorderLayout.WEST);
        content.add(textPanel, BorderLayout.CENTER);

        root.add(content, BorderLayout.CENTER);
        setContentPane(root);

        pack();
        setSize(new Dimension(430, 330));
        setLocationRelativeTo(owner);
    }

    private JPanel createStatCard(String label, String value) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(230, 235, 241), 1),
                new EmptyBorder(10, 12, 10, 12)
        ));

        JLabel left = new JLabel(label);
        left.setFont(new Font("Segoe UI", Font.BOLD, 14));
        left.setForeground(new Color(58, 71, 86));

        JLabel right = new JLabel(value);
        right.setFont(new Font("Segoe UI", Font.BOLD, 14));
        right.setForeground(new Color(44, 62, 80));
        right.setHorizontalAlignment(SwingConstants.RIGHT);

        panel.add(left, BorderLayout.WEST);
        panel.add(right, BorderLayout.EAST);
        return panel;
    }

    public static void showWin(
            Component parent,
            String title,
            int steps,
            int coinsCollected,
            int totalCoins,
            String starsText
    ) {
        Window window = SwingUtilities.getWindowAncestor(parent);
        ResultDialog dialog = new ResultDialog(
                window,
                title,
                "Bạn đã hoàn thành màn chơi thành công.",
                String.valueOf(steps),
                coinsCollected + "/" + totalCoins,
                starsText,
                "OK",
                new Color(46, 204, 113)
        );
        dialog.setVisible(true);
    }

    public static void showLose(
            Component parent,
            int livesLeft
    ) {
        Window window = SwingUtilities.getWindowAncestor(parent);
        ResultDialog dialog = new ResultDialog(
                window,
                "Bị quái bắt!",
                "Bạn bị quái bắt và được đưa về ô Start.",
                "-",
                "-",
                "Lives: " + livesLeft,
                "Tiếp tục",
                new Color(231, 76, 60)
        );
        dialog.setVisible(true);
    }

    public static void showGameOver(
            Component parent
    ) {
        Window window = SwingUtilities.getWindowAncestor(parent);
        ResultDialog dialog = new ResultDialog(
                window,
                "Game Over",
                "Bạn đã hết mạng. Hãy thử lại.",
                "-",
                "-",
                "☆☆☆",
                "Chơi lại",
                new Color(192, 57, 43)
        );
        dialog.setVisible(true);
    }
}