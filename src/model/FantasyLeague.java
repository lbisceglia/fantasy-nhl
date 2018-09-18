package model;

import java.util.ArrayList;

public class FantasyLeague {
    // TODO 2: Add FantasyLeague name once multiple leagues are supported
    // String name;
    ArrayList<FantasyTeam> participants;
    ArrayList<NhlPlayer> availablePlayers;

    public FantasyLeague() {
//        this.name = name;
        this.participants = new ArrayList<>();

        // TODO 1: Remove NHLPlayer object instantiation once NHL player database is added
        // Initializing NHL Players to use in the data model
        NhlPlayer McDavid = new NhlPlayer("Connor McDavid", "Edmonton Oilers", "Centre", 41, 67);
        NhlPlayer Boeser = new NhlPlayer("Brock Boeser", "Vancouver Canucks", "Right Wing", 29, 26);
        NhlPlayer Ovechkin = new NhlPlayer("Alexander Ovechkin", "Washington Capitals", "Left Wing", 49, 38);
        NhlPlayer Hedman = new NhlPlayer("Victor Hedman", "Tampa Bay Lightning", "Defence", 17, 46);
        NhlPlayer Josi = new NhlPlayer("Roman Josi", "Nashville Predators", "Defence", 14, 39);

        this.availablePlayers = new ArrayList<>();
        this.availablePlayers.add(McDavid);
        this.availablePlayers.add(Boeser);
        this.availablePlayers.add(Ovechkin);
        this.availablePlayers.add(Hedman);
        this.availablePlayers.add(Josi);
    }

    // EFFECTS: Returns true if the team name is unique in this league
    //          Returns false if the name is already taken
    public boolean isExistingTeamName(String teamName) {
        for (FantasyTeam t : this.participants) {
            if (teamName.equals(t.getTeamName())) {
                return true;
            }
        }
        return false;
    }

    // REQUIRES: The team name is unique (i.e. valid)
    // MODIFIES: this
    // EFFECTS: Creates a new FantasyTeam with the given name and adds it to the league
    public void createTeam(String teamName) {
        System.out.println("Creating team...");
        FantasyTeam team = new FantasyTeam(teamName);
        this.participants.add(team);
        System.out.println(teamName + " was added to the fantasy league!");
    }

    // EFFECTS: Creates and returns a list of teams in the given league
    public ArrayList<String> getTeamNames() {
        ArrayList<String> teams = new ArrayList<>();
        for(FantasyTeam t : this.participants){
            String team = t.getTeamName();
            teams.add(team);
        }
        return teams;
    }
}
