package com.gymfox.bcwc5.gun;

public class Gun {
    private int amount;
    private int capacity;
    private boolean isReady;
    private String model;
    private int totalShots;

    public Gun(String model, int capacity) {
        this.amount = 0;
        this.capacity = capacity;
        this.isReady = false;
        this.model = model;
        this.totalShots = 0;
    }

    public void shoot() throws OutOfRoundsException, GunIsNotReadyException {
        if ( amount == 0 ) {
            throw new OutOfRoundsException();
        }
        if ( !ready() ) {
            throw new GunIsNotReadyException();
        }

        amount -= 1;
        totalShots += 1;

        System.out.println("Bang!");
        String.format("Total shots: ( %d / %d )", getTotalShots(), getCapacity());
    }

    public String toString() {
        StringBuffer out = new StringBuffer();

        out.append("Gun - " + getModel() +"\n")
                .append("Capacity / amount: ( " + getCapacity() + " / " + getAmount() + " )\n")
                .append(!ready() ? ("not ready\n") : ("ready\n"));

        return out.toString();
    }

    public Gun clone() throws CloneNotSupportedException {
        return (Gun) super.clone();
    }

    public void reload() {
        amount = capacity;
    }

    public void prepare() {
        isReady = !isReady;
    }

    public boolean ready() {
        return isReady;
    }

    public int getAmount() {
        return amount;
    }

    public int getCapacity() {
        return capacity;
    }

    public String getModel() {
        return model;
    }

    public int getTotalShots() {
        return totalShots;
    }
}