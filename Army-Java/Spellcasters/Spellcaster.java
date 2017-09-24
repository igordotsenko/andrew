package com.gymfox.Army.Spellcasters;

import com.gymfox.Army.MagicSkills.MagicSkills;
import com.gymfox.Army.Spells.Fireball;
import com.gymfox.Army.Spells.Heal;
import com.gymfox.Army.Spells.Spell;
import com.gymfox.Army.Units.Unit;

import java.util.*;

public abstract class Spellcaster extends Unit {
    private int manaPointLimits;
    private int currentMP;
    private Spell currentSpell;
    private MagicSkills magicPower;
    private Map<String, Spell> spellbook = new HashMap<>();
    private static final String DEFAULT_SPELL = "Fireball";
    private final static List<Spell> DEFAULT_SPELL_BOOK = Collections.unmodifiableList(Arrays.asList(new Fireball(),
            new Heal()));

    public static class ManaIsOverException extends Exception{}

    public Spellcaster(String name, int healthPointLimit, int damage, int manaPointLimits, MagicSkills magicPower,
                       List<Spell> DEFAULT_SPELL_BOOK) {
        this(name, healthPointLimit, damage, manaPointLimits, magicPower, DEFAULT_SPELL_BOOK, DEFAULT_SPELL);
    }

    public Spellcaster(String name, int healthPointLimit, int damage, int manaPointLimits, MagicSkills magicPower,
                       String spell) {
        this(name, healthPointLimit, damage, manaPointLimits, magicPower, DEFAULT_SPELL_BOOK, spell);
    }

    public Spellcaster(String name, int healthPointLimit, int damage, int manaPointLimits, MagicSkills magicPower,
                       List<Spell> spells, String currentSpell) {
        super(name, healthPointLimit, damage);
        this.manaPointLimits = manaPointLimits;
        this.currentMP = manaPointLimits;
        learnAllSpells(spells);
        this.magicPower = magicPower;
        this.currentSpell = spellbook.get(currentSpell);
    }

    private void learnAllSpells(List<Spell> spells) {
        for (Spell spell : spells) {
            spellbook.put(spell.getSpellsName(), spell);
        }
    }

    public void castSpell(Unit victim) throws IsSelfAttackException, UnitIsDeadException, ManaIsOverException {
        ensureIsNotSelfAttack(victim);
        ensureIsAlive();
        ensureManaIsNotOver();

        getCurrentSpell().applySpell(victim, magicPower);

        setCurrentMP(getCurrentMP() - getCurrentSpell().getManaConsumption());
    }

    protected void ensureManaIsNotOver() throws ManaIsOverException {
        if ( getCurrentMP() < getCurrentSpell().getManaConsumption() ) {
            throw new ManaIsOverException();
        }
    }

    protected void learnSpell(Spell newSpell) {
        spellbook.put(newSpell.getSpellsName(), newSpell);
    }

    public int getManaPointLimits() {
        return manaPointLimits;
    }

    public int getCurrentMP() {
        return currentMP;
    }

    public Spell getCurrentSpell() {
        return currentSpell;
    }

    public void setCurrentMP(int newCurrentMP) {
        this.currentMP = newCurrentMP;
    }

    public void setCurrentSpell(String newCurrentSpell) {
        currentSpell = spellbook.get(newCurrentSpell);
    }
}
