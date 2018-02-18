package com.gymfox.army.Spellcasters;

import com.gymfox.army.Ability.DefaultAbility;
import com.gymfox.army.MagicSkills.MagicSkills;
import com.gymfox.army.Spells.Fireball;
import com.gymfox.army.Spells.Heal;
import com.gymfox.army.Spells.Spell;
import com.gymfox.army.Units.Demon;
import com.gymfox.army.Units.Unit;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Necromancer extends Spellcaster {
    private static double DEFAULT_BATTLE_MAGIC_SKILL = 1.0;
    private static double DEFAULT_HEAL_MAGIC_SKILL = 0.5;
    private final static List<Spell> DEFAULT_SPELL_BOOK = Collections.unmodifiableList(Arrays.asList(new Fireball(),
            new Heal()));

    public Necromancer(String name, int healthPointLimit, int damage, int manaPointLimits) {
        super(name, healthPointLimit, damage, manaPointLimits, new MagicSkills(DEFAULT_BATTLE_MAGIC_SKILL,
                        DEFAULT_HEAL_MAGIC_SKILL), DEFAULT_SPELL_BOOK);
        this.ability = new DefaultAbility(this);
        this.isDead = true;
    }

    @Override
    public void castSpell(Unit victim) throws IsSelfAttackException, UnitIsDeadException, ManaIsOverException {
        if ( victim.getImmunityToMagic() ) {
            return;
        }

        addObservable(victim);
        super.castSpell(victim);
    }

    @Override
    public void attack(Unit victim) throws UnitIsDeadException, IsSelfAttackException, Demon.MasterAttackedException {
        addObservable(victim);
        super.attack(victim);
    }
}
