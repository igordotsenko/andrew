#ifndef SPELLBOOKS_H
#define SPELLBOOKS_H 

#include "../mystic/spellcaster.h"

using namespace std;

class Spellcaster;
class Ability;
    
class Spellbooks {
    private:
        string spellsName;
        int manaConsumption;
        int hitPoints;
        
    public:
        Spellbooks(const string& name, int manaConsumption, int hitPoints);
        virtual ~Spellbooks();
    
        virtual const string& getSpellsName() const;
        virtual int getManaConsumption() const;
        virtual int getHitPoints() const;
};


#endif //SPELLBOOKS_H