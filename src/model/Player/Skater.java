package model.Player;

import java.io.Serializable;

public class Skater extends Player implements Serializable {
    private int totalGoals;
    private int totalAssists;

    public Skater(String name, Position position, int weekFantasyPoints, int totalFantasyPoints, int totalGoals, int totalAssists){
        super(name, position, weekFantasyPoints, totalFantasyPoints);
        if(totalGoals >= 0) {
            this.totalGoals = totalGoals;
        } else {
            this.totalGoals = 0;
        }
        if(totalAssists >= 0) {
            this.totalAssists = totalAssists;
        } else {
            this.totalAssists = 0;
        }
    //TODO: Add the following statistical categories: shots on goal, powerplay points, hits, blocked shots
    }

    //getters

    // EFFECTS: Return the skater's total goals scored in the active season
    public int getTotalGoals() {
        return this.totalGoals;
    }

    // EFFECTS: Return the skater's total assists scored in the active season
    public int getTotalAssists() {
        return this.totalAssists;
    }

    // setters

    // MODIFIES: this
    // EFFECTS: Sets the skater's total goals scored in the active season if the number is positive
    public void setTotalGoals(int goals) {
        if (goals > 0) {
            this.totalGoals = goals;
        }
    }

    // MODIFIES: this
    // EFFECTS: Sets the skater's total assists scored in the active season if the number is positive
    public void setTotalAssists(int assists) {
        if (assists > 0) {
            this.totalAssists = assists;
        }
    }
}