package com.gymfox.bcwc5.car;

import com.gymfox.bcwc5.base.point.Point;

public class CarMain {
    public static void main(String[] args) throws OutOfFuelException,
            ToMuchFuelException {
        Car car = new Car(100, 0.15, new Point(1.0, 1.0), "Mercedes");

        System.out.println(car);

        car.refill(100);
        System.out.println("Refilling...");
        System.out.println("Current fuel amount: " + car.getFuelAmount());

        car.drive(new Point(12, 2));
        System.out.println(car);

    }
}
