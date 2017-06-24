#include <cxxtest/TestSuite.h>

#include "../units/unit.h"
#include "../units/soldier.h"
#include "../units/rogue.h"
#include "../units/berserk.h"
#include "../units/vampire.h"
#include "../units/werewolf.h"

#include "../state/state.h"
#include "../state/wolfstate.h"
#include "../state/humanstate.h"

#include "../combat/ability.h"

#include "../mystic/spellcaster.h"
#include "../mystic/wizard.h"
#include "../mystic/healer.h"
#include "../mystic/priest.h"
#include "../mystic/warlock.h"
#include "../units/demon.h"
    
#include "../spells/spell.h"
#include "../spells/fireball.h"
#include "../spells/heal.h"
#include "../spells/summon.h"

using namespace std;

class ArmyTest : public CxxTest::TestSuite {
    public:

        void testType() {
            Soldier* soldier = new Soldier("Steve", 100, 10);
            Rogue* rogue = new Rogue("Robin", 105, 14);
            Berserk* berserk = new Berserk("T-900", 120, 15);
            Vampire* vampire = new Vampire("Count Dracula", 100, 10);
            Werewolf* wolf = new Werewolf("Van Hellsing", 80, 15);
            Wizard* wizard = new Wizard("Marilyn", 80, 10, 120);

            TS_ASSERT_EQUALS(soldier->getUnitType(), 0);
            TS_ASSERT_EQUALS(rogue->getUnitType(), 1);
            TS_ASSERT_EQUALS(berserk->getUnitType(), 2);
            TS_ASSERT_EQUALS(vampire->getUnitType(), 3);
            TS_ASSERT_EQUALS(wolf->getUnitType(), 4);
            TS_ASSERT_EQUALS(wizard->getUnitType(), 5);

            delete soldier;
            delete rogue;
            delete berserk;
            delete vampire;
            delete wolf;
            delete wizard;
        }

        void testUnitIsDeadException() {
            Soldier* soldier = new Soldier("Steve", 100, 10);
            Vampire* vampire = new Vampire("Count Dracula", 100, 10);

            soldier->setCurrentHP(0);
            TS_ASSERT_THROWS(vampire->attack(soldier), UnitIsDeadException);

            delete vampire;
            delete soldier;
        }

        void testSelfAttackException() {
            Soldier* sold1 = new Soldier("Steve", 100, 10);
            
            TS_ASSERT_THROWS(sold1->attack(sold1), IsSelfAttackException);

            delete sold1;
        }

        void testGetters() {
            Soldier* soldier = new Soldier("Steve", 100, 10);

            TS_ASSERT_EQUALS(soldier->getName(), "Steve");
            TS_ASSERT_EQUALS(soldier->getHPLimit(), 100);
            TS_ASSERT_EQUALS(soldier->getCurrentHP(), 100);
            TS_ASSERT_EQUALS(soldier->getDamage(), 10);

            delete soldier;
        }

        void testSetters() {
            Soldier* soldier = new Soldier("Steve", 100, 10);

            soldier->setHPLimit(500);
            soldier->setCurrentHP(500);
            soldier->setDamage(500);

            TS_ASSERT_EQUALS(soldier->getHPLimit(), 500);
            TS_ASSERT_EQUALS(soldier->getCurrentHP(), 500);
            TS_ASSERT_EQUALS(soldier->getDamage(), 500);

            delete soldier;
        }

        void testHeals() {
            Soldier* soldier = new Soldier("Steve", 100, 10);

            soldier->setCurrentHP(50);
            soldier->heal(soldier->getHPLimit());

            TS_ASSERT_EQUALS(soldier->getCurrentHP(), 100);

            delete soldier;
        }

        void testRogue() {
            Soldier* soldier = new Soldier("Steve", 100, 10);
            Rogue* rogue = new Rogue("Robin", 105, 14);

            rogue->attack(soldier);

            TS_ASSERT_EQUALS(rogue->getCurrentHP(), 105);    

            delete soldier;
            delete rogue;
        }

