package com.gymfox.army.state;

import com.gymfox.army.units.Unit;

public class HumanState extends State {
    public HumanState(Unit currentUnitState) {
        super(currentUnitState);
        setStateName("Human");
    }
}
