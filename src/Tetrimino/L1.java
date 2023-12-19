package Tetrimino;

import java.awt.Color;

public class L1 extends Tetromino{
    public L1() {
        create(Color.orange);
    }

    public void setXY(int x, int y) {
        b[0].x = x;
        b[0].y = y;
        
    }
}
