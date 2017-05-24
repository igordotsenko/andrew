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
    
#include "../magicskill/spell.h"


using namespace std;

class ArmyTest : public CxxTest::TestSuite {
    public:

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

        void testException() {
            Soldier* soldier = new Soldier("Steve", 100, 10);
            Vampire* vampire = new Vampire("Count Dracula", 100, 10);

            soldier->setCurrentHP(0);

            TS_ASSERT_THROWS(vampire->attack(soldier), UnitIsDeadException);

            delete vampire;
            delete soldier;
        }

        void testSelfAttack() {
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

        void testBerserk() {
            Wizard* wizard = new Wizard("Marilyn", 100, 12, 100);
            Berserk* berserk = new Berserk("T-900", 120, 15);

            TS_ASSERT_EQUALS(berserk->getCurrentHP(), 120);

            delete wizard;
            delete berserk;
        }

        void testVampire() {
            Soldier* soldier = new Soldier("Steve", 100, 18);
            Vampire* vampire = new Vampire("Count Dracula", 100, 12);

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

        //     delete soldier;
        //     delete berserk;
        //     delete wizard;
        // }
};