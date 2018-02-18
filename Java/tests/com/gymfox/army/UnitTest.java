package com.gymfox.army;

import com.gymfox.army.Spellcasters.*;
import com.gymfox.army.Units.*;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;

public class UnitTest {
    @Test
    public void allUnitsGettersTest() {
        Soldier soldier = new Soldier("Steve", 100, 12);
        Rogue rogue = new Rogue("Robin", 100, 8);
        Berserk berserk = new Berserk("Viking", 120, 20);
        Vampire vamp = new Vampire("Count Dracula", 100, 12);
        Werewolf wolf = new Werewolf("Van Hellsing", 80, 10);
        Wizard wizard = new Wizard("Marylin", 90, 5, 120);
        Healer healer = new Healer("Manson", 60, 4, 100);
        Priest priest = new Priest("Francis", 90, 10, 120);
        Warlock warlock = new Warlock("Warlock", 90, 8, 120);
        Necromancer necro = new Necromancer("Freddy", 50, 10, 100);

        assertEquals("Steve", soldier.getName());
        assertEquals(100, soldier.getHealthPointLimit());
        assertEquals(100, soldier.getCurrentHP());
        assertEquals(12, soldier.getDamage());
        assertEquals(false, soldier.getIsDead());

        assertEquals("Robin", rogue.getName());
        assertEquals(100, rogue.getHealthPointLimit());
        assertEquals(100, rogue.getCurrentHP());
        assertEquals(8, rogue.getDamage());
        assertEquals(false, rogue.getIsDead());

        assertEquals("Viking", berserk.getName());
        assertEquals(120, berserk.getHealthPointLimit());
        assertEquals(120, berserk.getCurrentHP());
        assertEquals(20, berserk.getDamage());
        assertEquals(false, berserk.getIsDead());

        assertEquals("Count Dracula", vamp.getName());
        assertEquals(100, vamp.getHealthPointLimit());
        assertEquals(100, vamp.getCurrentHP());
        assertEquals(12, vamp.getDamage());
        assertEquals(true, vamp.getIsDead());

        assertEquals("Van Hellsing", wolf.getName());
        assertEquals(80, wolf.getHealthPointLimit());
        assertEquals(80, wolf.getCurrentHP());
        assertEquals(10, wolf.getDamage());
        assertEquals(false, wolf.getIsDead());

        wolf.changeState();

        assertEquals("Van Hellsing", wolf.getName());
        assertEquals(160, wolf.getHealthPointLimit());
        assertEquals(160, wolf.getCurrentHP());
        assertEquals(20, wolf.getDamage());
        assertEquals(false, wolf.getIsDead());

        assertEquals("Marylin", wizard.getName());
        assertEquals(90, wizard.getHealthPointLimit());
        assertEquals(90, wizard.getCurrentHP());
        assertEquals(5, wizard.getDamage());
        assertEquals(120, wizard.getManaPointLimits());
        assertEquals(120, wizard.getCurrentMP());
        assertEquals(false, wizard.getIsDead());

        assertEquals("Manson", healer.getName());
        assertEquals(60, healer.getHealthPointLimit());
        assertEquals(60, healer.getCurrentHP());
        assertEquals(4, healer.getDamage());
        assertEquals(100, healer.getManaPointLimits());
        assertEquals(100, healer.getCurrentMP());
        assertEquals(false, healer.getIsDead());

        assertEquals("Francis", priest.getName());
        assertEquals(90, priest.getHealthPointLimit());
        assertEquals(90, priest.getCurrentHP());
        assertEquals(10, priest.getDamage());
        assertEquals(120, priest.getManaPointLimits());
        assertEquals(120, priest.getCurrentMP());
        assertEquals(false, priest.getIsDead());

        assertEquals("Warlock", warlock.getName());
        assertEquals(90, warlock.getHealthPointLimit());
        assertEquals(90, warlock.getCurrentHP());
        assertEquals(8, warlock.getDamage());
        assertEquals(120, warlock.getManaPointLimits());
        assertEquals(120, warlock.getCurrentMP());
        assertEquals(false, warlock.getIsDead());

        assertEquals("Freddy", necro.getName());
        assertEquals(50, necro.getHealthPointLimit());
        assertEquals(50, necro.getCurrentHP());
        assertEquals(10, necro.getDamage());
        assertEquals(100, necro.getManaPointLimits());
        assertEquals(100, necro.getCurrentMP());
        assertEquals(true, necro.getIsDead());
    }

