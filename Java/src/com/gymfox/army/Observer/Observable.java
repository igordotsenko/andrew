package com.gymfox.Army.Observer;

import com.gymfox.Army.Units.Unit;

public interface Observable {
    public void addObserver(Observer observer);
    public void removeObserver(Observer observer);
    public void notifyObservers() throws Unit.UnitIsDeadException;
}
