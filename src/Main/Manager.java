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

    int num;
    public static ArrayList<Tetromino> nextBlocks = new ArrayList<>();
    public static ArrayList<Block> inactive = new ArrayList<>();

    // Timers
    public static int fallInterval = 60;

    static boolean endgame;

    // Holding
    private boolean used;
    private Tetromino hold;
    private boolean swap = false;
    final int HOLD_X;
    final int HOLD_Y;

    // Score and level
    int level = 1;
    int lines;
    public static int score;
    
    // Set gameplay frame
    public Manager() {
        xLeft = (GamePanel.WIDTH/2) - (WIDTH/2);
        xRight = xLeft + WIDTH;
        yTop = 50;
        yBottom = yTop + HEIGHT;

        START_X = xLeft + 4 + (WIDTH / 2) - Block.SIZE;
        START_Y = yTop + 4 + Block.SIZE;

        HOLD_X = xLeft - 125;
        HOLD_Y = yTop + 50;

        // Set starting block
        currTetromino = pickTetromino();
        currTetromino.setXY(START_X, START_Y);

        num = 50;
        for (int i = 0; i < 5; i++) {
            nextBlocks.add(pickTetromino());
            nextBlocks.get(i).setXY(xRight + 100, yTop + num);
            num += 120;
        }
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
            for (int i = 0; i < currTetromino.b.length; i++) {
                currTetromino.b[i].x -= 4;
                currTetromino.b[i].y -= 4;
                inactive.add(currTetromino.b[i]);
            }

            if (currTetromino.b[0].x + 4 == START_X && currTetromino.b[0].y + 4 == START_Y) {
                endgame = true;
            }

            currTetromino.deactivating = false;
            
            currTetromino = nextBlocks.get(0);
            currTetromino.setXY(START_X, START_Y);

            nextBlocks.remove(0);
            num = 50;
            for (int i = 0; i < nextBlocks.size(); i++) {
                nextBlocks.get(i).setXY(xRight + 100, yTop + num);
                num += 120;
            }

            
            nextBlocks.add(pickTetromino());
            nextBlocks.get(4).setXY(xRight + 100, yTop + num);

            swap = false;

            deleteLine();
        } else
            currTetromino.update();

            // Handle hard drop
            if (Controller.hardDropPressed) {
                currTetromino.hardDrop();
                Controller.hardDropPressed = false;
            }

            if (Controller.shift && !used) {
                holdMino();
                used = true;
            }

            if (!Controller.shift)
                used = false;
    }

    private void holdMino() {
        if (hold == null) {
            hold = currTetromino;
            hold.setXY(HOLD_X, HOLD_Y);
            currTetromino = nextBlocks.get(0);
            currTetromino.setXY(START_X, START_Y);

            nextBlocks.remove(0);
            num = 50;
            for (int i = 0; i < nextBlocks.size(); i++) {
                nextBlocks.get(i).setXY(xRight + 100, yTop + num);
                num += 120;
            }
            
            nextBlocks.add(pickTetromino());
            nextBlocks.get(4).setXY(xRight + 100, yTop + num);
        } else if (!swap) {
            Tetromino temp = hold;
            hold = currTetromino;
            hold.setXY(HOLD_X, HOLD_Y);
            currTetromino = temp;
            currTetromino.setXY(START_X, START_Y);
            swap = true;
        }
    }

    private void deleteLine() {
        int x = xLeft;
        int y = yTop;
        int bc = 0;
        int lc = 0;
        int increment = 10;

        while (x < xRight && y < yBottom) {
            for (int i = 0; i < inactive.size(); i++) {
                if (inactive.get(i).x == x && inactive.get(i).y == y) {
                    bc++;
                }
            }

            x += Block.SIZE;

            if (x == xRight) {
                if (bc == 12) {
                    for (int i = inactive.size() - 1; i > -1; i--) {
                        if (inactive.get(i).y == y) {
                            inactive.remove(i);
                        }
                    }

                    lc++;
                    lines++;

                    if (lines % increment == 0 && fallInterval > 1) {
                        level++;
                        increment += 10;

                        if (fallInterval > 10)
                            fallInterval -= 5;
                        else
                            fallInterval -= 1;
                    }

                    for (int i = 0; i < inactive.size(); i++) {
                        if (inactive.get(i).y < y) {
                            inactive.get(i).y += Block.SIZE;
                        }
                    }
                }

                bc = 0;
                x = xLeft;
                y += Block.SIZE;
            }
        }

        // Score calculations
        if (lc == 1) {
            score += 40 * level;
        } else if (lc == 2) {
            score += 100 * level;
        } else if (lc == 3) {
            score += 300 * level;
        } else if (lc == 4) {
            score += 1200 * level;
        }
    }

    public void draw(Graphics2D g2) {
        int x, y;

        // Draw playground
        g2.setColor(Color.WHITE);
        g2.setStroke(new BasicStroke(4f));
        g2.drawRect(xLeft - 4, yTop - 4, WIDTH + 8, HEIGHT + 8);

        // Set Font and Rendering
        g2.setFont(new Font("Arial", Font.BOLD, 25));
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        // Draw scoring frame
        y = yBottom - 220;
        g2.drawRect(xLeft - 300, y, 250, 220);
        g2.drawString("Level: " + level, xLeft - 260, y + 60);
        g2.drawString("Lines: " + lines, xLeft - 260, y + 120);
        g2.drawString("Score: " + String.format("%06d", score), xLeft - 260, y + 180);

        // Draw currTetromino
        if (currTetromino != null) {
            currTetromino.draw(g2);
        }

        // Draw next blocks
        for (int i = 0; i < nextBlocks.size(); i++) {
            nextBlocks.get(i).draw(g2);
        }

        // Draw placed blocks
        for (int i = 0; i < inactive.size(); i++) {
            inactive.get(i).draw(g2);
        }

        // Draw held block
        if (hold != null) {
            hold.draw(g2);
        }

        // Notify player of pause or game over
        g2.setFont(g2.getFont().deriveFont(50f));
        if (endgame) {
            // Draw game over
            g2.setColor(Color.yellow);
            x = xLeft + 45;
            y = yTop + 320;
            g2.drawString("Game Over", x, y);
        } else if (Controller.pause) {
            // Draw pause game
            g2.setColor(Color.white);
            x = xLeft + 90;
            y = yTop + 320;
            g2.drawString("Paused", x, y);
        }
    }
}