package model.Team;

import model.League.League;
import model.Player.Player;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Team implements Serializable {
    private String teamName;
    private int fantasyPoints;
    private Set<Player> players;
    private League league;

    // EFFECTS: Constructs an empty team with the given name and base stats
    public Team(String teamName) {
        this.teamName = teamName;
        fantasyPoints = 0;
        players = new HashSet<>();
        league = null;
    }

    // EFFECTS: Returns the team's name
    public String getTeamName() {
        return teamName;
    }

    // EFFECTS: Returns the team's fantasy points
    public int getFantasyPoints() {
        return fantasyPoints;
    }

    // EFFECTS: Return the list of players on the team
    public Set<Player> getPlayers() {
        return players;
    }

    public League getLeague() {
        return league;
    }

    // EFFECTS: Sets the team's name to the given string non-empty
    //          Does nothing if the string is empty
    public void setTeamName(String teamName) {
        if (!teamName.equals("")) {
            this.teamName = teamName;
        }
    }

    // EFFECTS: adds a player to players and adds this team to the player's team
    public void addPlayer(Player player) {
        if (!containsPlayer(player)) {
            players.add(player);
            player.setTeam(this);
        }
    }

    // EFFECTS: adds the player from players and removes this team from the player's team
    public void removePlayer(Player player) {
        if (containsPlayer(player)) {
            players.remove(player);
            player.setTeam(null);
        }
    }

    // EFFECTS: returns true if the player is on the roster
    public boolean containsPlayer(Player player) {
        return players.contains(player);
    }

    // EFFECTS: Sets the team's league to the given league
    public void setLeague(League league) {
        if (this.league == null || !this.league.equals(league)) {
            this.league = league;
            this.league.addTeam(this);
        }
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
