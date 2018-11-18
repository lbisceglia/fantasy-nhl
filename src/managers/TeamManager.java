//package managers;
//
//import models.League;
//import models.Player;
//import models.Team;
//
//import java.io.Serializable;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.Set;
//
//public class TeamManager implements Serializable {
//    private final League league;
//    private Map<Team, List<Player>> teamPlayerMap;
//
//    // EFFECTS: Construct a TeamManager with a blank lookup table
//    public TeamManager(League league) {
//        teamPlayerMap = new HashMap<>();
//        this.league = league;
//    }
//
////    public league getLeague() {
////        return league;
////    }
//
//    // EFFECTS: Returns the teams in the HashMap
//    public Set<Team> getTeams() {
//        return teamPlayerMap.keySet();
//    }
//
//    // EFFECTS: Return the number of teams in the HashMap
//    public int size() {
//        return teamPlayerMap.size();
//    }
//
//    // EFFECTS: Return true if the given team is in the HashMap
//    public boolean containsTeam(Team team) {
//        return teamPlayerMap.containsKey(team);
//    }

//    // EFFECTS: Return true if the HashMap is empty
//    public boolean isEmpty() {
//        return teamPlayerMap.isEmpty();
//    }
//
//    // EFFECTS: Does nothing if the team is valid
//    //          Throws an InvalidTeamException if the given team name is not unique in the league
//    public void checkIfTeamIsUnique(Team team) throws InvalidTeamException {
//        if (!containsTeam(team)) {
//            // do nothing
//        } else {
//            throw new InvalidTeamException();
//        }
//    }
//
////    // EFFECTS: Returns the number of players on the given team
////    public int teamSize(team team) {
////        return teamPlayerMap.get(team).size();
////    }
//
//    // EFFECTS: Adds a new team to the league if it is valid
//    //          Throws an InvalidTeamException if not valid
//    public void addTeam(Team team) {
//        if (!containsTeam(team)) {
//            List<Player> players = team.getPlayers();
//            teamPlayerMap.put(team, players);
//        }
//    }
//
//    public void removeTeam(Team team) {
//        if (containsTeam(team)) {
//            List<Player> players = team.getPlayers();
//            for (Player p : players) {
//                p.removeObserver(team);
//            }
//            teamPlayerMap.remove(team);
//        }
//    }
//
//    public boolean containsPlayer(Team team, Player player) {
//        return teamPlayerMap.get(team).contains(player);
//    }
//
//    public boolean containsPlayer(Player player) {
//        Set<Team> teams = getTeams();
//        for (Team t : teams) {
//            if (containsPlayer(t, player)) {
//                return true;
//            }
//        }
//        return false;
//    }
//
//}
