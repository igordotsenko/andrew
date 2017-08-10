package com.gymfox.Army.Observer;

import com.gymfox.Army.Units.Unit;

public interface Observable {
    public void addObserver(Unit observer);
    public void removeObserver(Unit observer);
    public void notifyObservers() throws Unit.UnitIsDeadException;
}
