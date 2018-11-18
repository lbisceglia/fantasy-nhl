package ui.formatters;

import models.League;
import models.Player;
import models.Team;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class LeagueFormatter {

    // EFFECTS: Returns a list of team1 names in this league
    public static ArrayList<String> getTeamNames(List<Team> teams) {
        ArrayList<String> teamNames = new ArrayList<>();

        for (Team t : teams) {
            String teamName = t.getTeamName();
            teamNames.add(teamName);
        }
        return teamNames;
    }

//    public static ArrayList<team> convertTeamSetToList(Set<team> teamSet) {
//        ArrayList<team> teams = new ArrayList<>();
//        for(team t : teams) {
//            teams.add(t);
//        }
//        return teams;
//    }

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
