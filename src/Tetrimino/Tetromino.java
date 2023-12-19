package Tetrimino;

import java.awt.Color;
import java.awt.Graphics2D;

public class Tetromino {
    public Block[] b = new Block[4];
    public Block[] temp = new Block[4];

    public void create(Color c) {
        b[0] = new Block(c);
        b[1] = new Block(c);
        b[2] = new Block(c);
        b[3] = new Block(c);

        temp[0] = new Block(c);
        temp[1] = new Block(c);
        temp[2] = new Block(c);
        temp[3] = new Block(c);
    }

    public void setXY(int x, int y) {}
    public void updateXY(int x, int y) {}
    public void update() {}

    public void draw(Graphics2D g2) {
        
    }
}
