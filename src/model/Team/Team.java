package model.Team;

import model.Player.Goalie;
import model.Player.Player;
import model.Player.Position;
import model.Player.Skater;

import java.io.Serializable;
import java.util.ArrayList;

public class Team implements Serializable, SkaterList, GoalieList {
    private String teamName;
    private ArrayList<Skater> skaters;
    private ArrayList<Goalie> goalies;
    private int fantasyPoints;

    public Team(String teamName) {
        this.teamName = teamName;
        skaters = new ArrayList<>();
        goalies = new ArrayList<>();
        fantasyPoints = 0;
    }

    // getters

    // EFFECTS: Returns the team's name
    public String getTeamName() {
        return teamName;
    }

    @Override
    // EFFECTS: Returns the number of players on the team
    public int getSize() {
        return getPlayers().size();
    }


    @Override
    // EFFECTS: Returns the list of skaters on the team
    public ArrayList<Skater> getSkaters() {
        return skaters;
    }

    @Override
    // EFFECTS: Returns the list of goalies on the team
    public ArrayList<Goalie> getGoalies() {
        return goalies;
    }

    @Override
    // EFFECTS: Returns the list of players on the team
    public ArrayList<Player> getPlayers() {
        ArrayList<Player> players = new ArrayList<>();
        players.addAll(skaters);
        players.addAll(goalies);
        return players;
    }

    @Override
    // EFFECTS: Returns the list of player names on the team
    public ArrayList<String> getPlayerNames() {
        ArrayList<String> roster = new ArrayList<>();
        for (Player p : getPlayers()) {
            String player = p.getPlayerName();
            roster.add(player);
        }
        return roster;
    }

    // EFFECTS: Returns the list of player names and positions on the team
    public ArrayList<String> getPlayerNamesandPositions() {
        ArrayList<String> roster = new ArrayList<>();
        for (Player p : getPlayers()) {
            String player = p.getPlayerName();
            Position position = p.getPlayerPosition();
            String title = player + " (" + position + ")";
            roster.add(title);
        }
        return roster;
    }

    @Override
    // EFFECTS: Returns true if the player is on the team
    //          Returns false otherwise
    public boolean containsPlayer(Player p) {
        return getPlayers().contains(p);
    }

    @Override
    // EFFECTS: Returns true if the Team contains a player with the given name
    //          Returns false otherwise
    public boolean containsPlayerName(String playerName) {
        for (Player p : getPlayers()) {
            if (p.getPlayerName().equals(playerName)) {
                return true;
            }
        }
        return false;
    }

    @Override
    // EFFECTS: Returns the skater with the corresponding name
    //          Returns nothing if not found
    public Skater skaterLookup(String skaterName) {
        for (Skater s : getSkaters()) {
            if (s.getPlayerName().equals(skaterName)) {
                return s;
            }
        }
        return null;
    }

    @Override
    // EFFECTS: Returns the goalie with the corresponding name
    //          Returns nothing if not found
    public Goalie goalieLookup(String goalieName) {
        for (Goalie g : getGoalies()) {
            if (g.getPlayerName().equals(goalieName)) {
                return g;
            }
        }
        return null;
    }


    @Override
    // EFFECTS: Returns the player with the corresponding name
    //          Returns nothing if not found
    public Player playerLookup(String playerName) {
        if (null == skaterLookup(playerName)) {
            return goalieLookup(playerName);
        }
        return skaterLookup(playerName);
    }

    public void printTeamAndPlayers() {
        System.out.println("Team: " + this.getTeamName() + ", Roster: " + this.getPlayerNamesandPositions());
    }

    // EFFECTS: Returns the total number of goals scored by skaters on the team
    public int getTotalTeamGoals() {
        int totalGoals = 0;
        for (Skater s : skaters) {
            totalGoals += s.getTotalGoals();
        }
        return totalGoals;
    }

    // EFFECTS: Returns the total number of assists scored by skaters on the team
    public int getTotalTeamAssists() {
        int totalAssists = 0;
        for (Skater s : skaters) {
            totalAssists += s.getTotalAssists();
        }
        return totalAssists;
    }

    // setters

    @Override
    // MODIFIES: this
    // EFFECTS: Adds a skater to the team if not already on the team
    //          Does nothing if the skater is already on the team
    public void addSkater(Skater s) {
        if (!skaters.contains(s)) {
            skaters.add(s);
        }
    }

    @Override
    // MODIFIES: this
    // EFFECTS: Adds a skater to the team if not already on the team
    //          Does nothing if the skater is already on the team
    public void addGoalie(Goalie g) {
        if (!goalies.contains(g)) {
            goalies.add(g);
        }
    }

    @Override
    // MODIFIES: this
    // EFFECTS: Removes a skater from the team if they are on the team
    //          Does nothing if the skater is not on the team
    public void removeSkater(Skater s) {
        if (skaters.contains(s)) {
            skaters.remove(s);
        }
    }

    @Override
    // MODIFIES: this
    // EFFECTS: Removes a goalie from the team if they are on the team
    //          Does nothing if the goalie is not on the team
    public void removeGoalie(Goalie g) {
        if (goalies.contains(g)) {
            goalies.remove(g);
        }
    }


}