    @Test ( expected = Unit.UnitIsDeadException.class )
    public void unitIsDeadExceptionTest() throws Unit.UnitIsDeadException, Unit.IsSelfAttackException,
            Demon.MasterAttackedException {
        Soldier soldier = new Soldier("Steve", 100, 12);
        Rogue rogue = new Rogue("Robin", 10, 8);

        soldier.attack(rogue);
        soldier.attack(rogue);
    }

    @Test ( expected = Unit.IsSelfAttackException.class )
    public void isSelfAttackExceptionTest() throws Unit.UnitIsDeadException, Unit.IsSelfAttackException,
            Demon.MasterAttackedException {
        Soldier soldier = new Soldier("Steve", 100, 12);

        soldier.attack(soldier);
    }

    @Test ( expected = Spellcaster.ManaIsOverException.class)
    public void manaIsOverExceptionTest() throws Unit.IsSelfAttackException, Spellcaster.ManaIsOverException, Unit
            .UnitIsDeadException {
        Wizard wizard = new Wizard("Marylin", 90, 5, 120);
        Healer healer = new Healer("Manson", 60, 4, 100);
        Priest priest = new Priest("Francis", 90, 10, 120);

        wizard.setCurrentMP(5);
        healer.setCurrentMP(5);
        priest.setCurrentMP(5);

        wizard.castSpell(healer);
        healer.castSpell(priest);
        priest.castSpell(wizard);
    }

    @Test ( expected = Spellcaster.ManaIsOverException.class )
    public void manaIsOverExceptionForPriestTest() throws Unit.IsSelfAttackException, Spellcaster.ManaIsOverException, Unit.UnitIsDeadException {
        Priest priest = new Priest("Francis", 90, 10, 120);
        Vampire vamp = new Vampire("Count Dracula", 100, 12);

        priest.setCurrentSpell("Fireball");
        priest.setCurrentMP(5);
        priest.castSpell(vamp);
    }

    @Test
    public void priestHealsUndead() throws Unit.IsSelfAttackException, Spellcaster.ManaIsOverException, Unit
            .UnitIsDeadException {
        Priest priest = new Priest("Francis", 90, 10, 120);
        Vampire vamp = new Vampire("Count Dracula", 100, 12);

        priest.setCurrentSpell("Heal");
        priest.castSpell(vamp);

        assertEquals(100, vamp.getCurrentHP());
    }

    @Test ( expected = Spellcaster.ManaIsOverException.class )
    public void manaIsOverExceptionForWarlockTest() throws Unit.IsSelfAttackException, Spellcaster.ManaIsOverException,
            Unit
            .UnitIsDeadException, Warlock.DemonIsAlreadySummonedException {
        Warlock warlock = new Warlock("Warlock", 90, 8, 120);
        Wizard wizard = new Wizard("Marylin", 90, 5, 120);

        wizard.setCurrentMP(5);
        warlock.setCurrentMP(5);

        warlock.summonDemon();
        wizard.castSpell(warlock);
    }

    @Test
    public void soldierTest() throws Unit.UnitIsDeadException, Unit.IsSelfAttackException, Demon
            .MasterAttackedException {
        Soldier soldier1 = new Soldier("Steve", 100, 12);
        Soldier soldier2 = new Soldier("Baki", 100, 14);

        soldier1.attack(soldier2);

        assertEquals(88, soldier2.getCurrentHP());
        assertEquals(93, soldier1.getCurrentHP());

        soldier2.attack(soldier1);

        assertEquals(79, soldier1.getCurrentHP());
        assertEquals(82, soldier2.getCurrentHP());
    }

