package model.Player;

import java.io.Serializable;

public abstract class Player implements Serializable{
    protected final String name;
    protected Position position;
    protected int weekFantasyPoints;
    protected int totalFantasyPoints;

    public Player(String name, Position position, int weekFantasyPoints, int totalFantasyPoints){
        this.name = name;
        this.position = position;
        if(weekFantasyPoints >= 0) {
            this.weekFantasyPoints = weekFantasyPoints;
        } else {
            this.weekFantasyPoints = 0;
        }
        if(totalFantasyPoints >= 0) {
            this.totalFantasyPoints = totalFantasyPoints;
        } else {
            this.totalFantasyPoints = 0;
        }
    }

    //getters

    // EFFECTS: Return the skater's name
    public String getPlayerName() {
        return this.name;
    }

    // EFFECTS: Return the skater's hockey position
    public Position getPlayerPosition() {
        return this.position;
    }

    // EFFECTS: Return the number of fantasy points the skater earned this week
    public int getWeekFantasyPoints() {
        return this.weekFantasyPoints;
    }

    // EFFECTS: Return the number of fantasy points the skater earned so far this season
    public int getTotalFantasyPoints() {
        return this.totalFantasyPoints;
    }
}
