package model.Player;

import model.exceptions.InvalidPositionException;
import model.exceptions.InvalidStatException;

import java.io.Serializable;

public class Skater extends Player implements Serializable {
    private int totalGoals;
    private int totalAssists;

    // EFFECTS: Constructs a skater with the given name, position, totalGoals, and totalAssists
    //          Does nothing if the skater tries to have position G (i.e. goalie)
    public Skater(String name, Position position, int totalGoals, int totalAssists) throws InvalidPositionException, InvalidStatException {
        super(name, position);
        if (totalGoals >= 0) {
            this.totalGoals = totalGoals;
        } else {
            throw new InvalidStatException("Goals cannot be negative.");
//            this.totalGoals = 0;
        }
        if (totalAssists >= 0) {
            this.totalAssists = totalAssists;
        } else {
//            this.totalAssists = 0;
            throw new InvalidStatException("Assists cannot be negative.");
        }
        //TODO: add the following statistical categories: shots on goal, powerplay points, hits, blocked shots
    }

    // EFFECTS: Constructs a skater with the given name, position, and base stats
    public Skater(String name, Position position) {
        super(name, position);
        totalGoals = 0;
        totalAssists = 0;
        //TODO: add the following statistical categories: shots on goal, powerplay points, hits, blocked shots
    }

    //getters

    // EFFECTS: Returns the skater's total goals scored in the active season
    public int getTotalGoals() {
        return this.totalGoals;
    }

    // EFFECTS: Returns the skater's total assists scored in the active season
    public int getTotalAssists() {
        return this.totalAssists;
    }

    // setters

    // MODIFIES: this
    // EFFECTS: Sets the skater's total goals scored in the active season if the number is non-negative
    public void setTotalGoals(int goals) throws InvalidStatException {
        if (goals >= 0) {
            this.totalGoals = goals;
        } else {
            throw new InvalidStatException("Goals cannot be negative.");
        }
    }

    // MODIFIES: this
    // EFFECTS: Sets the skater's total assists scored in the active season if the number is non-negative
    public void setTotalAssists(int assists) throws InvalidStatException {
        if (assists >= 0) {
            this.totalAssists = assists;
        } else {
            throw new InvalidStatException("Assists cannot be negative.");
        }
    }
}