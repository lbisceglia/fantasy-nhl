package model.League;

import java.io.Serializable;

public class LeagueManager implements Serializable {




//    Map<League, TeamManager> leagueTeamMap;
//
//    // EFFECTS: Construct a LeagueManager with a blank lookup table
//    public LeagueManager() {
//        leagueTeamMap = new HashMap<>();
//    }
//
//    // EFFECTS: Return the number of leagues in the HashMap
//    public int size() {
//        return leagueTeamMap.size();
//    }
//
//    // EFFECTS: Return true if the given league is in the HashMap
//    public boolean containsLeague(League league) {
//        return leagueTeamMap.containsKey(league);
//    }
//
//    // EFFECTS: Return true if the HashMap is empty
//    public boolean isEmpty() {
//        return leagueTeamMap.isEmpty();
//    }
//
//    // EFFECTS: Returns the number of teams in the given league
//    public int leagueSize(League league) {
//        return leagueTeamMap.get(league).size();
//    }
//
//    // EFFECTS: Adds a new league to the HashMap
////    public void addLeague(HashSet<Player> players) {
////        League league = new League(players);
////        TeamManager teamManager = new TeamManager(league);
////        leagueTeamMap.put(league, teamManager);
////    }
//
//    public void removeLeague(League league) {
//        leagueTeamMap.remove(league);
//    }
//
//    // EFFECTS: Adds a team1 to the given league and adds the league to the team1's league
//    public void addTeam(League league, Team team) {
//        if (!containsTeam(league, team)) {
//            league.addTeam(team);
//        }
//    }
//
//    // EFFECTS: Removes a team from the given league and adds its players back to the available players pool
//    public void removeTeam(League league, Team team) {
//        if (containsTeam(league, team)) {
//            league.removeTeam(team);
//            List<Player> players = team.getPlayers();
//            league.getAvailablePlayers().addAll(players);
//        }
//    }
}
