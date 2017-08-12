package com.gymfox.Army.Spellcasters;

import com.gymfox.Army.Ability.WarlockAbility;
import com.gymfox.Army.MagicSkills.MagicSkills;
import com.gymfox.Army.Spells.Summon;
import com.gymfox.Army.Units.Demon;

public class Warlock extends Spellcaster {

    public static class IsNotSummonSpellsException extends Exception{};
    public static class DemonIsAlreadySummonedException extends Exception{};
    public static class DemonIsNotSummonedException extends Exception{};

    private Demon demon = null;

    public Warlock(String name, int healthPointLimit, int damage, int manaPointLimits) {
        super(name, healthPointLimit, damage, manaPointLimits);
        this.ability = new WarlockAbility(this);
        learnSpell(new Summon());
        this.currentSpell = spellbook.get("Summon");
        this.magicPower = new MagicSkills(0.5,0.5);
    }

    public Demon summonDemon() throws IsNotSummonSpellsException, DemonIsAlreadySummonedException,
            Spellcaster.ManaIsOverException {
        ensureIsSummonSpell();
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

    private void ensureIsSummonSpell() throws IsNotSummonSpellsException {
        if ( getCurrentSpell().getSpellsName() != "Summon" ) {
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
