package com.gymfox.Army.Ability;

import com.gymfox.Army.State.HumanState;
import com.gymfox.Army.State.State;
import com.gymfox.Army.State.WolfState;
import com.gymfox.Army.Units.Unit;

public class WerewolfAbility extends Ability {
    public WerewolfAbility(Unit currentUnit) {
        super(currentUnit);
        this.currentState = new HumanState(currentUnit);
        this.nextState = new WolfState(currentUnit);
    }

    @Override
    public void takeMagicDamage(int damage) throws Unit.UnitIsDeadException {
        getCurrentState().takeMagicDamage(damage);
    }

    @Override
    public void changeState()  {
        State currentState = getCurrentUnit().getAbility().getCurrentState();
        State nextState = getCurrentUnit().getAbility().getNextState();
        State temp = currentState;

        getCurrentUnit().getAbility().setCurrentState(nextState);
        getCurrentUnit().getAbility().setNextState(temp);
        currentState = nextState;

        int newCurrentHP = (int)(getHealthMultiplier() * (double)currentState.getHealthPointLimit());

        getCurrentUnit().setHealthPointLimit(currentState.getHealthPointLimit());
        getCurrentUnit().setCurrentHP(newCurrentHP);
        getCurrentUnit().setDamage(currentState.getDamage());
    }

    public double getHealthMultiplier() {
        return (double)getCurrentUnit().getCurrentHP() / (double)getCurrentUnit().getHealthPointLimit();
    }
}
