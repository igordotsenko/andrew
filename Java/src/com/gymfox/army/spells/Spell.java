package com.gymfox.army.spells;

import com.gymfox.army.magicskills.MagicSkills;
import com.gymfox.army.units.Unit;

public abstract class Spell {
    private String spellsName;
    private int manaConsumption;
    private int hitPoints;
    protected boolean isBattleSpell = false;

    public Spell(String spellsName, int manaConsumption, int hitPoints) {
        this.spellsName = spellsName;
        this.manaConsumption = manaConsumption;
        this.hitPoints = hitPoints;
    }

    public void applySpell(Unit victim, MagicSkills magicPower) throws Unit.UnitIsDeadException {
        if ( isBattleSpell() ) {
            victim.takeMagicDamage(battleSpellDamagePoints(magicPower));

            return;
        }
        victim.heal(healSpellPoints(magicPower));
    }

    private int battleSpellDamagePoints(MagicSkills battleDamage) {
        return (int) (getHitPoints() * battleDamage.getBattleMagicSkill());
    }

    private int healSpellPoints(MagicSkills healPoints) {
        return (int) (getHitPoints() * healPoints.getHealingMagicSkill());
    }

    public String getSpellsName() {
        return spellsName;
    }

    public int getManaConsumption() {
        return manaConsumption;
    }

    public int getHitPoints() {
        return hitPoints;
    }

    public boolean isBattleSpell() {
        return isBattleSpell;
    }
}
