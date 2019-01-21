package com.gymfox.snakegame;

import javax.swing.*;

public class GameFrame extends JFrame {
    static final int ICON_SIZE = 32;
    static final int SIZE_WIDTH = 320;
    static final int SIZE_HEIGHT = 345;
    static final int BEER_COUNT = 100;

    public GameFrame() {
        setTitle("com.gymfox.snakegame.Snake");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(SIZE_WIDTH, SIZE_HEIGHT);
        setLocationRelativeTo(null);
        add(new GameField());
        setVisible(true);
    }
}
