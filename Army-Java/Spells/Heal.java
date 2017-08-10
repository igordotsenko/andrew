package com.gymfox.Army.Spells;

public class Heal extends Spell {
    public Heal() {
        this.spellsType = SpellsType.HEALSPELL;
        this.spellsName = "Heal";
        this.manaConsumption = 6;
        this.hitPoints = 10;
    }
}