        void testVampire() {
            Soldier* soldier = new Soldier("Steve", 100, 18);
            Vampire* vampire = new Vampire("Count Dracula", 100, 12);

            vampire->attack(soldier);
            TS_ASSERT_EQUALS(soldier->getCurrentHP(), 80);
            TS_ASSERT_EQUALS(vampire->getCurrentHP(), 91);
            
            soldier->attack(vampire);
            TS_ASSERT_EQUALS(vampire->getCurrentHP(), 80);
            TS_ASSERT_EQUALS(soldier->getCurrentHP(), 74);

            delete soldier;
            delete vampire;
        }

        void testWerewolf() {
            Soldier* soldier = new Soldier("Steve", 100, 15);
            Werewolf* wolf = new Werewolf("Van Hellsing", 80, 15);

            soldier->attack(wolf);
            TS_ASSERT_EQUALS(wolf->getCurrentHP(), 65);
            TS_ASSERT_EQUALS(wolf->getDamage(), 15);

            wolf->changeState();

            TS_ASSERT_EQUALS(wolf->getCurrentHP(), 130);
            TS_ASSERT_EQUALS(wolf->getDamage(), 30);

            soldier->attack(wolf);

            TS_ASSERT_EQUALS(wolf->getCurrentHP(), 115);

            delete soldier;
            delete wolf;
        }

        void testWizard() {
            Soldier* soldier = new Soldier("Steve", 100, 10);
            Berserk* berserk = new Berserk("T-900", 120, 15);
            Werewolf* wolf = new Werewolf("Van Hellsing", 80, 15);
            Wizard* wizard = new Wizard("Marilyn", 100, 12, 100);

            wizard->castSpell(soldier);
            wizard->castSpell(soldier);
            wizard->castSpell(soldier);
            wizard->castSpell(berserk);
            wizard->castSpell(wolf);

            TS_ASSERT_EQUALS(soldier->getCurrentHP(), 64);
            TS_ASSERT_EQUALS(berserk->getCurrentHP(), 120);
            TS_ASSERT_EQUALS(wolf->getCurrentHP(), 68);

            TS_ASSERT_EQUALS(wizard->getCurrentHP(), 100);
            TS_ASSERT_EQUALS(wizard->getCurrentMP(), 60);

            wolf->changeState();
            wizard->castSpell(wolf);

            TS_ASSERT_EQUALS(wolf->getCurrentHP(), 112);

            wizard->setCurrentSpell("Heal");

            berserk->setCurrentHP(100);

            wizard->castSpell(soldier);
            wizard->castSpell(soldier);
            wizard->castSpell(soldier);
            wizard->castSpell(berserk);
            wizard->castSpell(wolf);

            TS_ASSERT_EQUALS(soldier->getCurrentHP(), 79);
            TS_ASSERT_EQUALS(berserk->getCurrentHP(), 105);
            TS_ASSERT_EQUALS(wolf->getCurrentHP(), 117);

            TS_ASSERT_EQUALS(wizard->getCurrentMP(), 22);

            wizard->setCurrentMP(1);

            TS_ASSERT_THROWS(wizard->castSpell(soldier), ManaIsOverException);

            delete soldier;
            delete berserk;
            delete wolf;
            delete wizard;
        }

        void testHealer() {
            Soldier* soldier = new Soldier("Steve", 100, 10);
            Berserk* berserk = new Berserk("T-900", 120, 15);
            Healer* healer = new Healer("Manson", 100, 12, 100);

            soldier->setCurrentHP(30);
            berserk->setCurrentHP(60);

            healer->castSpell(soldier);
            healer->castSpell(berserk);

            TS_ASSERT_EQUALS(soldier->getCurrentHP(), 40);
            TS_ASSERT_EQUALS(berserk->getCurrentHP(), 70);
            TS_ASSERT_EQUALS(healer->getCurrentHP(), 100);
            TS_ASSERT_EQUALS(healer->getCurrentMP(), 88);

            healer->setCurrentSpell("Fireball");

            healer->castSpell(soldier);
            healer->castSpell(berserk);
            
            TS_ASSERT_EQUALS(soldier->getCurrentHP(), 34);
            TS_ASSERT_EQUALS(berserk->getCurrentHP(), 70);
            TS_ASSERT_EQUALS(healer->getCurrentMP(), 72);

            healer->setCurrentMP(1);

            TS_ASSERT_THROWS(healer->castSpell(soldier), ManaIsOverException);

            delete soldier;
            delete berserk;
            delete healer;
        }

