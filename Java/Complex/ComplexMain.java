import java.util.Scanner;

/**
 * Created by andrew on 18.11.16.
 */
public class ComplexMain {
    public static void main(String[] args) throws CloneNotSupportedException {
        Scanner scan = new Scanner(System.in);
        double x,y;

        x = scan.nextDouble();
        y = scan.nextDouble();
        Complex a = new Complex(x, y);

        x = scan.nextDouble();
        y = scan.nextDouble();
        Complex b = new Complex(x, y);

        System.out.println(a.sum(b));
        System.out.println(a.diff(b));
        System.out.println(a.mult(b));
    }
}