    @Test
    public void rogueTest() throws Unit.UnitIsDeadException, Unit.IsSelfAttackException, Demon.MasterAttackedException {
        Rogue rogue = new Rogue("Robin", 100, 8);
        Soldier soldier = new Soldier("Steve", 100, 12);

        rogue.attack(soldier);

        assertEquals(100, rogue.getCurrentHP());
        assertEquals(92, soldier.getCurrentHP());
    }

    @Test
    public void berserkAndWizardTest() throws Unit.IsSelfAttackException, Spellcaster.ManaIsOverException, Unit
            .UnitIsDeadException {
        Berserk berserk = new Berserk("Viking", 120, 20);
        Wizard wizard = new Wizard("Marylin", 90,5,120);

        wizard.castSpell(berserk);
        assertEquals(120, berserk.getCurrentHP());
        assertEquals(112, wizard.getCurrentMP());
    }

    @Test
    public void wizardAttackTest() throws Unit.IsSelfAttackException, Spellcaster.ManaIsOverException, Unit
            .UnitIsDeadException {
        Wizard wizard = new Wizard("Marylin", 90,5,120);
        Soldier soldier = new Soldier("Steve", 100, 18);

        wizard.castSpell(soldier);

        assertEquals(88, soldier.getCurrentHP());
    }

    @Test
    public void wizardHealTest() throws Unit.IsSelfAttackException, Spellcaster.ManaIsOverException, Unit
            .UnitIsDeadException {
        Wizard wizard = new Wizard("Marylin", 90,5,120);
        Soldier soldier = new Soldier("Steve", 100, 18);

        soldier.setCurrentHP(50);
        wizard.setCurrentSpell("Heal");

        wizard.castSpell(soldier);

        assertEquals(55, soldier.getCurrentHP());
        assertEquals(114, wizard.getCurrentMP());
    }

    @Test
    public void vampireTest() throws Unit.UnitIsDeadException, Unit.IsSelfAttackException,
            Demon.MasterAttackedException {
        Soldier soldier = new Soldier("Steve", 100, 18);
        Vampire vamp = new Vampire("Count Dracula", 100, 12);

        vamp.attack(soldier);

        assertEquals(80, soldier.getCurrentHP());
        assertEquals(91, vamp.getCurrentHP());

        soldier.attack(vamp);
        assertEquals(80, vamp.getCurrentHP());
        assertEquals(67, soldier.getCurrentHP());
    }

    @Test
    public void werewolfTest() throws Unit.IsSelfAttackException, Spellcaster.ManaIsOverException, Unit
            .UnitIsDeadException {
        Werewolf wolf = new Werewolf("Van Hellsing", 80, 10);
        Wizard wizard = new Wizard("Marylin", 90, 5, 120);

        wizard.castSpell(wolf);
        assertEquals(68, wolf.getCurrentHP());

        wolf.changeState();

        wizard.castSpell(wolf);
        assertEquals(112, wolf.getCurrentHP());
    }

    @Test
    public void healerTest() throws Unit.IsSelfAttackException, Spellcaster.ManaIsOverException, Unit
            .UnitIsDeadException {
        Healer healer = new Healer("Manson", 60, 4, 100);
        Soldier soldier = new Soldier("Steve", 100, 18);

        healer.setCurrentSpell("Fireball");

        healer.castSpell(soldier);

        assertEquals(94, soldier.getCurrentHP());
        assertEquals(92, healer.getCurrentMP());
    }

    @Test
    public void priestTest() throws Unit.IsSelfAttackException, Spellcaster.ManaIsOverException, Unit
            .UnitIsDeadException {
        Priest priest = new Priest("Francis", 90, 10, 120);
        Soldier soldier = new Soldier("Steve", 100, 18);
        Vampire vamp = new Vampire("Count Dracula", 100, 12);

        priest.setCurrentSpell("Fireball");

        priest.castSpell(soldier);
        priest.castSpell(vamp);

        assertEquals(94, soldier.getCurrentHP());
        assertEquals(76, vamp.getCurrentHP());
    }