        void testPriest() {
            Soldier* soldier = new Soldier("Steve", 100, 10);
            Berserk* berserk = new Berserk("T-900", 120, 15);
            Priest* priest = new Priest("Francis", 100, 12, 100);

            soldier->setCurrentHP(30);
            berserk->setCurrentHP(60);

            priest->castSpell(soldier);
            priest->castSpell(berserk);

            TS_ASSERT_EQUALS(soldier->getCurrentHP(), 45);
            TS_ASSERT_EQUALS(berserk->getCurrentHP(), 75);
            TS_ASSERT_EQUALS(priest->getCurrentHP(), 100);
            TS_ASSERT_EQUALS(priest->getCurrentMP(), 88);

            priest->setCurrentSpell("Fireball");

            priest->castSpell(soldier);
            priest->castSpell(berserk);
            
            TS_ASSERT_EQUALS(soldier->getCurrentHP(), 39);
            TS_ASSERT_EQUALS(berserk->getCurrentHP(), 75);
            TS_ASSERT_EQUALS(priest->getCurrentMP(), 72);

            priest->setCurrentMP(1);

            TS_ASSERT_THROWS(priest->castSpell(soldier), ManaIsOverException);

            delete soldier;
            delete berserk;
            delete priest;
        }

        void testWarlock() {
            Soldier* soldier = new Soldier("Steve", 100, 10);
            Warlock* warlock = new Warlock("Warlock", 70, 4, 100);

            warlock->setCurrentSpell("Heal");

            TS_ASSERT_THROWS(warlock->summonDemon(), IsNotSummonSpellsException)            

            warlock->setCurrentSpell("Summon");
            warlock->summonDemon();

            TS_ASSERT_THROWS(warlock->summonDemon(), DemonIsAlreadySummonedException);

            warlock->getDemon()->attack(soldier);

            TS_ASSERT_EQUALS(soldier->getCurrentHP(), 80);
            TS_ASSERT_EQUALS(warlock->getDemon()->getCurrentHP(), 115);

            TS_ASSERT_EQUALS(warlock->getCurrentMP(), 85);
            TS_ASSERT_THROWS(warlock->getDemon()->attack(warlock->getDemon()), IsSelfAttackException);
            TS_ASSERT_THROWS(warlock->getDemon()->attack(warlock), MasterAttackedException);

            delete soldier;
            delete warlock;
        }

        void testSpellbooks() {
            Wizard* wizard = new Wizard("Marilyn", 100, 12, 100);
            Warlock* warlock = new Warlock("Warlock", 70, 4, 100);

            TS_ASSERT_EQUALS(wizard->getCurrentSpell()->getSpellsName(), "Fireball");
            TS_ASSERT_EQUALS(wizard->getCurrentSpell()->getManaConsumption(), 8);
            TS_ASSERT_EQUALS(wizard->getCurrentSpell()->getHitPoints(), 12);
            TS_ASSERT_EQUALS(wizard->getCurrentSpell()->getSpellsType(), 0);

            wizard->setCurrentSpell("Heal");
            TS_ASSERT_EQUALS(wizard->getCurrentSpell()->getSpellsName(), "Heal");
            TS_ASSERT_EQUALS(wizard->getCurrentSpell()->getManaConsumption(), 6);
            TS_ASSERT_EQUALS(wizard->getCurrentSpell()->getHitPoints(), 10);
            TS_ASSERT_EQUALS(wizard->getCurrentSpell()->getSpellsType(), 1);

            TS_ASSERT_EQUALS(warlock->getCurrentSpell()->getSpellsName(), "Summon");
            TS_ASSERT_EQUALS(warlock->getCurrentSpell()->getManaConsumption(), 15);
            TS_ASSERT_EQUALS(warlock->getCurrentSpell()->getHitPoints(), 0);
            TS_ASSERT_EQUALS(warlock->getCurrentSpell()->getSpellsType(), 2);

            delete wizard;
            delete warlock;
        }
};
