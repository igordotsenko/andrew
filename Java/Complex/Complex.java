import java.util.Vector;

/**
 * Created by andrew on 18.11.16.
 */
public class Complex implements Cloneable{
    private double real;
    private double imaginary;

    public Complex(double real, double imaginary) {
        this.real = real;
        this.imaginary = imaginary;
    }

    public Complex() {
        this(0.0, 0.0);
    }

    public double getReal() {
        return real;
    }

    public double getImaginary() {
        return imaginary;
    }

    public void setReal(double real) {
        this.real = real;
    }

    public void setImaginary(double imaginary) {
        this.imaginary = imaginary;
    }

    public Complex sum(Complex other) {
        return new Complex(real + other.real, imaginary + other.imaginary);
    }

    public Complex diff(Complex other) {
        return new Complex(real - other.real, imaginary - other.imaginary);
    }

    public Complex mult(Complex other) {
        return new Complex(real * other.real - imaginary * other.imaginary, real * other.imaginary + imaginary * other.real);
    }

    public boolean equals(Complex other) {
        return real == other.real && imaginary == other.imaginary;
    }

    public Complex clone() throws CloneNotSupportedException {
        return (Complex) super.clone();
    }

    public String toString() {
        if ( imaginary < 0 ) {
            return real + "" + imaginary + "i";
        }
        return real + " + " + imaginary + "i";
    }
}
