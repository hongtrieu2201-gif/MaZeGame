package mazegame;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class SideInfoPanel extends JPanel {
    private final GamePanel gamePanel;

    public SideInfoPanel(GamePanel gamePanel) {
        this.gamePanel = gamePanel;

        setOpaque(false);
        setPreferredSize(new Dimension(270, 0));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        add(createSectionTitle("Mục tiêu"));
        add(Box.createVerticalStrut(10));
        add(createInfoCard(
                "Objective",
                new String[]{
                        "• Đi tới ô Exit",
                        "• Né quái đi tuần",
                        "• Nhặt coin để lấy sao"
                }
        ));

        add(Box.createVerticalStrut(18));
        add(createSectionTitle("Điều khiển"));
        add(Box.createVerticalStrut(10));
        add(createInfoCard(
                "Controls",
                new String[]{
                        "• W A S D",
                        "• Phím mũi tên",
                        "• Menu để quay lại"
                }
        ));

        add(Box.createVerticalStrut(18));
        add(createSectionTitle("Ký hiệu"));
        add(Box.createVerticalStrut(10));
        add(createLegendCard());

        add(Box.createVerticalStrut(18));
        add(createSectionTitle("Trạng thái"));
        add(Box.createVerticalStrut(10));
        add(createDynamicStatsCard());

        add(Box.createVerticalGlue());

        // Tự repaint định kỳ để stats cập nhật theo game
        Timer timer = new Timer(200, e -> repaint());
        timer.start();
    }

    private JLabel createSectionTitle(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Segoe UI", Font.BOLD, 18));
        label.setForeground(new Color(36, 52, 71));
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        return label;
    }

    private JPanel createInfoCard(String title, String[] lines) {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(new Color(248, 250, 252));
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(226, 232, 238), 1),
                new EmptyBorder(16, 16, 16, 16)
        ));
        card.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 15));
        titleLabel.setForeground(new Color(44, 62, 80));

        card.add(titleLabel);
        card.add(Box.createVerticalStrut(10));

        for (String line : lines) {
            JLabel item = new JLabel(line);
            item.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            item.setForeground(new Color(92, 107, 121));
            card.add(item);
            card.add(Box.createVerticalStrut(6));
        }

        return card;
    }

    private JPanel createLegendCard() {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(new Color(248, 250, 252));
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(226, 232, 238), 1),
                new EmptyBorder(16, 16, 16, 16)
        ));
        card.setAlignmentX(Component.LEFT_ALIGNMENT);

        card.add(createLegendRow("Bắt Đầu", new Color(46, 204, 113)));
        card.add(Box.createVerticalStrut(10));
        card.add(createLegendRow("Enemy", new Color(52, 152, 219)));
        card.add(Box.createVerticalStrut(10));
        card.add(createLegendRow("Coin", new Color(241, 196, 15)));
        card.add(Box.createVerticalStrut(10));
        card.add(createLegendRow("Kết Thúc", new Color(231, 76, 60)));

        return card;
    }

    private JPanel createLegendRow(String text, Color color) {
        JPanel row = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        row.setOpaque(false);

        JComponent dot = new JComponent() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(color);
                g2.fillOval(0, 0, 16, 16);
                g2.dispose();
            }

            @Override
            public Dimension getPreferredSize() {
                return new Dimension(16, 16);
            }
        };

        JLabel label = new JLabel(text);
        label.setFont(new Font("Segoe UI", Font.BOLD, 14));
        label.setForeground(new Color(60, 72, 88));

        row.add(dot);
        row.add(label);
        return row;
    }

    private JPanel createDynamicStatsCard() {
        return new JPanel() {
            {
                setOpaque(false);
                setAlignmentX(Component.LEFT_ALIGNMENT);
            }

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
            }

            @Override
            public Dimension getPreferredSize() {
                return new Dimension(240, 170);
            }

            @Override
            protected void paintChildren(Graphics g) {
                removeAll();

                GameState state = gamePanel.getGameState();

                JPanel card = new JPanel();
                card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
                card.setBackground(new Color(248, 250, 252));
                card.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(new Color(226, 232, 238), 1),
                        new EmptyBorder(16, 16, 16, 16)
                ));
                card.setAlignmentX(Component.LEFT_ALIGNMENT);

                card.add(createStatLabel("Level", String.valueOf(state.getLevel())));
                card.add(Box.createVerticalStrut(8));
                card.add(createStatLabel("Steps", String.valueOf(state.getSteps())));
                card.add(Box.createVerticalStrut(8));
                card.add(createStatLabel("Lives", String.valueOf(state.getLives())));
                card.add(Box.createVerticalStrut(8));
                card.add(createStatLabel("Coins", state.getCoinsCollected() + "/" + state.getTotalCoins()));
                card.add(Box.createVerticalStrut(8));
                card.add(createStatLabel("Stars", state.getStarsText()));

                setLayout(new BorderLayout());
                add(card, BorderLayout.CENTER);

                super.paintChildren(g);
            }
        };
    }

    private JPanel createStatLabel(String label, String value) {
        JPanel row = new JPanel(new BorderLayout());
        row.setOpaque(false);

        JLabel left = new JLabel(label);
        left.setFont(new Font("Segoe UI", Font.BOLD, 14));
        left.setForeground(new Color(60, 72, 88));

        JLabel right = new JLabel(value);
        right.setFont(new Font("Segoe UI", Font.BOLD, 14));
        right.setForeground(new Color(44, 62, 80));

        row.add(left, BorderLayout.WEST);
        row.add(right, BorderLayout.EAST);
        return row;
    }
}