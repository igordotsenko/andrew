package com.gymfox.Army.Spellcasters;

import com.gymfox.Army.Ability.NecroAbility;
import com.gymfox.Army.Units.Demon;
import com.gymfox.Army.Units.Unit;

public class Necromancer extends Spellcaster {
    public Necromancer(String name, int healthPointLimit, int damage, int manaPointLimits) {
        super(name, healthPointLimit, damage, manaPointLimits);
        this.ability = new NecroAbility(this);
        setCurrentSpell("Fireball");
        setDead();
    }

    @Override
    public void castSpell(Unit victim) throws IsSelfAttackException, UnitIsDeadException, ManaIsOverException {

        if ( victim.getImmunityToMagic() ) {
            return;
        }

        addObservable(victim);
        super.castSpell(victim);
    }

    @Override
    public void attack(Unit victim) throws UnitIsDeadException, IsSelfAttackException, Demon.MasterAttackedException {
        addObservable(victim);
        super.attack(victim);
    }
}
