package model.Player;

import model.exceptions.InvalidPositionException;

import java.io.Serializable;

public abstract class Player implements Serializable {
    protected String name;
    protected Position position;
    protected int weekFantasyPoints;
    protected int totalFantasyPoints;


    // EFFECTS: Construct a player with the given name and position
    public Player(String name, Position position) throws InvalidPositionException {
        this.name = name;
        if (this instanceof Skater && position.equals(Position.G)) {
            throw new InvalidPositionException("A skater cannot be a goalie.");
        } else {
            this.position = position;
        }
        // TODO: create method for calculating FantasyPoints
        weekFantasyPoints = 0;
        totalFantasyPoints = 0;
    }

    //getters

    // EFFECTS: Return the player's name
    public String getPlayerName() {
        return name;
    }

    // EFFECTS: Return the player's hockey position
    public Position getPlayerPosition() {
        return position;
    }

    // EFFECTS: Return the number of fantasy points the player earned this week
    public int getWeekFantasyPoints() {
        return weekFantasyPoints;
    }

    // EFFECTS: Return the number of fantasy points the player earned so far this season
    public int getTotalFantasyPoints() {
        return totalFantasyPoints;
    }
}
