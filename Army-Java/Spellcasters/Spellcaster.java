package com.gymfox.Army.Spellcasters;

import com.gymfox.Army.Exception.*;
import com.gymfox.Army.Spells.*;
import com.gymfox.Army.Units.Unit;

import java.util.HashMap;
import java.util.Map;

public abstract class Spellcaster extends Unit{
    private int manaPointLimits;
    private int currentMP;
    private Spell currentSpell;
    private Map<String, Spell> spellbook = new HashMap<>();

    public Spellcaster(String name, int healthPointLimit, int damage, int manaPointLimits) {
        super(name, healthPointLimit, damage);
        this.manaPointLimits = manaPointLimits;
        this.currentMP = manaPointLimits;
        this.learnSpell(new Fireball());
        this.learnSpell(new Heal());
    }

    public void castSpell(Unit victim) throws IsSelfAttackException, UnitIsDeadException, ManaIsOverException {
        ensureIsNotSelfAttack(victim);
        ensureIsAlive();
        ensureManaIsNotOver();

        getAbility().castSpell(victim, currentSpell);
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

    public void setManaPointLimits(int newManaPointLimits) {
        this.manaPointLimits = newManaPointLimits;
    }

    public void setCurrentMP(int newCurrentMP) {
        this.currentMP = newCurrentMP;
    }

    public void setCurrentSpell(String newCurrentSpell) {
        currentSpell = spellbook.get(newCurrentSpell);
    }
}
