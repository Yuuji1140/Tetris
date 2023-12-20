package Tetrimino;

import java.awt.Color;

public class L1 extends Tetromino{
    public L1() {
        create(Color.orange);
    }

    public void setXY(int x, int y) {
        // Axis of rotation
        b[0].x = x;
        b[0].y = y;

        b[1].x = b[0].x;
        b[1].y = b[0].y - Block.SIZE;
        b[2].x = b[0].x;
        b[2].y = b[0].y + Block.SIZE;
        b[3].x = b[0].x + Block.SIZE;
        b[3].y = b[0].y + Block.SIZE;
    }

    @Override
    public void getD1() {
        temp[1].x = b[0].x;
        temp[1].y = b[0].y - Block.SIZE;
        temp[2].x = b[0].x;
        temp[2].y = b[0].y + Block.SIZE;
        temp[3].x = b[0].x + Block.SIZE;
        temp[3].y = b[0].y + Block.SIZE;

        updateXY(1);
    }

    @Override
    public void getD2() {
        
    }

    @Override
    public void getD3() {
        
    }

    @Override
    public void getD4() {
        
    }
}
