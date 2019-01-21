package com.gymfox.snakegame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameField extends JPanel implements ActionListener {
    private SnakeFood food;
    private Snake snake;
    private boolean inGame = true;
    private final Timer speed = new Timer( 125, this);

    public GameField() {
        this.food = new SnakeFood();
        this.snake = new Snake();
        initGame();
        addKeyListener(new SnakeKeyListener(snake));
        setFocusable(true);
    }

    private void initGame() {
        snake.createSnake();
        food.createBeer();
        speed.start();
    }

    public void checkBeer() {
        if ( snake.getSnakeCoordinateX(Snake.SNAKE_HEAD_COORDINATE) == food.getBeerX() &&
                snake.getSnakeCoordinateY(Snake.SNAKE_HEAD_COORDINATE) == food.getBeerY() ) {
            snake.incrementSnakeSize();
            food.createBeer();
        }
    }

    public void checkCollision() {
        for (int i = snake.getSnakeSize(); i > 0 ; i--) {
            if( i > 4 && snake.getSnakeCoordinateX(Snake.SNAKE_HEAD_COORDINATE) == snake.getSnakeCoordinateX(i) && snake.getSnakeCoordinateY(Snake.SNAKE_HEAD_COORDINATE) == snake.getSnakeCoordinateY(i)) {
                inGame = false;
            }
        }

        if ( snake.getSnakeCoordinateX(Snake.SNAKE_HEAD_COORDINATE) > GameFrame.SIZE_WIDTH || snake.getSnakeCoordinateY(Snake.SNAKE_HEAD_COORDINATE) > GameFrame.SIZE_WIDTH ) {
            inGame = false;
        }
        if ( snake.getSnakeCoordinateY(Snake.SNAKE_HEAD_COORDINATE) < 0 || snake.getSnakeCoordinateX(Snake.SNAKE_HEAD_COORDINATE) < 0 ) {
            inGame = false;
        }
    }

    @Override
    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        if ( inGame() ) {
            graphics.drawImage(food.getFoodImage(), food.getBeerX(), food.getBeerY(), this);
            for (int i = 1; i < snake.getSnakeSize(); i++) {
                graphics.drawImage(snake.getImageSnakeHead(), snake.getSnakeCoordinateX(Snake.SNAKE_HEAD_COORDINATE),
                        snake.getSnakeCoordinateY(Snake.SNAKE_HEAD_COORDINATE), this);
                graphics.drawImage(snake.getImageSnakeBody(), snake.getSnakeCoordinateX(i),
                        snake.getSnakeCoordinateY(i), this);
            }
        } else {
            graphics.setColor(Color.black);
            graphics.drawString("Game Over", 125, GameFrame.SIZE_WIDTH/2);
        }
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if (inGame()) {
            checkBeer();
            checkCollision();
            snake.move();

        }
        repaint();
    }

    public boolean inGame() {
        return inGame;
    }
}
