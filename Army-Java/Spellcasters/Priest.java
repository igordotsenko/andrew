package com.gymfox.Army.Spellcasters;

import com.gymfox.Army.Ability.PriestAbility;
import com.gymfox.Army.MagicSkills.MagicSkills;
import com.gymfox.Army.Units.Unit;

public class Priest extends Spellcaster {
    public Priest(String name, int healthPointLimit, int damage, int manaPointLimits) {
        super(name, healthPointLimit, damage, manaPointLimits);
        this.ability = new PriestAbility(this);
        this.currentSpell = spellbook.get("Heal");
        this.magicPower = new MagicSkills(0.5,2);
    }

    @Override
    public void applySpell(Unit victim) throws UnitIsDeadException {
        if ( currentSpell.isBattleSpell() ) {
            if ( victim.getIsDead() ) {
                victim.takeMagicDamage(battleSpellPoints()*4);

                return;
            }
            victim.takeMagicDamage(battleSpellPoints());

            return;
        }
        victim.heal(healingSpellPoints());
    }
}
