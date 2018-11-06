package model.Player;

import model.Team.Team;

import java.io.Serializable;
import java.util.Objects;

public abstract class Player implements Serializable {
    protected String name;
//    protected Position position;
    protected int weekFantasyPoints;
    protected int totalFantasyPoints;
    protected Team team;


    // EFFECTS: Construct a player with the given name and position
    //          Player is not assigned to a team and given base stats
    public Player(String name) {
        this.name = name;
        // TODO: create method for calculating FantasyPoints
        weekFantasyPoints = 0;
        totalFantasyPoints = 0;
        team = null;
    }

//    // EFFECTS: Construct a player with the given name and position
//    public Player(String name, Position position) throws InvalidPositionException {
//        this.name = name;
    //TODO: Show TA this mess
//        if (this instanceof Skater && position.equals(Position.G)) {
//            throw new InvalidPositionException("A skater cannot be a goalie.");
//        } else {
//            this.position = position;
//        }
//        weekFantasyPoints = 0;
//        totalFantasyPoints = 0;
//        team = null;
//    }

    // EFFECTS: Return the player's name
    public String getPlayerName() {
        return name;
    }

//    // EFFECTS: Return the player's hockey position
//    public Position getPlayerPosition() {
//        return position;
//    }

    // EFFECTS: Return the number of fantasy points the player earned this week
    public int getWeekFantasyPoints() {
        return weekFantasyPoints;
    }

    // EFFECTS: Return the number of fantasy points the player earned so far this season
    public int getTotalFantasyPoints() {
        return totalFantasyPoints;
    }

    // EFFECTS: Return the player's team
    public Team getTeam() {
        return team;
    }

    // EFFECTS: Sets the player's team to the given team
    public void setTeam(Team team) {
        if (this.team == null || !this.team.equals(team)) {
            this.team = team;
            this.team.addPlayer(this);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return Objects.equals(name, player.name);
    }

    @Override
    public int hashCode() {

        return Objects.hash(name);
    }
}
