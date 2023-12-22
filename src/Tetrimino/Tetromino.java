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

    boolean leftC, rightC, bottomC;
    public boolean active = true;

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
        rotationCollision();
        
        if (!leftC && !rightC && !bottomC) {
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
    }
    
    public void getD1() {}
    public void getD2() {}
    public void getD3() {}
    public void getD4() {}

    public void movementCollison() {
        leftC = false;
        rightC = false;
        bottomC = false;

        inactiveCollision();

        // Left Wall
        for (int i = 0; i < b.length; i++) {
            if (b[i].x == Manager.xLeft)
                leftC = true;
        }

        // Right
        for (int i = 0; i < b.length; i++) {
            if (b[i].x + Block.SIZE == Manager.xRight)
                rightC = true;
        }

        // Bottom
        for (int i = 0; i < b.length; i++) {
            if (b[i].y + Block.SIZE == Manager.yBottom)
                bottomC = true;
        }
    }

    public void rotationCollision() {
        leftC = false;
        rightC = false;
        bottomC = false;

        inactiveCollision();

        // Left Wall
        for (int i = 0; i < b.length; i++) {
            if (temp[i].x < Manager.xLeft)
                leftC = true;
        }

        // Right
        for (int i = 0; i < b.length; i++) {
            if (temp[i].x + Block.SIZE > Manager.xRight)
                rightC = true;
        }

        // Bottom
        for (int i = 0; i < b.length; i++) {
            if (temp[i].y + Block.SIZE > Manager.yBottom)
                bottomC = true;
        }
    }

    public void inactiveCollision() {
        for (int i = 0; i < Manager.inactive.size(); i++) {
            int targetX = Manager.inactive.get(i).x;
            int targetY = Manager.inactive.get(i).y;

            // Check bottom
            for (int j = 0; j < b.length; j++) {
                if(b[j].x == targetX && b[j].y + Block.SIZE == targetY)
                    bottomC = true;
            }

            // Check left
            for (int j = 0; j < b.length; j++) {
                if(b[j].x == targetX && b[j].y + Block.SIZE == targetY)
                    leftC = true;
            }

            // Check right
            for (int j = 0; j < b.length; j++) {
                if(b[j].x == targetX && b[j].y + Block.SIZE == targetY)
                    rightC = true;
            }
        }
    }

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

        movementCollison();

        if (Controller.downPressed) {
            if(!bottomC) {
                b[0].y += Block.SIZE;
                b[1].y += Block.SIZE;
                b[2].y += Block.SIZE;
                b[3].y += Block.SIZE;

                // Reset counter when moved downward
                dropCounter = 0;
            }
            Controller.downPressed = false;
        }

        if (Controller.leftPressed) {
            if(!leftC) {
                b[0].x -= Block.SIZE;
                b[1].x -= Block.SIZE;
                b[2].x -= Block.SIZE;
                b[3].x -= Block.SIZE;
            }
            Controller.leftPressed = false;
        }

        if (Controller.rightPressed) {
            if(!rightC) {
                b[0].x += Block.SIZE;
                b[1].x += Block.SIZE;
                b[2].x += Block.SIZE;
                b[3].x += Block.SIZE;
            }
            Controller.rightPressed = false;
        }

        if (bottomC)
            active = false;
        else {
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
