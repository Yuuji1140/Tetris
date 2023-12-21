package Tetrimino;

import java.awt.Color;
import java.awt.Graphics2D;

import Main.Controller;
import Main.Manager;

public class Tetromino {
    public Block[] b = new Block[4];
    public Block[] temp = new Block[4];
    int dropCounter = 0;
    public int direction = 1;

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

    public void updateXY(int direction) {
        this.direction = direction;
        b[0].x = temp[0].x;
        b[0].y = temp[0].y;
        b[1].x = temp[1].x;
        b[1].y = temp[1].y;
        b[2].x = temp[2].x;
        b[2].y = temp[2].y;
        b[3].x = temp[3].x;
        b[3].y = temp[3].y;
    }
    
    public void getD1() {}
    public void getD2() {}
    public void getD3() {}
    public void getD4() {}

    public void update() {
        // Move Tetromino
        if (Controller.upPressed) {
            switch (direction) {
                case 1:
                    getD2();
                    break;
                case 2:
                    getD3();
                    break;
                case 3:
                    getD4();
                    break;
                case 4:
                    getD1();
                    break;
            }
            Controller.upPressed = false;
        }        
        if (Controller.downPressed) {
            b[0].y += Block.SIZE;
            b[1].y += Block.SIZE;
            b[2].y += Block.SIZE;
            b[3].y += Block.SIZE;

            // Reset counter when moved downward
            dropCounter = 0;

            Controller.downPressed = false;
        }
        if (Controller.leftPressed) {
            b[0].x -= Block.SIZE;
            b[1].x -= Block.SIZE;
            b[2].x -= Block.SIZE;
            b[3].x -= Block.SIZE;

            Controller.leftPressed = false;
        }
        if (Controller.rightPressed) {
            b[0].x += Block.SIZE;
            b[1].x += Block.SIZE;
            b[2].x += Block.SIZE;
            b[3].x += Block.SIZE;

            Controller.rightPressed = false;
        }

        // Increases every frame
        dropCounter++;

        if (dropCounter == Manager.fallInterval) {
            // Reached bottom of play area
            b[0].y += Block. SIZE;
            b[1].y += Block.SIZE;
            b[2].y += Block.SIZE;
            b[3].y += Block.SIZE;
            dropCounter = 0;
        }
    }

    public void draw(Graphics2D g2) {
        int margin = 2;
        g2.setColor(b[0].c);
        g2.fillRect(b[0].x - margin, b[0].y - margin, Block.SIZE - (margin * 2), Block.SIZE - (margin * 2));
        g2.fillRect(b[1].x - margin, b[1].y - margin, Block.SIZE - (margin * 2), Block.SIZE - (margin * 2));
        g2.fillRect(b[2].x - margin, b[2].y - margin, Block.SIZE - (margin * 2), Block.SIZE - (margin * 2));
        g2.fillRect(b[3].x - margin, b[3].y - margin, Block.SIZE - (margin * 2), Block.SIZE - (margin * 2));
    }
}
