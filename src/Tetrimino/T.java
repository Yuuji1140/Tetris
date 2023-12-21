package Tetrimino;

import java.awt.Color;

public class T extends Tetromino {
    public T() {
        create(Color.magenta);
    }

    public void setXY(int x, int y) {
        // Axis of rotation
        b[0].x = x;
        b[0].y = y;

        b[1].x = b[0].x;
        b[1].y = b[0].y - Block.SIZE;
        b[2].x = b[0].x - Block.SIZE;
        b[2].y = b[0].y;
        b[3].x = b[0].x + Block.SIZE;
        b[3].y = b[0].y;
    }

    //    o
    //  o o o
    @Override
    public void getD1() {
        temp[0].x = b[0].x;
        temp[0].y = b[0].y;
        temp[1].x = b[0].x;
        temp[1].y = b[0].y - Block.SIZE;
        temp[2].x = b[0].x - Block.SIZE;
        temp[2].y = b[0].y;
        temp[3].x = b[0].x + Block.SIZE;
        temp[3].y = b[0].y;

        updateXY(1);
    }

    //  o
    //  o o 
    //  o
    @Override
    public void getD2() {        
        temp[0].x = b[0].x;
        temp[0].y = b[0].y;
        temp[1].x = b[0].x + Block.SIZE;
        temp[1].y = b[0].y;
        temp[2].x = b[0].x;
        temp[2].y = b[0].y - Block.SIZE;
        temp[3].x = b[0].x;
        temp[3].y = b[0].y + Block.SIZE;

        updateXY(2);
    }

    //  o o o
    //    o
    @Override
    public void getD3() {
        temp[0].x = b[0].x;
        temp[0].y = b[0].y;
        temp[1].x = b[0].x;
        temp[1].y = b[0].y + Block.SIZE;
        temp[2].x = b[0].x + Block.SIZE;
        temp[2].y = b[0].y;
        temp[3].x = b[0].x - Block.SIZE;
        temp[3].y = b[0].y;

        updateXY(3);
    }

    //    o
    //  o o 
    //    o
    @Override
    public void getD4() {
        temp[0].x = b[0].x;
        temp[0].y = b[0].y;
        temp[1].x = b[0].x - Block.SIZE;
        temp[1].y = b[0].y;
        temp[2].x = b[0].x;
        temp[2].y = b[0].y + Block.SIZE;
        temp[3].x = b[0].x;
        temp[3].y = b[0].y - Block.SIZE;

        updateXY(4);
    }
}
