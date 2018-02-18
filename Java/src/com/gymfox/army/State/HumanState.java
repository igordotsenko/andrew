package com.gymfox.Army.State;

import com.gymfox.Army.Units.Unit;

public class HumanState extends State {
    public HumanState(Unit currentUnitState) {
        super(currentUnitState);
        setStateName("Human");
    }
}
