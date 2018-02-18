package com.gymfox.Army.Spellcasters;

import com.gymfox.Army.Ability.DefaultAbility;
import com.gymfox.Army.MagicSkills.MagicSkills;
import com.gymfox.Army.Spells.Fireball;
import com.gymfox.Army.Spells.Heal;
import com.gymfox.Army.Spells.Spell;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Healer extends Spellcaster {
    private static double DEFAULT_BATTLE_MAGIC_SKILL = 0.5;
    private static double DEFAULT_HEAL_MAGIC_SKILL = 1.0;
    private final static List<Spell> DEFAULT_SPELL_BOOK = Collections.unmodifiableList(Arrays.asList(new Fireball(),
            new Heal()));

    public Healer(String name, int healthPointLimit, int damage, int manaPointLimits) {
        super(name, healthPointLimit, damage, manaPointLimits, new MagicSkills(DEFAULT_BATTLE_MAGIC_SKILL,
                        DEFAULT_HEAL_MAGIC_SKILL), DEFAULT_SPELL_BOOK);
        this.ability = new DefaultAbility(this);
    }
}
