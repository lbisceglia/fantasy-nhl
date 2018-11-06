package model.Player;

import model.exceptions.InvalidStatException;

import java.io.Serializable;

import static model.utilities.StatManager.isNaturalNumberStat;

public class Skater extends Player implements Serializable {
    private int totalGoals;
    private int totalAssists;
    private Position position;

    // EFFECTS: Constructs a skater with the given name, position, totalGoals, and totalAssists
    public Skater(String name, Position position, int totalGoals, int totalAssists) throws InvalidStatException {
        super(name);
        this.position = position;
        setTotalGoals(totalGoals);
        setTotalAssists(totalAssists);
        //TODO: add the following statistical categories: shots on goal, powerplay points, hits, blocked shots
    }

    // EFFECTS: Constructs a skater with the given name, position, and base stats
    public Skater(String name, Position position) {
        super(name);
        this.position = position;
        totalGoals = 0;
        totalAssists = 0;
        //TODO: add the following statistical categories: shots on goal, powerplay points, hits, blocked shots
    }

//    // EFFECTS: Constructs a skater with the given name, position, totalGoals, and totalAssists
//    //          Does nothing if the skater tries to have position G (i.e. goalie)
//    public Skater(String name, Position position, int totalGoals, int totalAssists) throws InvalidPositionException, InvalidStatException {
//        super(name, position);
//        if (totalGoals >= 0) {
//            this.totalGoals = totalGoals;
//        } else {
//            throw new InvalidStatException("Goals cannot be negative.");
//        }
//        if (totalAssists >= 0) {
//            this.totalAssists = totalAssists;
//        } else {
//            throw new InvalidStatException("Assists cannot be negative.");
//        }
//    }

//    // EFFECTS: Constructs a skater with the given name, position, and base stats
//    public Skater(String name, Position position) {
//        super(name, position);
//        totalGoals = 0;
//        totalAssists = 0;
//    }


        // EFFECTS: Return the skater's hockey position
    public Position getPlayerPosition() {
        return position;
    }

    // EFFECTS: Returns the skater's total goals scored in the active season
    public int getTotalGoals() {
        return this.totalGoals;
    }

    // EFFECTS: Returns the skater's total assists scored in the active season
    public int getTotalAssists() {
        return this.totalAssists;
    }

    // MODIFIES: this
    // EFFECTS: Sets the skater's total goals scored in the active season if the number is non-negative
    public void setTotalGoals(int goals) throws InvalidStatException {
        String statType = "Goals";
        if(isNaturalNumberStat(statType, goals)) {
            totalGoals = goals;
        }
    }

    // MODIFIES: this
    // EFFECTS: Sets the skater's total assists scored in the active season if the number is non-negative
    public void setTotalAssists(int assists) throws InvalidStatException {
        String statType = "Assists";
        if(isNaturalNumberStat(statType, assists)) {
            totalAssists = assists;
        }
    }

//    // MODIFIES: this
//    // EFFECTS: Sets the skater's total goals scored in the active season if the number is non-negative
//    public void setTotalGoals(int goals) throws InvalidStatException {
//        if (goals >= 0) {
//            this.totalGoals = goals;
//        } else {
//            throw new InvalidStatException("Goals cannot be negative.");
//        }
//    }
//
//    // MODIFIES: this
//    // EFFECTS: Sets the skater's total assists scored in the active season if the number is non-negative
//    public void setTotalAssists(int assists) throws InvalidStatException {
//        if (assists >= 0) {
//            this.totalAssists = assists;
//        } else {
//            throw new InvalidStatException("Assists cannot be negative.");
//        }
//    }
}