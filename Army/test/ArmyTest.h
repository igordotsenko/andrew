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

#include "../mystic/spellcaster.h"
#include "../mystic/wizard.h"
    
#include "../magicskill/spell.h"


using namespace std;

class ArmyTest : public CxxTest::TestSuite {
    public:

        void testSoldier() {
            Soldier* soldier = new Soldier("Steve", 100, 10);

            soldier->heal(150);

            TS_ASSERT_EQUALS(soldier->getCurrentHP(), 100);

            delete soldier;
        }

        void testRogue() {
            Soldier* soldier = new Soldier("Steve", 100, 10);
            Rogue* rogue = new Rogue("Robin", 100, 14);

            rogue->attack(soldier);


            TS_ASSERT_EQUALS(rogue->getCurrentHP(), 100);    

            delete soldier;
            delete rogue;
        }

        void testVampire() {
            Soldier* soldier = new Soldier("Steve", 100, 15);
            Vampire* vampire = new Vampire("Count Dracula", 100, 15);

            for ( int i = 0; i < 2; i++ ) {
                vampire->attack(soldier);
            }

            TS_ASSERT_EQUALS(vampire->getCurrentHP(), 93);

            delete soldier;
            delete vampire;
        }

        void testWerewolf() {
            Soldier* soldier = new Soldier("Steve", 100, 10);
            Werewolf* wolf = new Werewolf("Van Hellsing", 80, 15);

            wolf->changeState();

            TS_ASSERT_EQUALS(wolf->getCurrentHP(), 160);
            TS_ASSERT_EQUALS(wolf->getDamage(), 30);


            for ( int i = 0; i < 2; i++ ) {
                soldier->attack(wolf);
            }

            TS_ASSERT_EQUALS(wolf->getCurrentHP(), 140);

            delete soldier;
            delete wolf;
        }
};
