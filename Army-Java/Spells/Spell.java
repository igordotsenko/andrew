package com.gymfox.Army.Spells;

public abstract class Spell {

    public enum SpellsType{
        BATTLESPELL,
        HEALSPELL,
        SUMMONSPELL
    }

    private String spellsName;
    private int manaConsumption;
    private int hitPoints;
    private SpellsType spellsType;

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

    public void setSpellsName(String newSpellsName) {
        this.spellsName = newSpellsName;
    }

    public void setManaConsumption(int newManaConsumption) {
        this.manaConsumption = newManaConsumption;
    }

    public void setHitPoints(int newHitPoints) {
        this.hitPoints = newHitPoints;
    }

    public void setSpellsType(SpellsType newSpellsType) {
        this.spellsType = newSpellsType;
    }
}
