package mazegame;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class IconFactory {

    public static Icon createMapIcon() {
        BufferedImage img = baseImage();
        Graphics2D g2 = prepare(img);

        g2.setStroke(new BasicStroke(2f));
        for (int r = 3; r <= 13; r += 5) {
            for (int c = 3; c <= 13; c += 5) {
                g2.drawRoundRect(c, r, 3, 3, 1, 1);
            }
        }

        g2.dispose();
        return new ImageIcon(img);
    }

    public static Icon createRandomIcon() {
        BufferedImage img = baseImage();
        Graphics2D g2 = prepare(img);

        g2.setStroke(new BasicStroke(2.2f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        g2.drawArc(2, 4, 8, 8, 200, 220);
        g2.drawLine(6, 2, 9, 2);
        g2.drawLine(9, 2, 8, 5);

        g2.drawArc(8, 6, 8, 8, 20, 220);
        g2.drawLine(10, 16, 7, 16);
        g2.drawLine(7, 16, 8, 13);

        g2.dispose();
        return new ImageIcon(img);
    }

    public static Icon createRestartIcon() {
        BufferedImage img = baseImage();
        Graphics2D g2 = prepare(img);

        g2.setStroke(new BasicStroke(2.2f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        g2.drawArc(3, 3, 12, 12, 35, 280);
        g2.drawLine(12, 2, 15, 2);
        g2.drawLine(15, 2, 14, 5);

        g2.dispose();
        return new ImageIcon(img);
    }

    public static Icon createExitIcon() {
        BufferedImage img = baseImage();
        Graphics2D g2 = prepare(img);

        g2.setStroke(new BasicStroke(2.4f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        g2.drawLine(4, 4, 14, 14);
        g2.drawLine(14, 4, 4, 14);

        g2.dispose();
        return new ImageIcon(img);
    }

    public static Icon createPlayIcon() {
        BufferedImage img = baseImage();
        Graphics2D g2 = prepare(img);

        Polygon p = new Polygon();
        p.addPoint(5, 3);
        p.addPoint(14, 9);
        p.addPoint(5, 15);
        g2.fillPolygon(p);

        g2.dispose();
        return new ImageIcon(img);
    }

    public static Icon createBackIcon() {
        BufferedImage img = baseImage();
        Graphics2D g2 = prepare(img);

        g2.setStroke(new BasicStroke(2.4f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        g2.drawLine(14, 9, 4, 9);
        g2.drawLine(4, 9, 8, 5);
        g2.drawLine(4, 9, 8, 13);

        g2.dispose();
        return new ImageIcon(img);
    }

    private static BufferedImage baseImage() {
        return new BufferedImage(18, 18, BufferedImage.TYPE_INT_ARGB);
    }

    private static Graphics2D prepare(BufferedImage img) {
        Graphics2D g2 = img.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(Color.WHITE);
        return g2;
    }
}