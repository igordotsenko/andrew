package com.gymfox.Army.Spells;

public class Fireball extends Spell {
    public Fireball() {
        this.spellsType = SpellsType.BATTLESPELL;
        this.spellsName = "Fireball";
        this.manaConsumption = 8;
        this.hitPoints = 12;
    }
}
