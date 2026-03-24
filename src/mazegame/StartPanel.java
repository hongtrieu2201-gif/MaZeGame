package mazegame;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class StartPanel extends JPanel {
    private final Runnable onStartSample;
    private final Runnable onStartRandom;

    public StartPanel(Runnable onStartSample, Runnable onStartRandom) {
        this.onStartSample = onStartSample;
        this.onStartRandom = onStartRandom;

        setLayout(new GridBagLayout());
        setPreferredSize(new Dimension(1100, 720));
        setOpaque(false);

        buildUI();
    }

    private void buildUI() {
        ShadowPanel card = new ShadowPanel();
        card.setPreferredSize(new Dimension(900, 620));

        JPanel content = new JPanel(new BorderLayout(28, 0));
        content.setOpaque(false);
        content.setBorder(new EmptyBorder(28, 28, 28, 28));

        content.add(buildLeftPanel(), BorderLayout.WEST);
        content.add(buildRightPanel(), BorderLayout.CENTER);

        card.add(content, BorderLayout.CENTER);
        add(card);
    }

    private JPanel buildLeftPanel() {
        JPanel left = new JPanel();
        left.setOpaque(false);
        left.setPreferredSize(new Dimension(420, 0));
        left.setLayout(new BoxLayout(left, BoxLayout.Y_AXIS));

        JLabel badge = new JLabel("JAVA SWING PROJECT");
        badge.setOpaque(true);
        badge.setBackground(new Color(232, 240, 252));
        badge.setForeground(new Color(52, 99, 184));
        badge.setFont(new Font("Segoe UI", Font.BOLD, 12));
        badge.setBorder(new EmptyBorder(8, 12, 8, 12));
        badge.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel title = new JLabel("<html>MAZE<br>GAME</html>");
        title.setFont(new Font("Segoe UI", Font.BOLD, 42));
        title.setForeground(new Color(24, 39, 58));
        title.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel sub = new JLabel("Java Swing • OOP • Animation");
        sub.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        sub.setForeground(new Color(103, 116, 132));
        sub.setAlignmentX(Component.LEFT_ALIGNMENT);

        JTextArea desc = new JTextArea(
                "Nhiệm vụ của bạn là đưa nhân vật từ Start đến Exit, "
                        + "né quái đi tuần và vượt qua mê cung một cách an toàn."
        );
        desc.setEditable(false);
        desc.setFocusable(false);
        desc.setOpaque(false);
        desc.setWrapStyleWord(true);
        desc.setLineWrap(true);
        desc.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        desc.setForeground(new Color(72, 84, 96));
        desc.setAlignmentX(Component.LEFT_ALIGNMENT);
        desc.setMaximumSize(new Dimension(380, 120));

        JPanel infoBox = new JPanel();
        infoBox.setLayout(new BoxLayout(infoBox, BoxLayout.Y_AXIS));
        infoBox.setBackground(new Color(246, 248, 251));
        infoBox.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(227, 233, 240), 1),
                new EmptyBorder(18, 18, 18, 18)
        ));
        infoBox.setAlignmentX(Component.LEFT_ALIGNMENT);
        infoBox.setMaximumSize(new Dimension(390, 130));

        JLabel info1 = new JLabel("• Di chuyển bằng WASD hoặc phím mũi tên");
        JLabel info2 = new JLabel("•Né quái để tới đích an toàn ");
        JLabel info3 = new JLabel("• ib fb Phạm Hồng Triệu chỉ cho chơi=))");

        Font infoFont = new Font("Segoe UI", Font.PLAIN, 16);
        Color infoColor = new Color(74, 85, 99);

        info1.setFont(infoFont);
        info2.setFont(infoFont);
        info3.setFont(infoFont);

        info1.setForeground(infoColor);
        info2.setForeground(infoColor);
        info3.setForeground(infoColor);

        infoBox.add(info1);
        infoBox.add(Box.createVerticalStrut(10));
        infoBox.add(info2);
        infoBox.add(Box.createVerticalStrut(10));
        infoBox.add(info3);

        ThemeButton btnSample = new ThemeButton(
                "Chơi ",
                IconFactory.createPlayIcon(),
                new Color(52, 152, 219)
        );

     

        Dimension buttonSize = new Dimension(280, 48);

        btnSample.setPreferredSize(buttonSize);
       ;

        btnSample.setMaximumSize(buttonSize);
        
        btnSample.setMinimumSize(buttonSize);
       

        btnSample.setAlignmentX(Component.LEFT_ALIGNMENT);
        

        btnSample.addActionListener(e -> onStartSample.run());
       
        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        buttonPanel.setMaximumSize(new Dimension(280, 168));

        buttonPanel.add(btnSample);
        buttonPanel.add(Box.createVerticalStrut(12));
       

        
        

       left.add(badge);
