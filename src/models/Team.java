package models;

import exceptions.InvalidTeamException;
import interfaces.Observer;
import managers.FantasyWeekManager;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;

public class Team implements Serializable, Observer {
    public static final int MAX_ROSTER_SIZE = 6;
    public static final int ALLOWED_CENTERS = 1;
    public static final int ALLOWED_RWS = 1;
    public static final int ALLOWED_LWS = 1;
    public static final int ALLOWED_DEFENCEMEN = 2;
    public static final int ALLOWED_GOALIES = 1;

    private String teamName;
    private double currentWeekFantasyPoints;
    private double overallFantasyPoints;
    private ArrayList<Player> players;
    private FantasyWeekManager fantasyWeekManager;


    // EFFECTS: Constructs an empty team with the given name and base statManager
    public Team(String teamName) throws InvalidTeamException {
        if (!teamName.equals("")) {
            this.teamName = teamName;
        } else {
            throw new InvalidTeamException("Sorry, a team cannot be created with an empty name.");
        }
        currentWeekFantasyPoints = 0;
        overallFantasyPoints = 0;
        players = new ArrayList<>();
        fantasyWeekManager = new FantasyWeekManager();
    }

    // EFFECTS: Returns the team's name
    public String getTeamName() {
        return teamName;
    }

    // EFFECTS: Return the fantasy points for the current week
    public double getCurrentWeekFantasyPoints() {
        return currentWeekFantasyPoints;
    }

    // EFFECTS: Returns the team's overal fantasy points
    public double getOverallFantasyPoints() {
        return overallFantasyPoints;
    }

    // EFFECTS: Return the player roster
    public ArrayList<Player> getPlayers() {
        return players;
    }

    // MODIFIES: this
    // EFFECTS: Sets the team's name
    //          Does nothing if the string is empty
    public void setTeamName(String teamName) {
        if (!teamName.equals("")) {
            this.teamName = teamName;
        }
    }

    // MODIFIES: this
    // EFFECTS: sets the current week's fantasy points
    public void setCurrentWeekFantasyPoints(double points) {
        this.currentWeekFantasyPoints = points;
    }

    // MODIFIES: this, player
    // EFFECTS: Adds the player to the roster and subscribes to the player's updates
    public void addPlayer(Player player) {
        if (!players.contains(player)) {
            players.add(player);
            player.addObserver(this);
        }
    }

    // MODIFIES: this, player
    // EFFECTS: Removes the player from the roster and unsubscribes from receiving updates
    public void removePlayer(Player player) {
        players.remove(player);
        player.removeObserver(this);
    }


    @Override
    // MODIFIES: this
    // EFFECTS: Adds the stat to the fantasy week in which it was earned
    //          Adds the fantasy points to the team's totals and updates the end user
    public void update(Stat stat) {
        double points = stat.getFantasyPoints();

        fantasyWeekManager.addStat(stat);
        currentWeekFantasyPoints += points;
        overallFantasyPoints += points;
        fantasyWeekManager.updateUser(this,stat);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Team team = (Team) o;
        return Objects.equals(teamName, team.teamName);
    }

    @Override
    public int hashCode() {

        return Objects.hash(teamName);
    }
}
