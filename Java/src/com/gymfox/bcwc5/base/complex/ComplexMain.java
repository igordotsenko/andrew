package com.gymfox.bcwc5.base.complex;

import java.util.Scanner;

public class ComplexMain {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        double x,y;

        System.out.print("Enter value for real: ");
        x = scan.nextDouble();
        y = scan.nextDouble();
        Complex a = new Complex(x, y);

        System.out.print("Enter value for imaginary: ");
        x = scan.nextDouble();
        y = scan.nextDouble();
        Complex b = new Complex(x, y);

        System.out.println(a.sum(b));
        System.out.println(a.diff(b));
        System.out.println(a.mult(b));
    }
}
