package com.gymfox.Army.Ability;

import com.gymfox.Army.Spells.Spell;
import com.gymfox.Army.Units.Unit;

public class WarlockAbility extends Ability {
    public WarlockAbility(Unit currentUnit) {
        super(currentUnit);
    }

    public void castSpell(Unit victim, Spell currentSpell) throws Unit.UnitIsDeadException {
        if ( currentSpell.getSpellsType() == Spell.SpellsType.BATTLESPELL ) {
            victim.takeMagicDamage(currentSpell.getHitPoints() / 2);

            return;
        }
        if ( currentSpell.getSpellsType() == Spell.SpellsType.HEALSPELL) {
            victim.heal(currentSpell.getHitPoints() / 2);

            return;
        }
    }
}