    @Test
    public void healTest() throws Unit.UnitIsDeadException {
        Soldier soldier = new Soldier("Steve", 100, 18);

        soldier.setCurrentHP(88);

        soldier.heal(soldier.getDamage());

        assertEquals(100, soldier.getCurrentHP());
    }

    @Test
    public void warlockTest() throws Warlock.DemonIsAlreadySummonedException, Unit
            .UnitIsDeadException, Unit.IsSelfAttackException, Demon.MasterAttackedException,
            Warlock.DemonIsNotSummonedException,
            Spellcaster.ManaIsOverException {
        Warlock warlock = new Warlock("Warlock", 90, 8, 120);
        Soldier soldier = new Soldier("Steve", 100, 18);

        warlock.summonDemon();

        warlock.getDemon().attack(soldier);

        assertEquals(88, soldier.getCurrentHP());
        assertEquals(91, warlock.getDemon().getCurrentHP());
    }

    @Test ( expected = Warlock.DemonIsAlreadySummonedException.class )
    public void demonIsAlreadySummonedExceptionTest() throws Warlock.DemonIsAlreadySummonedException,
            Spellcaster.ManaIsOverException {
        Warlock warlock = new Warlock("Warlock", 90, 8, 120);

        warlock.summonDemon();
        warlock.summonDemon();
    }

    @Test ( expected = Demon.MasterAttackedException.class )
    public void masterAttackedExceptionTest() throws Warlock.DemonIsAlreadySummonedException,
            Unit.UnitIsDeadException, Unit.IsSelfAttackException, Demon.MasterAttackedException,
            Warlock.DemonIsNotSummonedException, Spellcaster.ManaIsOverException {
        Warlock warlock = new Warlock("Warlock", 90, 8, 120);

        warlock.summonDemon();

        warlock.getDemon().attack(warlock);
    }



    @Test ( expected = Warlock.DemonIsNotSummonedException.class )
    public void demonIsNotSummonedExceptionTest() throws Warlock.DemonIsNotSummonedException, Unit
            .UnitIsDeadException, Unit
            .IsSelfAttackException, Demon.MasterAttackedException {
        Warlock warlock = new Warlock("Warlock", 90, 8, 120);
        Soldier soldier = new Soldier("Steve", 100, 18);

        warlock.getDemon().attack(soldier);
    }



    @Test
    public void addObservableTest() throws Unit.UnitIsDeadException, Unit.IsSelfAttackException,
            Demon.MasterAttackedException, Spellcaster.ManaIsOverException {
        Soldier soldier = new Soldier("Steve", 100, 12);
        Rogue rogue = new Rogue("Robin", 100, 8);
        Berserk berserk = new Berserk("Viking", 120, 20);
        Vampire vamp = new Vampire("Count Dracula", 100, 12);
        Werewolf wolf = new Werewolf("Van Hellsing", 80, 10);
        Wizard wizard = new Wizard("Marylin", 90, 5, 120);
        Healer healer = new Healer("Manson", 60, 4, 100);
        Priest priest = new Priest("Francis", 90, 10, 120);
        Warlock warlock = new Warlock("Warlock", 90, 8, 120);
        Necromancer necro = new Necromancer("Freddy", 50, 10, 120);

        necro.castSpell(soldier);
        necro.castSpell(rogue);
        necro.castSpell(berserk);
        necro.castSpell(vamp);
        necro.castSpell(wolf);
        necro.castSpell(wizard);
        necro.castSpell(healer);
        necro.castSpell(priest);
        necro.castSpell(warlock);

        assertEquals(8, necro.getObservables().size());

        soldier.setCurrentHP(8);
        soldier.attack(necro);
        soldier.attack(necro);

        assertEquals(7, necro.getObservables().size());
        assertEquals(36, necro.getCurrentHP());
    }

