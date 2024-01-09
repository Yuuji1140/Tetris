package Tetrimino;

import java.awt.Color;
import java.awt.Graphics2D;

import Main.Controller;
import Main.Manager;

public class Tetromino {
    public Block[] b = new Block[4];
    public Block[] temp = new Block[4];
    int x, y;
    
    int dropCounter = 0;
    public int direction = 1;

    boolean leftC, rightC, bottomC;
    public boolean active = true;

    public boolean deactivating;
    int dc = 0;

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

    public void setXY(int x, int y) {
        this.x = x;
        this.y = y;
    }

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
        for (int i = 0; i < b.length; i++) 
            if (b[i].x - 4 == Manager.xLeft)
                leftC = true;

        // Right Wall
        for (int i = 0; i < b.length; i++) 
            if (b[i].x + Block.SIZE - 4 == Manager.xRight)
                rightC = true;

        // Bottom Wall
        for (int i = 0; i < b.length; i++) 
            if (b[i].y + Block.SIZE - 4 == Manager.yBottom)
                bottomC = true;

        for (int i = 0; i < Manager.inactive.size(); i++)
            for (int j = 0; j < b.length; j++) {
                int targetX = Manager.inactive.get(i).x + 4;
                int targetY = Manager.inactive.get(i).y + 4;
            
                // Check left
                if (b[j].x - Block.SIZE == targetX && b[j].y == targetY)
                    leftC = true;
            
                // Check right
                if (b[j].x + Block.SIZE == targetX && b[j].y == targetY)
                    rightC = true;
        }
    }

    public void rotationCollision() {
        leftC = false;
        rightC = false;
        bottomC = false;

        inactiveCollision();

        // Left Wall
        for (int i = 0; i < b.length; i++) 
            if (temp[i].x - 4 < Manager.xLeft)
                leftC = true;

        // Right
        for (int i = 0; i < b.length; i++) 
            if (temp[i].x + Block.SIZE - 4 > Manager.xRight)
                rightC = true;

        // Bottom
        for (int i = 0; i < b.length; i++)
            if (temp[i].y + Block.SIZE - 4 > Manager.yBottom)
                bottomC = true;
    }

    public void inactiveCollision() {
        for (int i = 0; i < Manager.inactive.size(); i++) {
            int targetX = Manager.inactive.get(i).x + 4;
            int targetY = Manager.inactive.get(i).y + 4;

            // Check bottom
            for (int j = 0; j < b.length; j++)
                if(b[j].x == targetX && b[j].y + Block.SIZE == targetY)
                    bottomC = true;

            // Check left
            for (int j = 0; j < b.length; j++)
                if(b[j].x == targetX && b[j].y == targetY)
                    leftC = true;

            // Check right
            for (int j = 0; j < b.length; j++)
                if(b[j].x == targetX + Block.SIZE && b[j].y == targetY)
                    rightC = true;
        }
    }

    public void update() {
        movementCollison();

        if (deactivating)
            deactivating();

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
            if(!bottomC) {
                b[0].y += Block.SIZE;
                b[1].y += Block.SIZE;
                b[2].y += Block.SIZE;
                b[3].y += Block.SIZE;

                // Reset counter when moved downward
                dropCounter = 0;

                Manager.score += 1;
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
            deactivating = true;
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

        if (Controller.hardDropPressed) {
            hardDrop();
            Controller.hardDropPressed = false;
        }
    }

    public void hardDrop() {
        int linesDropped = 0;
        while (!bottomC) {
            b[0].y += Block.SIZE;
            b[1].y += Block.SIZE;
            b[2].y += Block.SIZE;
            b[3].y += Block.SIZE;

            movementCollison();
            linesDropped++;
        }

        Manager.score += 2 * linesDropped;
        active = false;
    }

    private void deactivating() {
        dc++;

        // 45 frames wait time
        if (dc == 45) {
            dc = 0;
            movementCollison();
            
            if (bottomC)
                active = false;
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
