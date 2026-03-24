package mazegame;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class ShadowPanel extends JPanel {

    public ShadowPanel() {
        setOpaque(false);
        setBorder(new EmptyBorder(16, 16, 16, 16));
        setLayout(new BorderLayout());
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int x = 8;
        int y = 8;
        int w = getWidth() - 16;
        int h = getHeight() - 16;
        int arc = 28;

        for (int i = 0; i < 8; i++) {
            g2.setColor(new Color(0, 0, 0, 18 - i * 2));
            g2.fillRoundRect(x + i, y + i, w - i * 2, h - i * 2, arc, arc);
        }

        g2.setColor(Color.WHITE);
        g2.fillRoundRect(x, y, w, h, arc, arc);

        g2.dispose();
        super.paintComponent(g);
    }
}