#ifndef SPELL_H
#define SPELL_H 

#include "../units/unit.h"

using namespace std;

class Spellcaster;
class Ability;

enum SpellsType {
    battleSpell,
    healSpell,
    summonSpell
};
    
class Spell {
    private:
        string spellsName;
        int manaConsumption;
        int hitPoints;
        int spellsType;

    protected:
        virtual void setSpellsName(const string& newSpellsName);
        virtual void setManaConsumption(int newManaConsumption);
        virtual void setHitPoints(int newHitPoints);
        virtual void setSpellsType(int newSpellsType);
        
    public:
        Spell();
        virtual ~Spell();
    
        virtual const string& getSpellsName() const;
        virtual int getManaConsumption() const;
        virtual int getHitPoints() const;
        virtual int getSpellsType() const;
};


#endif //SPELL_H