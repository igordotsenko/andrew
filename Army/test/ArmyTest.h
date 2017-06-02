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
    
#include "../spellbooks/spellbooks.h"

using namespace std;

class ArmyTest : public CxxTest::TestSuite {
    public:

        void testAlly() {
            Soldier* soldier1 = new Soldier("Steve", 100, 10);
            Soldier* soldier2 = new Soldier("Baki", 100, 10);

            soldier2->setFriendly();

            TS_ASSERT_EQUALS(soldier1->isAlly(), false);
            TS_ASSERT_EQUALS(soldier2->isAlly(), true);

            delete soldier1;
            delete soldier2;
        }

        void testIsFriendlyException() {
            Soldier* soldier1 = new Soldier("Steve", 100, 10);
            Soldier* soldier2 = new Soldier("Baki", 100, 10);
            Vampire* vampire = new Vampire("Count Dracula", 100, 10);
            Werewolf* wolf = new Werewolf("Van Hellsing", 80, 15);

            vampire->setFriendly();
            wolf->setFriendly();

            TS_ASSERT_THROWS(soldier1->attack(soldier2), IsFriendlyAttackException);
            TS_ASSERT_THROWS(wolf->attack(vampire), IsFriendlyAttackException);

            delete soldier1;
            delete soldier2;
            delete vampire;
            delete wolf;
        }

        void testType() {
            Soldier* soldier = new Soldier("Steve", 100, 10);
            Rogue* rogue = new Rogue("Robin", 105, 14);
            Berserk* berserk = new Berserk("T-900", 120, 15);
            Vampire* vampire = new Vampire("Count Dracula", 100, 10);
            Werewolf* wolf = new Werewolf("Van Hellsing", 80, 15);

            TS_ASSERT_EQUALS(soldier->getUnitType(), 0);
            TS_ASSERT_EQUALS(rogue->getUnitType(), 1);
            TS_ASSERT_EQUALS(berserk->getUnitType(), 2);
            TS_ASSERT_EQUALS(vampire->getUnitType(), 3);
            TS_ASSERT_EQUALS(wolf->getUnitType(), 4);

            delete soldier;
            delete rogue;
            delete berserk;
            delete vampire;
            delete wolf;
        }

        void testUnitIsDeadException() {
            Soldier* soldier = new Soldier("Steve", 100, 10);
            Vampire* vampire = new Vampire("Count Dracula", 100, 10);

            soldier->setCurrentHP(0);
            soldier->setFriendly();

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

            rogue->setFriendly();

            rogue->attack(soldier);

            TS_ASSERT_EQUALS(rogue->getCurrentHP(), 105);    

            delete soldier;
            delete rogue;
        }

        void testVampire() {
            Soldier* soldier = new Soldier("Steve", 100, 18);
            Vampire* vampire = new Vampire("Count Dracula", 100, 12);

            vampire->setFriendly();

            vampire->attack(soldier);
            TS_ASSERT_EQUALS(soldier->getCurrentHP(), 88);
            TS_ASSERT_EQUALS(vampire->getCurrentHP(), 99);
            
            soldier->attack(vampire);
            TS_ASSERT_EQUALS(soldier->getCurrentHP(), 82);
            TS_ASSERT_EQUALS(vampire->getCurrentHP(), 89);

            delete soldier;
            delete vampire;
        }

        void testWerewolf() {
            Soldier* soldier = new Soldier("Steve", 100, 15);
            Werewolf* wolf = new Werewolf("Van Hellsing", 80, 15);

            wolf->setFriendly();

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

        // void testWizard() {
        //     Soldier* soldier = new Soldier("Steve", 100, 10);
        //     Berserk* berserk = new Berserk("T-900", 120, 15);
        //     Wizard* wizard = new Wizard("Marilyn", 100, 12, 100);

            
            // wizard->attack(soldier);
            // wizard->attack(berserk);

            // TS_ASSERT_EQUALS(soldier->getCurrentHP(), 85);
            // TS_ASSERT_EQUALS(berserk->getCurrentHP(), 120);
            // TS_ASSERT_EQUALS(wizard->getCurrentHP(), 100);

            /*
            Exception for the case, when MP < spells consumtion
            TS_ASSERT_THROWS();
            */


        //     delete soldier;
        //     delete berserk;
        //     delete wizard;
        // }
};