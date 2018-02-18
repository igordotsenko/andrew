package com.gymfox.army.State;

import com.gymfox.army.Units.Unit;

public class HumanState extends State {
    public HumanState(Unit currentUnitState) {
        super(currentUnitState);
        setStateName("Human");
    }
}
