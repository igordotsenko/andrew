package com.gymfox.army.Units;

import com.gymfox.army.Spellcasters.Warlock;

public class Demon extends Soldier {
    public static class MasterAttackedException extends Exception{};

    private Warlock master;

    public Demon(Warlock master) {
        super("Demon", 100, 12);
        this.master = master;
    }

    private void ensureIsNotMaster(Unit target) throws MasterAttackedException {
        if ( target == getMaster() ) {
            throw new MasterAttackedException();
        }
    }

    @Override
    public void attack(Unit victim) throws Unit.UnitIsDeadException, Unit.IsSelfAttackException, MasterAttackedException {
        ensureIsNotMaster(victim);

        super.attack(victim);
    }

    public Warlock getMaster() {
        return master;
    }
}
