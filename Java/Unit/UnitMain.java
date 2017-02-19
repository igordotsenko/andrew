/**
 * Created by andrew on 04.12.16.
 */
public class UnitMain {
    public static void main(String[] args) throws UnitIsDeadException, CloneNotSupportedException {
        Unit barberain = new Unit("Barberian", 150, 20);
        Unit knight = new Unit("Knight", 120, 15);

        System.out.println(barberain);
        System.out.println(knight);

        barberain.attack(knight);

        System.out.println(barberain);
        System.out.println(knight);
    }
}
