package ui;

import model.League.League;
import model.Player.Player;
import model.Team.Team;

import java.util.ArrayList;
import java.util.Set;

public class LeagueFormatter {

    // EFFECTS: Returns a list of team names in this league
    public static ArrayList<String> getTeamNames(Set<Team> teams) {
        ArrayList<String> teamNames = new ArrayList<>();

        for (Team t : teams) {
            String team = t.getTeamName();
            teamNames.add(team);
        }
        return teamNames;
    }

    public static ArrayList<Team> convertTeamSetToList(Set<Team> teamSet) {
        ArrayList<Team> teams = new ArrayList<>();
        for(Team t : teams) {
            teams.add(t);
        }
        return teams;
    }

    // EFFECTS: Returns a list of the players names available in this league
    public static ArrayList<String> getAvailablePlayerNames(League league) {
        Set<Player> availablePlayers = league.getAvailablePlayers();
        ArrayList<String> availablePlayerNames = new ArrayList<>();

        for (Player p : availablePlayers) {
            availablePlayerNames.add(p.getPlayerName());
        }
        return availablePlayerNames;
    }

}
