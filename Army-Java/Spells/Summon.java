package com.gymfox.Army.Spells;

public class Summon extends Spell {
    public Summon() {
        this.spellsType = SpellsType.SUMMONSPELL;
        this.spellsName = "Summon";
        this.manaConsumption = 12;
        this.hitPoints = 0;
    }
}
