package com.gymfox.army.ability;

import com.gymfox.army.state.HumanState;
import com.gymfox.army.state.State;
import com.gymfox.army.state.WolfState;
import com.gymfox.army.units.Unit;

public class WerewolfAbility extends Ability {
    private State currentState;
    private State nextState;

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
        State currentState = getCurrentState();
        State nextState = getNextState();
        State temp = currentState;

        setCurrentState(nextState);
        setNextState(temp);
        currentState = nextState;

        int newCurrentHP = (int)(getHealthMultiplier() * currentState.getHealthPointLimit());

        getCurrentUnit().setHealthPointLimit(currentState.getHealthPointLimit());
        getCurrentUnit().setCurrentHP(newCurrentHP);
        getCurrentUnit().setDamage(currentState.getDamage());
    }

    public double getHealthMultiplier() {
        return (double)getCurrentUnit().getCurrentHP() / (double)getCurrentUnit().getHealthPointLimit();
    }

    public void setCurrentState(State currentState) {
        this.currentState = currentState;
    }

    public void setNextState(State nextState) {
        this.nextState = nextState;
    }

    public State getCurrentState() {
        return currentState;
    }

    public State getNextState() {
        return nextState;
    }
}
