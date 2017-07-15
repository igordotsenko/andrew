package com.gymfox.Army.Spells;

public class Fireball extends Spell {
    public Fireball() {
        setSpellsType(SpellsType.BATTLESPELL);
        setSpellsName("Fireball");
        setManaConsumption(8);
        setHitPoints(12);
    }
}
