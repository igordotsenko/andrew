import org.junit.Assert;
import org.junit.Test;

public class UnitTest {
    Unit barbarian = new Unit("Arnold", 120, 30);
    Unit knight = new Unit("Arthur", 100, 10);

    @Test
    public void attack() throws UnitIsDeadException {
        knight.attack(barbarian);
        Assert.assertEquals(barbarian.getHitPoints(), 110);
        Assert.assertEquals(knight.getHitPoints(), 85);
    }

    @Test
    public void addHitPoints1() throws UnitIsDeadException {
        knight.attack(barbarian);

        barbarian.addHitPoints(50);
        knight.addHitPoints(10);

        Assert.assertEquals(barbarian.getHitPoints(), 120);
        Assert.assertEquals(knight.getHitPoints(), 95);
    }

    @Test
    public void addHitPoints2() throws UnitIsDeadException {
        knight.attack(barbarian);

        barbarian.addHitPoints(5);
        knight.addHitPoints(5);

        Assert.assertEquals(barbarian.getHitPoints(), 115);
        Assert.assertEquals(knight.getHitPoints(), 90);
    }

}