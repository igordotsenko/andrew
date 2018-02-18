/**
 * Created by andrew on 18.11.16.
 */
public class PointMain {
    public static void main(String[] args) throws CloneNotSupportedException {
        Point a = new Point(2.7, 8.3);
        Point b = new Point(5.3, 5.7);
        Point c = b.clone();

        System.out.println("a: " + a);
        System.out.println("b: " + b);
        System.out.println("c: " + c);

        System.out.println("Distance: " + a.distance(b) );

        if (b.equals(c)) {
            System.out.println("Points are equals");
        } else {
            System.out.println("Point aren't equals");
        }

        c = a.clone();

        if (c.equals(a)) {
            System.out.println("Points are equals");
        } else {
            System.out.println("Point aren't equals");
        }

    }
}
