package com.gymfox.Army.Spellcasters;

import com.gymfox.Army.Ability.WarlockAbility;
import com.gymfox.Army.Exception.DemonIsAlreadySummonedException;
import com.gymfox.Army.Exception.DemonIsNotSummontException;
import com.gymfox.Army.Exception.IsNotSummonSpellsException;
import com.gymfox.Army.Spells.Spell;
import com.gymfox.Army.Spells.Summon;
import com.gymfox.Army.State.HumanState;
import com.gymfox.Army.Units.Demon;

public class Warlock extends Spellcaster {
    private Demon demon = null;

    public Warlock(String name, int healthPointLimit, int damage, int manaPointLimits) {
        super(name, healthPointLimit, damage, manaPointLimits);
        setUnitType(UnitType.WARLOCK);
        setAbility(new WarlockAbility(this));
        setCurrentState(new HumanState(this));
        setNextState(new HumanState(this));
        learnSpell(new Summon());
        setCurrentSpell("Summon");
    }

    public Demon summonDemon() throws IsNotSummonSpellsException, DemonIsAlreadySummonedException {
        ensureIsSummonSpell(getCurrentSpell());
        ensureIsNotSummoned();

        demon = new Demon(this);

        setCurrentMP(getCurrentMP() - getCurrentSpell().getManaConsumption());

        return demon;
    }

    public void removeDemon() {
        if ( demon == null ) {
            return;
        }

        demon = null;
    }

    private void ensureIsSummonSpell(Spell currentSpell) throws IsNotSummonSpellsException {
        if ( getCurrentSpell().getSpellsType() != Spell.SpellsType.SUMMONSPELL ) {
            throw new IsNotSummonSpellsException();
        }
    }

    private void ensureIsNotSummoned() throws DemonIsAlreadySummonedException {
        if ( demon != null ) {
            throw new DemonIsAlreadySummonedException();
        }
    }

    private void ensureIsSummoned() throws DemonIsNotSummontException {
        if ( demon == null ) {
            throw new DemonIsNotSummontException();
        }
    }

    public Demon getDemon() throws DemonIsNotSummontException {
        ensureIsSummoned();

        return demon;
    }
}
