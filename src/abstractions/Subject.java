package abstractions;

import interfaces.Observer;
import models.Stat;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public abstract class Subject implements Serializable {
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

    // EFFECTS: Removes an observer
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    // EFFECTS: Notifies all observers via push notification
    public void notifyObservers(Stat stat) {
        for (Observer o : observers) {
            o.update(stat);
        }
    }
}
