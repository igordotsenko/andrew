package com.gymfox.Army.Spellcasters;

import com.gymfox.Army.Ability.WarlockAbility;
import com.gymfox.Army.Exception.*;
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

    public Demon summonDemon() throws IsNotSummonSpellsException, DemonIsAlreadySummonedException, ManaIsOverException {
        ensureIsSummonSpell(getCurrentSpell());
        ensureManaIsNotOver();
        ensureIsNotSummoned();

        demon = new Demon(this);

        setCurrentMP(getCurrentMP() - getCurrentSpell().getManaConsumption());

        return demon;
    }

    public void removeDemon() throws UnitIsDeadException {
        if ( demon == null ) {
            return;
        }
        demon.notifyObservers();
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

    private void ensureIsSummoned() throws DemonIsNotSummonedException {
        if ( demon == null ) {
            throw new DemonIsNotSummonedException();
        }
    }

    public Demon getDemon() throws DemonIsNotSummonedException {
        ensureIsSummoned();

        return demon;
    }
}
