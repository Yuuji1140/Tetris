package Tetrimino;

import java.awt.Color;

public class Z2 extends Tetromino {
    public Z2() {
        create(Color.green);
    }

    public void setXY(int x, int y) {
        // Axis of rotation
        b[0].x = x;
        b[0].y = y;

        b[1].x = b[0].x;
        b[1].y = b[0].y - Block.SIZE;
        b[2].x = b[0].x + Block.SIZE;
        b[2].y = b[0].y;
        b[3].x = b[0].x + Block.SIZE;
        b[3].y = b[0].y + Block.SIZE;
    }

    //  o
    //  o o
    //    o
    @Override
    public void getD1() {
        temp[0].x = b[0].x;
        temp[0].y = b[0].y;
        temp[1].x = b[0].x;
        temp[1].y = b[0].y - Block.SIZE;
        temp[2].x = b[0].x + Block.SIZE;
        temp[2].y = b[0].y;
        temp[3].x = b[0].x + Block.SIZE;
        temp[3].y = b[0].y + Block.SIZE;

        updateXY(1);
    }

    //    o o
    //  o o
    @Override
    public void getD2() {        
        temp[0].x = b[0].x;
        temp[0].y = b[0].y;
        temp[1].x = b[0].x - Block.SIZE;
        temp[1].y = b[0].y;
        temp[2].x = b[0].x;
        temp[2].y = b[0].y - Block.SIZE;
        temp[3].x = b[0].x + Block.SIZE;
        temp[3].y = b[0].y - Block.SIZE;

        updateXY(2);
    }

    @Override
    public void getD3() {
        getD1();
    }

    @Override
    public void getD4() {
        getD2();
    }
}
