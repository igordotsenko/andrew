package com.gymfox.Army.Ability;

import com.gymfox.Army.Exception.UnitIsDeadException;
import com.gymfox.Army.Spells.Spell;
import com.gymfox.Army.Units.Unit;

public class WizardAbility extends Ability {
    public WizardAbility(Unit currentUnit) {
        super(currentUnit);
    }

    public void castSpell(Unit victim, Spell currentSpell) throws UnitIsDeadException {
        if ( currentSpell.getSpellsType() == Spell.SpellsType.BATTLESPELL ) {
            victim.takeMagicDamage(currentSpell.getHitPoints());

            return;
        }
        if ( currentSpell.getSpellsType() == Spell.SpellsType.HEALSPELL) {
            victim.heal(currentSpell.getHitPoints() / 2);

            return;
        }
    }
}
