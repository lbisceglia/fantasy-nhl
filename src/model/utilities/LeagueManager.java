package model.utilities;

import model.League.League;
import model.Player.Player;
import model.Team.Team;

import java.util.*;

public class LeagueManager {
    Map<League, Set<Team>> leagueTeamMap;

    // EFFECTS: Construct a LeagueManager with a blank lookup table
    public LeagueManager() {
        leagueTeamMap = new HashMap<>();
    }

    // EFFECTS: Return the number of leagues in the HashMap
    public int size() {
        return leagueTeamMap.size();
    }

    // EFFECTS: Return true if the given league is in the HashMap
    public boolean containsLeague(League league) {
        return leagueTeamMap.containsKey(league);
    }

    // EFFECTS: Return true if the HashMap is empty
    public boolean isEmpty() {
        return leagueTeamMap.isEmpty();
    }

    // EFFECTS: Returns the number of teams in the given league
    public int leagueSize(League league) {
        return leagueTeamMap.get(league).size();
    }

    // EFFECTS: Returns true if the team already exists in this league
    //          Returns false if the team does not already exist in this league
    private boolean containsTeam(League league, Team team) {
        return leagueTeamMap.get(league).contains(team);
    }

    // EFFECTS: Returns true if the given player is available in the league
    public boolean playerAvailable(League league, Player player) {
        return league.getAvailablePlayers().contains(player);
    }


    // EFFECTS: Adds a new league to the HashMap
    public void addLeague(HashSet<Player> players) {
        League league = new League(players);
        Set<Team> teams = league.getTeams();
        leagueTeamMap.put(league, teams);
    }

    public void removeLeague(League league) {
        leagueTeamMap.remove(league);
    }

    // EFFECTS: Adds a team to the given league and the league to the team's league
    public void addTeam(League league, Team team) {
        if (!containsTeam(league, team)) {
            league.addTeam(team);
            team.setLeague(league);
        }
    }

    public void removeTeam(League league, Team team) {
        if (!containsTeam(league, team)) {
            league.addTeam(team);
            team.setLeague(null);
        }
    }
}
