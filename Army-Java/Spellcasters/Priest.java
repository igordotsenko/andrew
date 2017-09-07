package com.gymfox.Army.Spellcasters;

import com.gymfox.Army.Ability.DefaultAbility;
import com.gymfox.Army.MagicSkills.MagicSkills;
import com.gymfox.Army.Units.Unit;

public class Priest extends Spellcaster {
    private static double DEFAULT_BATTLE_MAGIC_SKILL = 0.5;
    private static double DEFAULT_HEAL_MAGIC_SKILL = 2.0;

    public Priest(String name, int healthPointLimit, int damage, int manaPointLimits) {
        super(name, healthPointLimit, damage, manaPointLimits, new MagicSkills(DEFAULT_BATTLE_MAGIC_SKILL,
                        DEFAULT_HEAL_MAGIC_SKILL),
                "Heal");
        this.ability = new DefaultAbility(this);
    }

    @Override
    public void applySpell(Unit victim) throws UnitIsDeadException {
        if ( getCurrentSpell().isBattleSpell() ) {
            if ( victim.getIsDead() ) {
                victim.takeMagicDamage(getCurrentSpell().getHitPoints() * 2);

                return;
            }
            victim.takeMagicDamage(battleSpellPoints());

            return;
        }

        victim.heal(healingSpellPoints());
    }
}
