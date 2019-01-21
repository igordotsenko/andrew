package com.gymfox.snakegame;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class SnakeFood  {
    private Image food;
    private int beerX;
    private int beerY;

    public SnakeFood() {
        this.food = new ImageIcon("icons/beer.png").getImage();
    }

    public void createBeer() {
        beerX = new Random().nextInt(10) * GameFrame.ICON_SIZE;
        beerY = new Random().nextInt(10) * GameFrame.ICON_SIZE;
    }

    public Image getFoodImage() {
        return food;
    }

    public int getBeerX() {
        return beerX;
    }

    public int getBeerY() {
        return beerY;
    }
}
