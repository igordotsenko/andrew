package com.gymfox.snakegame;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class SnakeKeyListener extends KeyAdapter {
    private final Snake snake;

    public SnakeKeyListener(Snake snake) {
        this.snake = snake;
    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        super.keyPressed(keyEvent);

        if(keyEvent.getKeyCode() == KeyEvent.VK_LEFT && !snake.isRight()){
            snake.setLeft(true);
            snake.setUp(false);
            snake.setDown(false);
        }
        if(keyEvent.getKeyCode() == KeyEvent.VK_RIGHT && !snake.isLeft()){
            snake.setRight(true);
            snake.setUp(false);
            snake.setDown(false);
        }

        if(keyEvent.getKeyCode() == KeyEvent.VK_UP && !snake.isDown()){
            snake.setUp(true);
            snake.setRight(false);
            snake.setLeft(false);
        }
        if(keyEvent.getKeyCode() == KeyEvent.VK_DOWN && !snake.isUp()) {
            snake.setDown(true);
            snake.setRight(false);
            snake.setLeft(false);
        }
    }
}
