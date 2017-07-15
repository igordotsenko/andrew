package com.gymfox.Army.Spells;

public class Summon extends Spell {
    public Summon() {
        setSpellsType(SpellsType.SUMMONSPELL);
        setSpellsName("Summon");
        setManaConsumption(12);
        setHitPoints(0);
    }
}
