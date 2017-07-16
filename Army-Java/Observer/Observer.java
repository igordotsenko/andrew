package com.gymfox.Army.Observer;

import com.gymfox.Army.Units.Unit;

public interface Observer {
    public void addObservable(Unit observable);
    public void removeObservable(Unit observable);
}
