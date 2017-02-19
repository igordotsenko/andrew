public class Unit implements Cloneable{
    private String name;
    private int hitPoints;
    private int hitPointsLimit;
    private int damage;

    public Unit(String name, int hitPoint, int damage) {
        this.name = name;
        this.hitPoints = hitPoint;
        this.hitPointsLimit = hitPoint;
        this.damage = damage;
    }

    public void addHitPoints(int hp) throws UnitIsDeadException {
        ensureIsAlive();

        if (hitPointsLimit - hitPoints < hp) {
            hitPoints = hitPointsLimit;

            return;
        }
        hitPoints += hp;
    }

    public void takeDamage(int dmg) throws UnitIsDeadException {
        ensureIsAlive();

        hitPoints -= dmg;

        if ( hitPoints <= 0 ) {
            hitPoints = 0;
        }
    }

    public void attack(Unit enemy) throws UnitIsDeadException {
        ensureIsAlive();

        enemy.takeDamage(damage);
        System.out.println(getName() + " taked " + damage + " damage.\n");

        enemy.counterAttack(this);
    }

    public void counterAttack(Unit enemy) throws UnitIsDeadException {
        ensureIsAlive();

        enemy.takeDamage(damage/2);

        System.out.println(getName() + " counterattacking and taked " + damage/2 + " damage.\n");
    }

    public String toString() {
        StringBuffer out = new StringBuffer();

        out.append("Unit: " + getName() + "\n");
        out.append("Health: " + getHitPointsLimit() + "/" + getHitPoints() + "\n");
        out.append("Unit's damage: " + getDamage() + "\n");

        return out.toString();
    }

    public Unit clone() throws CloneNotSupportedException{
        return (Unit) super.clone();
    }

    public int getDamage() {
        return damage;
    }

    public int getHitPoints() {
        return hitPoints;
    }

    public int getHitPointsLimit() {
        return hitPointsLimit;
    }

    public String getName() {
        return name;
    }

    private void ensureIsAlive() throws UnitIsDeadException{
        if ( hitPoints == 0 ) {
            throw new UnitIsDeadException();
        }
    }
}
