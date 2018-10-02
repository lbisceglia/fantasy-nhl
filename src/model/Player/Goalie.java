package model.Player;

import java.io.Serializable;

public class Goalie extends Player implements Serializable{
    private double savePercentage;
    private double goalsAgainstAverage;

    public Goalie(String name, int weekFantasyPoints, int totalFantasyPoints, double savePercentage, double goalsAgainstAverage) {
        super(name, Position.G, weekFantasyPoints, totalFantasyPoints);
        if (savePercentage >= 0 && savePercentage <= 1) {
            this.savePercentage = savePercentage;
        } else {
            this.savePercentage = 1;
        }
        if (goalsAgainstAverage >= 0) {
            this.goalsAgainstAverage = goalsAgainstAverage;
        } else {
            this.goalsAgainstAverage = 0;
        }
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
    public void setSavePercentage(double svP) {
        if (svP >= 0 && svP <= 1) {
            this.savePercentage = svP;
        }
    }

    // MODIFIES: this
    // EFFECTS: Sets goals against average for the active season if the number is non-negative
    public void setGoalsAgainstAverage(double gAA) {
        if (gAA >= 0) {
            this.goalsAgainstAverage = gAA;
        }
    }
}