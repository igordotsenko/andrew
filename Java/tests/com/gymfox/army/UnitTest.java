package com.gymfox.Army;

import com.gymfox.Army.Spellcasters.*;
import com.gymfox.Army.Units.*;
import junit.framework.Assert;
import org.junit.Test;

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

        Assert.assertEquals("Steve", soldier.getName());
        Assert.assertEquals(100, soldier.getHealthPointLimit());
        Assert.assertEquals(100, soldier.getCurrentHP());
        Assert.assertEquals(12, soldier.getDamage());
        Assert.assertEquals(false, soldier.getIsDead());

        Assert.assertEquals("Robin", rogue.getName());
        Assert.assertEquals(100, rogue.getHealthPointLimit());
        Assert.assertEquals(100, rogue.getCurrentHP());
        Assert.assertEquals(8, rogue.getDamage());
        Assert.assertEquals(false, rogue.getIsDead());

        Assert.assertEquals("Viking", berserk.getName());
        Assert.assertEquals(120, berserk.getHealthPointLimit());
        Assert.assertEquals(120, berserk.getCurrentHP());
        Assert.assertEquals(20, berserk.getDamage());
        Assert.assertEquals(false, berserk.getIsDead());

        Assert.assertEquals("Count Dracula", vamp.getName());
        Assert.assertEquals(100, vamp.getHealthPointLimit());
        Assert.assertEquals(100, vamp.getCurrentHP());
        Assert.assertEquals(12, vamp.getDamage());
        Assert.assertEquals(true, vamp.getIsDead());

        Assert.assertEquals("Van Hellsing", wolf.getName());
        Assert.assertEquals(80, wolf.getHealthPointLimit());
        Assert.assertEquals(80, wolf.getCurrentHP());
        Assert.assertEquals(10, wolf.getDamage());
        Assert.assertEquals(false, wolf.getIsDead());

        wolf.changeState();

        Assert.assertEquals("Van Hellsing", wolf.getName());
        Assert.assertEquals(160, wolf.getHealthPointLimit());
        Assert.assertEquals(160, wolf.getCurrentHP());
        Assert.assertEquals(20, wolf.getDamage());
        Assert.assertEquals(false, wolf.getIsDead());

        Assert.assertEquals("Marylin", wizard.getName());
        Assert.assertEquals(90, wizard.getHealthPointLimit());
        Assert.assertEquals(90, wizard.getCurrentHP());
        Assert.assertEquals(5, wizard.getDamage());
        Assert.assertEquals(120, wizard.getManaPointLimits());
        Assert.assertEquals(120, wizard.getCurrentMP());
        Assert.assertEquals(false, wizard.getIsDead());

        Assert.assertEquals("Manson", healer.getName());
        Assert.assertEquals(60, healer.getHealthPointLimit());
        Assert.assertEquals(60, healer.getCurrentHP());
        Assert.assertEquals(4, healer.getDamage());
        Assert.assertEquals(100, healer.getManaPointLimits());
        Assert.assertEquals(100, healer.getCurrentMP());
        Assert.assertEquals(false, healer.getIsDead());

        Assert.assertEquals("Francis", priest.getName());
        Assert.assertEquals(90, priest.getHealthPointLimit());
        Assert.assertEquals(90, priest.getCurrentHP());
        Assert.assertEquals(10, priest.getDamage());
        Assert.assertEquals(120, priest.getManaPointLimits());
        Assert.assertEquals(120, priest.getCurrentMP());
        Assert.assertEquals(false, priest.getIsDead());

        Assert.assertEquals("Warlock", warlock.getName());
        Assert.assertEquals(90, warlock.getHealthPointLimit());
        Assert.assertEquals(90, warlock.getCurrentHP());
        Assert.assertEquals(8, warlock.getDamage());
        Assert.assertEquals(120, warlock.getManaPointLimits());
        Assert.assertEquals(120, warlock.getCurrentMP());
        Assert.assertEquals(false, warlock.getIsDead());

        Assert.assertEquals("Freddy", necro.getName());
        Assert.assertEquals(50, necro.getHealthPointLimit());
        Assert.assertEquals(50, necro.getCurrentHP());
        Assert.assertEquals(10, necro.getDamage());
        Assert.assertEquals(100, necro.getManaPointLimits());
        Assert.assertEquals(100, necro.getCurrentMP());
        Assert.assertEquals(true, necro.getIsDead());
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

        priest.castSpell(vamp);

        Assert.assertEquals(100, vamp.getCurrentHP());
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

        Assert.assertEquals(88, soldier2.getCurrentHP());
        Assert.assertEquals(93, soldier1.getCurrentHP());

        soldier2.attack(soldier1);

        Assert.assertEquals(79, soldier1.getCurrentHP());
        Assert.assertEquals(82, soldier2.getCurrentHP());
    }

    @Test
    public void rogueTest() throws Unit.UnitIsDeadException, Unit.IsSelfAttackException, Demon.MasterAttackedException {
        Rogue rogue = new Rogue("Robin", 100, 8);
        Soldier soldier = new Soldier("Steve", 100, 12);

        rogue.attack(soldier);

        Assert.assertEquals(100, rogue.getCurrentHP());
        Assert.assertEquals(92, soldier.getCurrentHP());
    }

    @Test
    public void berserkAndWizardTest() throws Unit.IsSelfAttackException, Spellcaster.ManaIsOverException, Unit
            .UnitIsDeadException {
        Berserk berserk = new Berserk("Viking", 120, 20);
        Wizard wizard = new Wizard("Marylin", 90,5,120);

        wizard.castSpell(berserk);
        Assert.assertEquals(120, berserk.getCurrentHP());
        Assert.assertEquals(112, wizard.getCurrentMP());
    }

    @Test
    public void wizardAttackTest() throws Unit.IsSelfAttackException, Spellcaster.ManaIsOverException, Unit
            .UnitIsDeadException {
        Wizard wizard = new Wizard("Marylin", 90,5,120);
        Soldier soldier = new Soldier("Steve", 100, 18);

        wizard.castSpell(soldier);

        Assert.assertEquals(88, soldier.getCurrentHP());
    }

    @Test
    public void wizardHealTest() throws Unit.IsSelfAttackException, Spellcaster.ManaIsOverException, Unit
            .UnitIsDeadException {
        Wizard wizard = new Wizard("Marylin", 90,5,120);
        Soldier soldier = new Soldier("Steve", 100, 18);

        soldier.setCurrentHP(50);
        wizard.setCurrentSpell("Heal");

        wizard.castSpell(soldier);

        Assert.assertEquals(55, soldier.getCurrentHP());
        Assert.assertEquals(114, wizard.getCurrentMP());
    }

    @Test
    public void vampireTest() throws Unit.UnitIsDeadException, Unit.IsSelfAttackException,
            Demon.MasterAttackedException {
        Soldier soldier = new Soldier("Steve", 100, 18);
        Vampire vamp = new Vampire("Count Dracula", 100, 12);

        vamp.attack(soldier);

        Assert.assertEquals(80, soldier.getCurrentHP());
        Assert.assertEquals(91, vamp.getCurrentHP());

        soldier.attack(vamp);
        Assert.assertEquals(80, vamp.getCurrentHP());
        Assert.assertEquals(67, soldier.getCurrentHP());
    }

    @Test
    public void werewolfTest() throws Unit.IsSelfAttackException, Spellcaster.ManaIsOverException, Unit
            .UnitIsDeadException {
        Werewolf wolf = new Werewolf("Van Hellsing", 80, 10);
        Wizard wizard = new Wizard("Marylin", 90, 5, 120);

        wizard.castSpell(wolf);
        Assert.assertEquals(68, wolf.getCurrentHP());

        wolf.changeState();

        wizard.castSpell(wolf);
        Assert.assertEquals(112, wolf.getCurrentHP());
    }

    @Test
    public void healerTest() throws Unit.IsSelfAttackException, Spellcaster.ManaIsOverException, Unit
            .UnitIsDeadException {
        Healer healer = new Healer("Manson", 60, 4, 100);
        Soldier soldier = new Soldier("Steve", 100, 18);

        healer.setCurrentSpell("Fireball");

        healer.castSpell(soldier);

        Assert.assertEquals(94, soldier.getCurrentHP());
        Assert.assertEquals(92, healer.getCurrentMP());
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

        Assert.assertEquals(94, soldier.getCurrentHP());
        Assert.assertEquals(76, vamp.getCurrentHP());
    }

    @Test
    public void healTest() throws Unit.UnitIsDeadException {
        Soldier soldier = new Soldier("Steve", 100, 18);

        soldier.setCurrentHP(88);

        soldier.heal(soldier.getDamage());

        Assert.assertEquals(100, soldier.getCurrentHP());
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

        Assert.assertEquals(88, soldier.getCurrentHP());
        Assert.assertEquals(91, warlock.getDemon().getCurrentHP());
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

        Assert.assertEquals(8, necro.getObservables().size());

        soldier.setCurrentHP(8);
        soldier.attack(necro);
        soldier.attack(necro);

        Assert.assertEquals(7, necro.getObservables().size());
        Assert.assertEquals(36, necro.getCurrentHP());
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

        Assert.assertEquals(1, soldier.getObservers().size());
        Assert.assertEquals(1, rogue.getObservers().size());
        Assert.assertEquals(0, berserk.getObservers().size());
        Assert.assertEquals(1, vamp.getObservers().size());
        Assert.assertEquals(1, wolf.getObservers().size());
        Assert.assertEquals(1, wizard.getObservers().size());
        Assert.assertEquals(1, healer.getObservers().size());
        Assert.assertEquals(1, priest.getObservers().size());
        Assert.assertEquals(1, warlock.getObservers().size());
        Assert.assertEquals(1, necro1.getObservers().size());

        necro.setCurrentHP(5);
        necro.attack(berserk);

        Assert.assertEquals(0, soldier.getObservers().size());
        Assert.assertEquals(0, rogue.getObservers().size());
        Assert.assertEquals(0, berserk.getObservers().size());
        Assert.assertEquals(0, vamp.getObservers().size());
        Assert.assertEquals(0, wolf.getObservers().size());
        Assert.assertEquals(0, wizard.getObservers().size());
        Assert.assertEquals(0, healer.getObservers().size());
        Assert.assertEquals(0, priest.getObservers().size());
        Assert.assertEquals(0, warlock.getObservers().size());
        Assert.assertEquals(0, necro1.getObservers().size());
    }

    @Test
    public void addBerserkToObservableList() throws Unit.UnitIsDeadException, Unit.IsSelfAttackException,
            Demon.MasterAttackedException {
        Berserk berserk = new Berserk("Viking", 120, 20);
        Necromancer necro = new Necromancer("Freddy", 50, 10, 120);

        necro.attack(berserk);

        Assert.assertEquals(1, berserk.getObservers().size());
        Assert.assertEquals(1, necro.getObservables().size());
    }

    @Test
    public void addSlaveToObservableList() throws Warlock.DemonIsAlreadySummonedException,
            Warlock.DemonIsNotSummonedException, Unit.IsSelfAttackException, Spellcaster.ManaIsOverException, Unit
                    .UnitIsDeadException {
        Warlock warlock = new Warlock("Warlock", 90, 8, 120);
        Necromancer necro = new Necromancer("Freddy", 50, 10, 120);

        warlock.summonDemon();
        necro.castSpell(warlock.getDemon());

        Assert.assertEquals(1, necro.getObservables().size());

        warlock.removeDemon();

        Assert.assertEquals(0, necro.getObservables().size());
    }

    @Test
    public void spellbooksGetters() {
        Warlock warlock = new Warlock("Warlock", 90, 8, 120);

        Assert.assertEquals("Fireball", warlock.getCurrentSpell().getSpellsName());
        Assert.assertEquals(12, warlock.getCurrentSpell().getHitPoints());
        Assert.assertEquals(8, warlock.getCurrentSpell().getManaConsumption());

        warlock.setCurrentSpell("Heal");
        Assert.assertEquals("Heal", warlock.getCurrentSpell().getSpellsName());
        Assert.assertEquals(10, warlock.getCurrentSpell().getHitPoints());
        Assert.assertEquals(6, warlock.getCurrentSpell().getManaConsumption());
    }
}
