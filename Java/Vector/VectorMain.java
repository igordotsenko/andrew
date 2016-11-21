/**
 * Created by andrew on 17.11.16.
 */
public class VectorMain {
    public static void main(String[] args) throws CloneNotSupportedException {
        Vector a = new Vector(2.3, 5.5);
        Vector b = new Vector(6.3, 2.6);
        Vector c = b.clone();

        System.out.println("Vector a = " + a);
        System.out.println("Vector b = " + b);

        System.out.println("Sum: " + a.sum(b));

        System.out.println("Distance: " + c.distance(a));

        if (c.equals(b)) {
            System.out.println("Vector c is equals to vector b");
        } else {
            System.out.println("Vector c isn't equals to vector b");
        }
    }
}