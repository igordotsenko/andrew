package com.gymfox.army.observer;

import com.gymfox.army.units.Unit;

public interface Observable {
    public void addObserver(Observer observer);
    public void removeObserver(Observer observer);
    public void notifyObservers() throws Unit.UnitIsDeadException;
}
