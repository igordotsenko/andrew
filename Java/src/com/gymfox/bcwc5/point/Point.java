/**
 * Created by andrew on 18.11.16.
 */
public class Point implements Cloneable{
    private double x;
    private double y;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Point() {
        this(0.0, 0.0);
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public Point clone() throws CloneNotSupportedException {
        return (Point) super.clone();
    }

    public boolean equals(Point other) {
        return x == other.x && y == other.y;
    }

    public double distance(Point p) {
        return Math.hypot(x - p.x, y - p.y);
    }

    public String toString() {
        return "(" + x + "," + y + ")";
    }
}
