package com.gymfox.Army.Spells;

public abstract class Spell {

    public enum SpellsType{
        BATTLESPELL,
        HEALSPELL,
        SUMMONSPELL
    }

    protected String spellsName;
    protected int manaConsumption;
    protected int hitPoints;
    protected SpellsType spellsType;

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

    public SpellsType getSpellsType() {
        return spellsType;
    }
}
