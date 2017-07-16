package com.gymfox.Army.Spellcasters;

import com.gymfox.Army.Ability.NecroAbility;
import com.gymfox.Army.Exception.IsSelfAttackException;
import com.gymfox.Army.Exception.ManaIsOverException;
import com.gymfox.Army.Exception.MasterAttackedException;
import com.gymfox.Army.Exception.UnitIsDeadException;
import com.gymfox.Army.State.HumanState;
import com.gymfox.Army.Units.Unit;

public class Necromancer extends Spellcaster {
    public Necromancer(String name, int healthPointLimit, int damage, int manaPointLimits) {
        super(name, healthPointLimit, damage, manaPointLimits);
        setUnitType(UnitType.NECROMANCER);
        setAbility(new NecroAbility(this));
        setCurrentState(new HumanState(this));
        setNextState(new HumanState(this));
        setCurrentSpell("Fireball");
        setDead();
    }

    @Override
    public void castSpell(Unit victim) throws IsSelfAttackException, UnitIsDeadException, ManaIsOverException {
        if ( victim.getUnitType() == UnitType.BERSERK ) {
            return;
        }
        addObservable(victim);
        super.castSpell(victim);
    }

    @Override
    public void attack(Unit victim) throws UnitIsDeadException, IsSelfAttackException, MasterAttackedException {
        addObservable(victim);
        super.attack(victim);
    }
}
