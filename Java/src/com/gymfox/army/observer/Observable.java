package com.gymfox.army.Observer;

import com.gymfox.army.Units.Unit;

public interface Observable {
    public void addObserver(Observer observer);
    public void removeObserver(Observer observer);
    public void notifyObservers() throws Unit.UnitIsDeadException;
}
