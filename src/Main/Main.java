package Main;

import javax.swing.JFrame;

public class Main {
    public static void main(String[] args) {
        JFrame board = new JFrame("Tetris");
        board.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        GamePanel gp = new GamePanel();

        board.add(gp);
        board.pack();

        board.setLocationRelativeTo(null);
        board.setVisible(true);

        gp.launch();
    }
}
