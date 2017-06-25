#ifndef VAMPIREABILITY_H
#define VAMPIREABILITY_H

#include "ability.h"
#include "../units/unit.h"

class VampireAbility: public Ability {
	private:
		void vampirism(Unit* victim);

	public:
		VampireAbility(Unit* currentUnit);
		virtual ~VampireAbility();

		virtual void attack(Unit* victim);};

#endif //VAMPIREABILITY_H