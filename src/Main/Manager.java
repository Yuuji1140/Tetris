package Main;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

public class Manager {
    final int WIDTH = 360;
    final int HEIGHT = 600;
    public static int xLeft;
    public static int xRight;
    public static int yTop;
    public static int yBottom;
    
    // Set gameplay frame
    public Manager() {
        xLeft = (GamePanel.WIDTH/2) - (WIDTH/2);
        xRight = xLeft + WIDTH;
        yTop = 50;
        yBottom = yTop + HEIGHT;
    }

    public void update() {

    }

    public void draw(Graphics2D g2) {
        // Draw playground
        g2.setColor(Color.WHITE);
        g2.setStroke(new BasicStroke(4f));
        g2.drawRect(xLeft - 4, yTop - 4, WIDTH + 8, HEIGHT + 8);

        // Draw Next Block Frame
        int x = xRight + 50;
        int y = yBottom - 200;
        g2.drawRect(x, y, 150, 150);
        g2.setFont(new Font("Arial", Font.BOLD, 30));
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2.drawString("NEXT", x + 35, y + 35);
    }
}