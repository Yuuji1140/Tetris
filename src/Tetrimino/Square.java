package Tetrimino;

import java.awt.Color;

public class Square extends Tetromino {
    public Square() {
        create(Color.yellow);
    }

    public void setXY(int x, int y) {
        // Axis of rotation
        b[0].x = x;
        b[0].y = y;

        b[1].x = b[0].x;
        b[1].y = b[0].y + Block.SIZE;
        b[2].x = b[0].x + Block.SIZE;
        b[2].y = b[0].y;
        b[3].x = b[0].x + Block.SIZE;
        b[3].y = b[0].y + Block.SIZE;
    }

    @Override
    public void getD1() {}

    @Override
    public void getD2() {}

    @Override
    public void getD3() {}

    @Override
    public void getD4() {}
}
