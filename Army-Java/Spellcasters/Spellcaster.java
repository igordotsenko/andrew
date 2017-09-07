package com.gymfox.Army.Spellcasters;

import com.gymfox.Army.MagicSkills.MagicSkills;
import com.gymfox.Army.Spells.*;
import com.gymfox.Army.Units.Unit;

import java.util.HashMap;
import java.util.Map;

public abstract class Spellcaster extends Unit {
    private int manaPointLimits;
    private int currentMP;
    private Spell currentSpell;
    private Map<String, Spell> spellbook = new HashMap<>();
    private MagicSkills magicPower;

    public static class ManaIsOverException extends Exception{}

    public Spellcaster(String name, int healthPointLimit, int damage, int manaPointLimits, MagicSkills magicPower,
                       String currentSpell) {
        super(name, healthPointLimit, damage);
        this.manaPointLimits = manaPointLimits;
        this.currentMP = manaPointLimits;
        this.learnSpell(new Fireball());
        this.learnSpell(new Heal());
        this.magicPower = magicPower;
        this.currentSpell = spellbook.get(currentSpell);
    }


    public void castSpell(Unit victim) throws IsSelfAttackException, UnitIsDeadException, ManaIsOverException {
        ensureIsNotSelfAttack(victim);
        ensureIsAlive();
        ensureManaIsNotOver();

        applySpell(victim);
        setCurrentMP(getCurrentMP() - getCurrentSpell().getManaConsumption());
    }

    public void applySpell(Unit victim) throws UnitIsDeadException {
        if ( currentSpell.isBattleSpell() ) {
            victim.takeMagicDamage(battleSpellPoints());

            return;
        }
        victim.heal(healingSpellPoints());
    }

    public int battleSpellPoints() {
        return (int) ((double)currentSpell.getHitPoints() * magicPower.getBattleMagicSkill());
    }

    public int healingSpellPoints() {
        return (int) ((double)currentSpell.getHitPoints() * magicPower.getHealingMagicSkill());
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
