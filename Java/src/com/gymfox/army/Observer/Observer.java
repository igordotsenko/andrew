package com.gymfox.army.Observer;

public interface Observer {
    public void addObservable(Observable observable);
    public void removeObservable(Observable observable);
    public void notifyObservable();
}
