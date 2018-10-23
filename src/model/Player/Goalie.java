package model.Player;

import model.exceptions.InvalidStatException;

import java.io.Serializable;

public class Goalie extends Player implements Serializable{
    private double savePercentage;
    private double goalsAgainstAverage;

    // EFFECTS: Construct a Goalie with the given name, savePercentage, and goalsAgainstAverage
    public Goalie(String name, double savePercentage, double goalsAgainstAverage) throws InvalidStatException {
        super(name, Position.G);
        if (savePercentage >= 0 && savePercentage <= 1) {
            this.savePercentage = savePercentage;
        } else {
            throw new InvalidStatException("Save Percentage must be between 0 and 1.");
        }
        if (goalsAgainstAverage >= 0) {
            this.goalsAgainstAverage = goalsAgainstAverage;
        } else {
            throw new InvalidStatException("Goals Against Average cannot be negative.");
        }
        //TODO: Add the following statistical categories: shutouts, wins
    }


    // EFFECTS: Constructs a goalie with the given name, position, and base stats
    public Goalie(String name) {
        super(name, Position.G);
        savePercentage = 1;
        goalsAgainstAverage = 0;
        //TODO: Add the following statistical categories: shutouts, wins
    }

    //getters

    // EFFECTS: Return the Goalie's save percentage in the active season
    public double getSavePercentage() {
        return this.savePercentage;
    }

    // EFFECTS: Return the Goalie's goals against average in the active season
    public double getGoalsAgainstAverage() {
        return this.goalsAgainstAverage;
    }

    // setters

    // MODIFIES: this
    // EFFECTS: Sets save percentage in the active season if the number is between 0 and 1, inclusive
    public void setSavePercentage(double svP) throws InvalidStatException {
        if (svP >= 0 && svP <= 1) {
            this.savePercentage = svP;
        } else {
            throw new InvalidStatException("Save Percentage must be between 0 and 1.");
        }
    }

    // MODIFIES: this
    // EFFECTS: Sets goals against average for the active season if the number is non-negative
    public void setGoalsAgainstAverage(double gAA) throws InvalidStatException {
        if (gAA >= 0) {
            this.goalsAgainstAverage = gAA;
        } else {
            throw new InvalidStatException("Goals Against Average cannot be negative.");
        }
    }
}