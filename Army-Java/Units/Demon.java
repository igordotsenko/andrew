package com.gymfox.Army.Units;

import com.gymfox.Army.Exception.IsSelfAttackException;
import com.gymfox.Army.Exception.MasterAttackedException;
import com.gymfox.Army.Exception.UnitIsDeadException;
import com.gymfox.Army.Spellcasters.Warlock;

public class Demon extends Soldier {
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
    public void attack(Unit victim) throws UnitIsDeadException, IsSelfAttackException, MasterAttackedException {
        ensureIsNotMaster(victim);

        super.attack(victim);
    }

    public Warlock getMaster() {
        return master;
    }
}
