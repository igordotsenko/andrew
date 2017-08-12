package com.gymfox.Army.Spells;

public abstract class Spell {
    protected String spellsName;
    protected int manaConsumption;
    protected int hitPoints;
    protected boolean isBattleSpell = false;

    public Spell() {}

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
