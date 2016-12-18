/**
 * Created by andrew on 03.12.16.
 */
public class GunMain {
    public static void main(String[] args) throws OutOfRoundsException {
        Gun beretta = new Gun("Beretta", 12);

        System.out.println(beretta);
        System.out.println("Reloaded...");
        beretta.reload();
        System.out.println("Preparing...");
        beretta.prepare();
        System.out.println(beretta);

        for ( int i = 0; i < 12; i++ ) {
            beretta.shoot();
        }
        System.out.println(beretta);
    }
}
