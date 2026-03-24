package mazegame;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ThemeButton extends JButton {
    private final Color baseColor;
    private final Color hoverColor;
    private final Color pressColor;

    private boolean hovered = false;

    public ThemeButton(String text, Icon icon, Color baseColor) {
        super(text, icon);
        this.baseColor = baseColor;
        this.hoverColor = brighten(baseColor, 18);
        this.pressColor = darken(baseColor, 20);

        setFont(new Font("Segoe UI", Font.BOLD, 13));
        setForeground(Color.WHITE);
        setFocusPainted(false);
        setBorderPainted(false);
        setContentAreaFilled(false);
        setOpaque(false);
        setCursor(new Cursor(Cursor.HAND_CURSOR));
        setBorder(new EmptyBorder(10, 18, 10, 18));
        setIconTextGap(8);
        setHorizontalTextPosition(SwingConstants.RIGHT);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                hovered = true;
                repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                hovered = false;
                repaint();
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int w = getWidth();
        int h = getHeight();
        int arc = 18;

        Color fill = getModel().isPressed() ? pressColor : (hovered ? hoverColor : baseColor);

        if (hovered && !getModel().isPressed()) {
            g2.setColor(new Color(0, 0, 0, 25));
            g2.fillRoundRect(2, 4, w - 4, h - 2, arc, arc);
        }

        g2.setColor(fill);
        g2.fillRoundRect(0, 0, w, h - 2, arc, arc);

        g2.dispose();
        super.paintComponent(g);
    }

    private Color brighten(Color c, int amount) {
        return new Color(
                Math.min(255, c.getRed() + amount),
                Math.min(255, c.getGreen() + amount),
                Math.min(255, c.getBlue() + amount)
        );
    }

    private Color darken(Color c, int amount) {
        return new Color(
                Math.max(0, c.getRed() - amount),
                Math.max(0, c.getGreen() - amount),
                Math.max(0, c.getBlue() - amount)
        );
    }
}