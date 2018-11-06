package model.Player;

import model.exceptions.InvalidStatException;

import java.io.Serializable;

import static model.utilities.StatManager.isNonNegativeStat;
import static model.utilities.StatManager.isValidPercentageStat;

public class Goalie extends Player implements Serializable{
    private double savePercentage;
    private double goalsAgainstAverage;

    // EFFECTS: Construct a Goalie with the given name, savePercentage, and goalsAgainstAverage
    // TODO: Show TA why position argument has been added (even though useless)
    public Goalie(String name, double savePercentage, double goalsAgainstAverage) throws InvalidStatException {
        super(name);
        setSavePercentage(savePercentage);
        setGoalsAgainstAverage(goalsAgainstAverage);
        //TODO: Add the following statistical categories: shutouts, wins
    }

//    // EFFECTS: Construct a Goalie with the given name, savePercentage, and goalsAgainstAverage
//    public Goalie(String name, double savePercentage, double goalsAgainstAverage) throws InvalidStatException {
//        super(name, Position.G);
//        if (savePercentage >= 0 && savePercentage <= 1) {
//            this.savePercentage = savePercentage;
//        } else {
//            throw new InvalidStatException("Save Percentage must be between 0 and 1.");
//        }
//        if (goalsAgainstAverage >= 0) {
//            this.goalsAgainstAverage = goalsAgainstAverage;
//        } else {
//            throw new InvalidStatException("Goals Against Average cannot be negative.");
//        }
//    }


    // EFFECTS: Constructs a goalie with the given name, position, and base stats
    public Goalie(String name) {
        super(name);
        savePercentage = 1;
        goalsAgainstAverage = 0;
        //TODO: Add the following statistical categories: shutouts, wins
    }

//    // EFFECTS: Constructs a goalie with the given name, position, and base stats
//    public Goalie(String name) {
//        super(name, Position.G);
//        savePercentage = 1;
//        goalsAgainstAverage = 0;
//        //TODO: Add the following statistical categories: shutouts, wins
//    }

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
    // EFFECTS: Sets save percentage in the active season if the number is a valid percentage
    public void setSavePercentage(double svP) throws InvalidStatException {
        String statType = "Save Percentage";
        if (isValidPercentageStat(statType, svP)) {
            this.savePercentage = svP;
        }
    }

    // MODIFIES: this
    // EFFECTS: Sets goals against average for the active season if the number is non-negative
    public void setGoalsAgainstAverage(double gAA) throws InvalidStatException {
        String statType = "Goals Against Average";
        if (isNonNegativeStat(statType, gAA)) {
            this.goalsAgainstAverage = gAA;
        }
    }
}