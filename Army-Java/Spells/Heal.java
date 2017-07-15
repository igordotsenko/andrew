package com.gymfox.Army.Spells;

public class Heal extends Spell {
    public Heal() {
        setSpellsType(SpellsType.HEALSPELL);
        setSpellsName("Heal");
        setManaConsumption(6);
        setHitPoints(10);
    }
}
