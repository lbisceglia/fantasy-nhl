package model.Observers;

import model.Stat.GameStat;

import java.util.ArrayList;
import java.util.List;

public abstract class Subject {
    protected List<Observer> observers = new ArrayList<>();

    // EFFECTS: Returns the list of observers
    public List<Observer> getObservers() {
        return observers;
    }

    // EFFECTS: Adds an observer if not already registered
    public void addObserver(Observer observer) {
        if(!observers.contains(observer)) {
            observers.add(observer);
        }
    }

    // EFFECTS: Removes and observer
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    public void notifyObservers(GameStat gameStat) {
        for (Observer o : observers) {
            o.update(gameStat);
        }
    }
}
