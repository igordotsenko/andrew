package com.gymfox.Army.Spellcasters;

import com.gymfox.Army.Ability.DefaultAbility;
import com.gymfox.Army.MagicSkills.MagicSkills;
import com.gymfox.Army.Units.Demon;
import com.gymfox.Army.Units.Unit;

public class Necromancer extends Spellcaster {
    private static double DEFAULT_BATTLE_MAGIC_SKILL = 1.0;
    private static double DEFAULT_HEAL_MAGIC_SKILL = 0.5;

    public Necromancer(String name, int healthPointLimit, int damage, int manaPointLimits) {
        super(name, healthPointLimit, damage, manaPointLimits, new MagicSkills(DEFAULT_BATTLE_MAGIC_SKILL,
                        DEFAULT_HEAL_MAGIC_SKILL),
                "Fireball");
        this.ability = new DefaultAbility(this);
        this.isDead = true;
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
