package Main;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.ArrayList;
import java.util.Random;

import Tetrimino.Block;
import Tetrimino.I;
import Tetrimino.L1;
import Tetrimino.L2;
import Tetrimino.Square;
import Tetrimino.T;
import Tetrimino.Tetromino;
import Tetrimino.Z1;
import Tetrimino.Z2;

public class Manager {

    // Main playground
    final int WIDTH = 360;
    final int HEIGHT = 600;
    public static int xLeft;
    public static int xRight;
    public static int yTop;
    public static int yBottom;

    // Tetromino
    Tetromino currTetromino;
    final int START_X;
    final int START_Y;

    Tetromino nextTetromino;
    final int NEXT_X;
    final int NEXT_Y;
    public static ArrayList<Block> inactive = new ArrayList<>();

    // Timers
    public static int fallInterval = 60;
    
    // Set gameplay frame
    public Manager() {
        xLeft = (GamePanel.WIDTH/2) - (WIDTH/2);
        xRight = xLeft + WIDTH;
        yTop = 50;
        yBottom = yTop + HEIGHT;

        START_X = xLeft + (WIDTH / 2) - Block.SIZE;
        START_Y = yTop + Block.SIZE;

        NEXT_X = xRight + 100;
        NEXT_Y = yTop + 480;

        // Set starting block
        currTetromino = pickTetromino();
        currTetromino.setXY(START_X, START_Y);

        nextTetromino = pickTetromino();
        nextTetromino.setXY(NEXT_X, NEXT_Y);
    }

    private Tetromino pickTetromino() {
        Tetromino mino = null;
        int i = new Random().nextInt(7);

        switch (i) {
            case 0:
                mino = new L1();
                break;
            case 1:
                mino = new L2();
                break;
            case 2:
                mino = new Square();
                break;
            case 3:
                mino = new I();
                break;
            case 4:
                mino = new T();
                break;
            case 5:
                mino = new Z1();
                break;
            case 6:
                mino = new Z2();
                break;
        }
        return mino;
    }

    public void update() {
        if (!currTetromino.active) {
            inactive.add(currTetromino.b[0]);
            inactive.add(currTetromino.b[1]);
            inactive.add(currTetromino.b[2]);
            inactive.add(currTetromino.b[3]);

            currTetromino.deactivating = false;

            currTetromino = nextTetromino;
            currTetromino.setXY(START_X, START_Y);

            nextTetromino = pickTetromino();
            nextTetromino.setXY(NEXT_X, NEXT_Y);
        } else
            currTetromino.update();
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
        g2.setFont(new Font("Arial", Font.BOLD, 25));
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2.drawString("NEXT", x + 40, y + 35);

        // Draw currTetromino
        if (currTetromino != null) {
            currTetromino.draw(g2);
        }

        // Draw nextTetromino
        nextTetromino.draw(g2);

        for (int i = 0; i < inactive.size(); i++) {
            inactive.get(i).draw(g2);
        }

        // Draw pause game
        g2.setColor(Color.white);
        g2.setFont(g2.getFont().deriveFont(50f));
        if (Controller.pause) {
            x = xLeft + 90;
            y = yTop + 320;
            g2.drawString("Paused", x, y);
        }
    }
}