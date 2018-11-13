//package model.Player;
//
//import model.Stat.StatManager;
//import model.Team.Team;
//
//import java.io.Serializable;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.Set;
//
//public class PlayerManager implements Serializable {
//    private final Team team;
//    private Map<Player, StatManager> playerStatMap;
//
//    // EFFECTS: Construct a PlayerManager with a blank lookup table
//    public PlayerManager(Team team) {
//        playerStatMap = new HashMap<>();
//        this.team = team;
//    }
//
////    public Team getTeam() {
////        return team;
////    }
////
////    // EFFECTS: Returns the players in the HashMap
////    public Set<Player> getPlayers() {
////        return playerStatMap.keySet();
////    }
//
////    // EFFECTS: Return the number of players in the HashMap
////    public int size() {
////        return playerStatMap.size();
////    }
////
////    // EFFECTS: Returns true if the given player is in the HashMap
////    public boolean containsPlayer(Player player) {
////        return playerStatMap.containsKey(player);
////    }
////
////    // EFFECTS: Return true if the HashMap is empty
////    public boolean isEmpty() {
////        return playerStatMap.isEmpty();
////    }
//
//
////    // EFFECTS: Adds a new player to the HashMap if it is valid
////    //          Throws an InvalidPlayerException if not valid
////    public void addPlayer(Player player) {
////        if (!containsPlayer(player)) {
////            StatManager statManager = player.getStatManager();
////            playerStatMap.put(player, statManager);
////            player.setTeam(team);
////        }
////    }
////
////    // Removes a player from the HashMap
////    public void removePlayer(Player player) {
////        if(containsPlayer(player)) {
////            playerStatMap.remove(player);
////            player.setTeam(null);
////        }
////    }
//
//    public double getFantasyPoints() {
//        double totalPoints = 0;
//        for (Player p : getPlayers()) {
//            totalPoints=+ p.getStatManager().getFantasyPoints();
//        }
//        return totalPoints;
//    }
//
//
//
//}
