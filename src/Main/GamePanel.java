package Main;

import javax.swing.JPanel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class GamePanel extends JPanel implements Runnable{
    public static final int WIDTH = 1280;
    public static final int HEIGHT = 720;
    final int FPS = 60;
    Thread gThread;
    Manager m;
    
    public GamePanel() {

        // Settings
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        this.setBackground(Color.black);
        this.setLayout(null);

        m = new Manager();
    }

    public void launch() {
        gThread = new Thread(this);
        gThread.start();
    }

    @Override
    public void run() {
        // Game loop
        double interval = 1000000000/FPS;
        double d = 0;
        long finalTime = System.nanoTime();
        long currTime;

        while(gThread != null) {
            currTime = System.nanoTime();

            d += (currTime - finalTime) / interval;
            finalTime = currTime;

            if (d >= 1) {
                update();
                repaint();
                d--;
            }
        }
    }

    private void update() {
        m.update();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;
        m.draw(g2);
    }
}