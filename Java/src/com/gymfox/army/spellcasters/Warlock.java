package com.gymfox.army.spellcasters;

import com.gymfox.army.ability.DefaultAbility;
import com.gymfox.army.magicskills.MagicSkills;
import com.gymfox.army.spells.Fireball;
import com.gymfox.army.spells.Heal;
import com.gymfox.army.spells.Spell;
import com.gymfox.army.units.Demon;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Warlock extends Spellcaster {
    private static double DEFAULT_BATTLE_MAGIC_SKILL = 0.5;
    private static double DEFAULT_HEAL_MAGIC_SKILL = 0.5;
    private final static List<Spell> DEFAULT_SPELL_BOOK = Collections.unmodifiableList(Arrays.asList(new Fireball(),
            new Heal()));
    private final int SUMMON_MANA_CONSUMPTION = 12;

    public static class DemonIsAlreadySummonedException extends Exception{}
    public static class DemonIsNotSummonedException extends Exception{}

    private Demon demon = null;

    public Warlock(String name, int healthPointLimit, int damage, int manaPointLimits) {
        super(name, healthPointLimit, damage, manaPointLimits, new MagicSkills(DEFAULT_BATTLE_MAGIC_SKILL,
                        DEFAULT_HEAL_MAGIC_SKILL), DEFAULT_SPELL_BOOK);
        this.ability = new DefaultAbility(this);
    }

    public Demon summonDemon() throws DemonIsAlreadySummonedException,
            Spellcaster.ManaIsOverException {
        ensureManaIsNotOver();
        ensureIsNotSummoned();

        demon = new Demon(this);

        setCurrentMP(getCurrentMP() - SUMMON_MANA_CONSUMPTION);

        return demon;
    }

    public void removeDemon() throws UnitIsDeadException {
        if ( demon == null ) {
            return;
        }

        demon.notifyObservers();
        demon = null;
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
