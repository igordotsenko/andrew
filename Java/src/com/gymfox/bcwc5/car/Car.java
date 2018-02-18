package com.gymfox.bcwc5.car;

import com.gymfox.bcwc5.base.point.Point;

public class Car implements Cloneable{
    private double fuelAmount;
    private double fuelCapacity;
    private double fuelConsumption;
    private Point location;
    private String model;

    public Car(double capacity, double consumption, Point location, String model) {
        this.fuelAmount = 0;
        this.fuelCapacity = capacity;
        this.fuelConsumption = consumption;
        this.location = location;
        this.model = model;
   }

    public void drive(Point destination) throws OutOfFuelException {
        double fuelNeed = location.distance(destination) * fuelConsumption;
        double newFuelAmount = fuelAmount - fuelNeed;

        if ( newFuelAmount < 0 ) {
            throw new OutOfFuelException();
        }

        fuelAmount = newFuelAmount;
        location = destination;
    }

    public void drive(double x, double y) throws OutOfFuelException {
        drive(new Point(x,y));
    }

    public void refill(double fuel) throws ToMuchFuelException{
        double newFuel = fuelAmount + fuel;

        if ( newFuel > fuelCapacity ) {
            throw new ToMuchFuelException();
        }
        fuelAmount = newFuel;
    }

    public String toString() {
        StringBuffer out = new StringBuffer();

        out.append("Car \"" + model + "\":\n")
                .append("-- Fuel Amount: " + fuelAmount)
                .append("\n-- Fuel Capacity: " + fuelCapacity)
                .append("\n-- Fuel Consumption: " + fuelConsumption)
                .append("\n-- Location: " + location);

        return out.toString();
    }

    public Car clone() throws CloneNotSupportedException {
        return (Car) super.clone();
    }

    public double getFuelAmount() {
        return fuelAmount;
    }

    public double getFuelCapacity() {
        return fuelCapacity;
    }

    public double getFuelConsumption() {
        return fuelConsumption;
    }

    public Point getLocation() {
        return location;
    }

    public String getModel() {
        return model;
    }
}
