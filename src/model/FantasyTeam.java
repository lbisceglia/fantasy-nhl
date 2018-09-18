package model;

import java.util.ArrayList;

public class FantasyTeam {
    private String teamName;
    private ArrayList<NhlPlayer> players;

    public FantasyTeam (String teamName) {
        this.teamName = teamName;
        this.players = new ArrayList<>();
    }

    // MODIFIES: this
    // EFFECTS: Adds a player to the team
    public void addPlayer(NhlPlayer player) {
        players.add(player);
    }

    // EFFECTS: Returns the team's name
    public String getTeamName() {
        return this.teamName;
    }
}
