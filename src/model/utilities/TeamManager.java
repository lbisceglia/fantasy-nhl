package model.utilities;

import model.Player.Player;
import model.Team.Team;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class TeamManager {
    Map<Team, Set<Player>> teamPlayerMap;

    // EFFECTS: Construct a TeamManager with a blank lookup table
    public TeamManager() {
        teamPlayerMap = new HashMap<>();
    }

    public Set<Team> getTeams() {
        return teamPlayerMap.keySet();
    }

    // EFFECTS: Return the number of teams in the HashMap
    public int size() {
        return teamPlayerMap.size();
    }

    // EFFECTS: Return true if the given team is in the HashMap
    public boolean containsTeam(Team team) {
        return teamPlayerMap.containsKey(team);
    }

    // EFFECTS: Return true if the HashMap is empty
    public boolean isEmpty() {
        return teamPlayerMap.isEmpty();
    }

    public boolean isValidTeam(String teamName) {
        Team team = new Team(teamName);
        return !containsTeam(team);
    }

    // EFFECTS: Returns the number of players on the given team
    public int teamSize(Team team) {
        return teamPlayerMap.get(team).size();
    }

    // EFFECTS: Adds a new team to the HashMap if it is valid
    public void addTeam(String teamName) {
        if (isValidTeam(teamName)) {
            Team team = new Team(teamName);
            Set<Player> players = team.getPlayers();
            teamPlayerMap.put(team, players);
        }
    }

    public void removeTeam(String teamName) {
        Team team = new Team(teamName);
        teamPlayerMap.remove(team);
    }

    private boolean containsPlayer(Team team, Player player) {
        return teamPlayerMap.get(team).contains(player);
    }

    // EFFECTS: Adds a player to the given team and the team to the player's team
    public void addPlayer(Team team, Player player) {
        if (!containsPlayer(team, player)) {
            team.addPlayer(player);
            player.setTeam(team);
        }
    }

    public void removePlayer(Team team, Player player) {
        if(containsPlayer(team, player)) {
            team.removePlayer(player);
            player.setTeam(null);
        }
    }

}
