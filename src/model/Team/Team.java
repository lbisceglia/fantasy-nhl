package model.Team;

import model.Player.Player;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;

public class Team implements PlayerList, Serializable {
    private String teamName;
    private HashSet<Player> players;
    private int fantasyPoints;

    public Team(String teamName) {
        this.teamName = teamName;
        this.players = new HashSet<>();
        this.fantasyPoints = 0;
    }

    // getters

    // EFFECTS: Returns the team's name
    public String getTeamName() {
        return teamName;
    }

    @Override
    // EFFECTS: Returns the number of players on the team
    public int getSize() {
        return players.size();
    }


    // EFFECTS: Returns the list of players on the team
    public HashSet<Player> getPlayers() {
        return players;
    }

    @Override
    // EFFECTS: Returns the list of player names on the team
    public ArrayList<String> getPlayerNames() {
        ArrayList<String> roster = new ArrayList<>();
        for (Player p : players) {
            String player = p.getPlayerName();
            roster.add(player);
        }
        return roster;
    }

    @Override
    // EFFECTS: Returns true if the player is on the team
    //          Returns false otherwise
    public boolean containsPlayer(Player p) {
        return players.contains(p);
    }

    @Override
    // EFFECTS: Returns true if the Team contains a player with the given name
    //          Returns false otherwise
    public boolean containsPlayerName(String playerName) {
        for(Player p : players) {
            if(p.getPlayerName().equals(playerName)) {
                return true;
            }
        } return false;
    }

    @Override
    // EFFECTS: Returns the player with the corresponding name
    //          Returns nothing if not found
    public Player playerLookup(String playerName) {
        for(Player p : this.players) {
            if (p.getPlayerName().equals(playerName)) {
                return p;
            }
        } return null;
    }

    public void printTeamAndPlayers() {
        System.out.println("Team: " + this.getTeamName() + ", Roster: " + this.getPlayerNames());
    }

    //TODO: add total team goals & assists functionality
//    // EFFECTS: Returns the total number of goals scored by players on the team
//    public int getTotalTeamGoals() {
//        int totalGoals = 0;
//        for (Player p : players) {
//            if(p instanceof Skater) {
//                totalGoals += p.getTotalGoals();
//            }
//            totalGoals += p.getTotalGoals();
//        }
//        return totalGoals;
//    }
//
//    // EFFECTS: Returns the total number of assists scored by players on the team
//    public int getTotalTeamAssists() {
//        int totalAssists = 0;
//        for (Player p : players) {
//            totalAssists += p.getTotalAssists();
//        }
//        return totalAssists;
//    }

    // setters

    @Override
    // MODIFIES: this
    // EFFECTS: Adds a player to the team if not already on the team
    //          Does nothing if the player is already on the team
    public void addPlayer(Player player) {
        players.add(player);
    }

    @Override
    // MODIFIES: this
    // EFFECTS: Removes a player from the team if they are on the team
    //          Does nothing if the player is not on the team
    public void removePlayer(Player player) {
        players.remove(player);
    }
}