    @Test
    public void addObserversTest() throws Unit.UnitIsDeadException, Unit.IsSelfAttackException,
            Demon.MasterAttackedException,
            Spellcaster.ManaIsOverException {
        Soldier soldier = new Soldier("Steve", 100, 12);
        Rogue rogue = new Rogue("Robin", 100, 8);
        Berserk berserk = new Berserk("Viking", 120, 20);
        Vampire vamp = new Vampire("Count Dracula", 100, 12);
        Werewolf wolf = new Werewolf("Van Hellsing", 80, 10);
        Wizard wizard = new Wizard("Marylin", 90, 5, 120);
        Healer healer = new Healer("Manson", 60, 4, 100);
        Priest priest = new Priest("Francis", 90, 10, 120);
        Warlock warlock = new Warlock("Warlock", 90, 8, 120);
        Necromancer necro = new Necromancer("Freddy", 50, 10, 120);
        Necromancer necro1 = new Necromancer("Shreck", 50, 10, 120);

        necro.castSpell(soldier);
        necro.castSpell(rogue);
        necro.castSpell(berserk);
        necro.castSpell(vamp);
        necro.castSpell(wolf);
        necro.castSpell(wizard);
        necro.castSpell(healer);
        necro.castSpell(priest);
        necro.castSpell(warlock);
        necro.castSpell(necro1);

        assertEquals(1, soldier.getObservers().size());
        assertEquals(1, rogue.getObservers().size());
        assertEquals(0, berserk.getObservers().size());
        assertEquals(1, vamp.getObservers().size());
        assertEquals(1, wolf.getObservers().size());
        assertEquals(1, wizard.getObservers().size());
        assertEquals(1, healer.getObservers().size());
        assertEquals(1, priest.getObservers().size());
        assertEquals(1, warlock.getObservers().size());
        assertEquals(1, necro1.getObservers().size());

        necro.setCurrentHP(5);
        necro.attack(berserk);

        assertEquals(0, soldier.getObservers().size());
        assertEquals(0, rogue.getObservers().size());
        assertEquals(0, berserk.getObservers().size());
        assertEquals(0, vamp.getObservers().size());
        assertEquals(0, wolf.getObservers().size());
        assertEquals(0, wizard.getObservers().size());
        assertEquals(0, healer.getObservers().size());
        assertEquals(0, priest.getObservers().size());
        assertEquals(0, warlock.getObservers().size());
        assertEquals(0, necro1.getObservers().size());
    }

    @Test
    public void addBerserkToObservableList() throws Unit.UnitIsDeadException, Unit.IsSelfAttackException,
            Demon.MasterAttackedException {
        Berserk berserk = new Berserk("Viking", 120, 20);
        Necromancer necro = new Necromancer("Freddy", 50, 10, 120);

        necro.attack(berserk);

        assertEquals(1, berserk.getObservers().size());
        assertEquals(1, necro.getObservables().size());
    }

    @Test
    public void addSlaveToObservableList() throws Warlock.DemonIsAlreadySummonedException,
            Warlock.DemonIsNotSummonedException, Unit.IsSelfAttackException, Spellcaster.ManaIsOverException, Unit
                    .UnitIsDeadException {
        Warlock warlock = new Warlock("Warlock", 90, 8, 120);
        Necromancer necro = new Necromancer("Freddy", 50, 10, 120);

        warlock.summonDemon();
        necro.castSpell(warlock.getDemon());

        assertEquals(1, necro.getObservables().size());

        warlock.removeDemon();

        assertEquals(0, necro.getObservables().size());
    }

    @Test
    public void spellbooksGetters() {
        Warlock warlock = new Warlock("Warlock", 90, 8, 120);

        assertEquals("Fireball", warlock.getCurrentSpell().getSpellsName());
        assertEquals(12, warlock.getCurrentSpell().getHitPoints());
        assertEquals(8, warlock.getCurrentSpell().getManaConsumption());

        warlock.setCurrentSpell("Heal");
        assertEquals("Heal", warlock.getCurrentSpell().getSpellsName());
        assertEquals(10, warlock.getCurrentSpell().getHitPoints());
        assertEquals(6, warlock.getCurrentSpell().getManaConsumption());
    }
}
