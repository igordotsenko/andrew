package com.gymfox.snakegame;

import javax.swing.*;
import java.awt.*;

public class Snake {
    private final Image snakeHead;
    private final Image snakeBody;
    private int[] snakeBodyX = new int[GameFrame.BEER_COUNT];
    private int[] snakeBodyY = new int[GameFrame.BEER_COUNT];
    private int snakeSize = 3;
    static final int SNAKE_HEAD_COORDINATE = 0;
    private boolean left = false;
    private boolean right = true;
    private boolean up = false;
    private boolean down = false;

    public Snake() {
        this.snakeHead = new ImageIcon("icons/snakeHead.png").getImage();
        this.snakeBody = new ImageIcon("icons/snakeBody.png").getImage();
    }

    public void createSnake() {
        for (int i = 0; i < snakeSize; i++) {
            snakeBodyX[i] = 64 - i * GameFrame.ICON_SIZE;
            snakeBodyY[i] = 64;
        }
    }

    public void move() {
        for ( int i = snakeSize; i > 0; i-- ) {
            snakeBodyX[i] = getSnakeCoordinateX(i - 1);
            snakeBodyY[i] = getSnakeCoordinateY(i - 1);
        }
        if ( left ) {
            snakeBodyX[SNAKE_HEAD_COORDINATE] -= GameFrame.ICON_SIZE;
        }
        if ( right ) {
            snakeBodyX[SNAKE_HEAD_COORDINATE] += GameFrame.ICON_SIZE;
        }
        if ( up ) {
            snakeBodyY[SNAKE_HEAD_COORDINATE] -= GameFrame.ICON_SIZE;
        }
        if ( down ) {
            snakeBodyY[SNAKE_HEAD_COORDINATE] += GameFrame.ICON_SIZE;
        }
    }

    public int getSnakeSize() {
        return snakeSize;
    }

    public void incrementSnakeSize() {
        snakeSize += 1;
    }

    public int getSnakeCoordinateX(int i) {
        return snakeBodyX[i];
    }

    public int getSnakeCoordinateY(int i) {
        return snakeBodyY[i];
    }

    public Image getImageSnakeHead() {
        return snakeHead;
    }

    public Image getImageSnakeBody() {
        return snakeBody;
    }

    public boolean isLeft() {
        return left;
    }

    public boolean isRight() {
        return right;
    }

    public boolean isUp() {
        return up;
    }

    public boolean isDown() {
        return down;
    }

    public void setLeft(boolean value) {
        left = value;
    }

    public void setRight(boolean value) {
        right = value;
    }

    public void setUp(boolean value) {
        up = value;
    }

    public void setDown(boolean value) {
        down = value;
    }
}