left.add(Box.createVerticalStrut(16));
left.add(title);
left.add(Box.createVerticalStrut(8));
left.add(sub);
left.add(Box.createVerticalStrut(18));
left.add(desc);
left.add(Box.createVerticalStrut(18));
left.add(infoBox);
left.add(Box.createVerticalStrut(18));
left.add(buttonPanel);
left.add(Box.createVerticalStrut(16));


        return left;
    }

    private JPanel buildRightPanel() {
        return new PreviewPanel();
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();

        GradientPaint gp = new GradientPaint(
                0, 0, new Color(239, 244, 250),
                getWidth(), getHeight(), new Color(220, 230, 241)
        );
        g2.setPaint(gp);
        g2.fillRect(0, 0, getWidth(), getHeight());

        g2.setColor(new Color(255, 255, 255, 80));
        g2.fillOval(-20, 30, 260, 260);
        g2.fillOval(getWidth() - 260, 20, 250, 250);
        g2.fillOval(getWidth() / 2 - 120, getHeight() - 160, 240, 240);

        g2.dispose();
        super.paintComponent(g);
    }

    static class PreviewPanel extends JPanel {
        public PreviewPanel() {
            setOpaque(false);
            setPreferredSize(new Dimension(380, 0));
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            int cardX = 25;
            int cardY = 20;
            int cardW = getWidth() - 50;
            int cardH = getHeight() - 40;

            g2.setColor(new Color(244, 247, 251));
            g2.fillRoundRect(cardX, cardY, cardW, cardH, 28, 28);

            g2.setColor(new Color(226, 232, 238));
            g2.drawRoundRect(cardX, cardY, cardW, cardH, 28, 28);

            g2.setFont(new Font("Segoe UI", Font.BOLD, 22));
            g2.setColor(new Color(33, 47, 61));
            g2.drawString("Preview", cardX + 24, cardY + 40);

            drawMiniMaze(g2, cardX + 24, cardY + 65);
            drawFeatureBox(g2, cardX + 24, cardY + 305, "Player", new Color(52, 152, 219));
            drawFeatureBox(g2, cardX + 24, cardY + 360, "Enemy", new Color(231, 76, 60));
            drawFeatureBox(g2, cardX + 24, cardY + 415, "Exit", new Color(46, 204, 113));

            g2.dispose();
        }

        private void drawMiniMaze(Graphics2D g2, int startX, int startY) {
            int tile = 30;

            int[][] map = {
                    {1,1,1,1,1,1,1,1},
                    {1,2,0,0,0,0,0,1},
                    {1,0,1,1,1,0,0,1},
                    {1,0,0,0,1,0,3,1},
                    {1,1,1,0,1,0,0,1},
                    {1,0,0,0,0,0,4,1},
                    {1,1,1,1,1,1,1,1}
            };

            for (int r = 0; r < map.length; r++) {
                for (int c = 0; c < map[r].length; c++) {
                    int x = startX + c * tile;
                    int y = startY + r * tile;

                    if (map[r][c] == 1) {
                        g2.setColor(new Color(65, 75, 88));
                        g2.fillRoundRect(x, y, tile - 2, tile - 2, 10, 10);
                    } else {
                        g2.setColor(new Color(252, 252, 253));
                        g2.fillRoundRect(x, y, tile - 2, tile - 2, 10, 10);
                        g2.setColor(new Color(228, 233, 239));
                        g2.drawRoundRect(x, y, tile - 2, tile - 2, 10, 10);
                    }

                    if (map[r][c] == 2) {
                        g2.setColor(new Color(52, 152, 219));
                        g2.fillOval(x + 6, y + 6, tile - 14, tile - 14);
                    } else if (map[r][c] == 3) {
                        g2.setColor(new Color(231, 76, 60));
                        g2.fillOval(x + 6, y + 6, tile - 14, tile - 14);
                    } else if (map[r][c] == 4) {
                        g2.setColor(new Color(46, 204, 113));
                        g2.fillRoundRect(x + 7, y + 7, tile - 16, tile - 16, 8, 8);
                    }
                }
            }
        }

        private void drawFeatureBox(Graphics2D g2, int x, int y, String text, Color color) {
            g2.setColor(Color.WHITE);
            g2.fillRoundRect(x, y, 280, 40, 16, 16);

            g2.setColor(new Color(228, 233, 239));
            g2.drawRoundRect(x, y, 280, 40, 16, 16);

            g2.setColor(color);
            g2.fillOval(x + 14, y + 11, 18, 18);

            g2.setColor(new Color(60, 72, 88));
            g2.setFont(new Font("Segoe UI", Font.BOLD, 15));
            g2.drawString(text, x + 46, y + 25);
        }
    }
}