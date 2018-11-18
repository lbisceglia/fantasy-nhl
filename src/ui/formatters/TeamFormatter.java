package ui.formatters;

import models.Player;

import java.util.ArrayList;
import java.util.Set;

import static ui.formatters.PlayerFormatter.getPlayerNameAndPosition;

public class TeamFormatter {


    public static ArrayList<Player> convertPlayerSetToList(Set<Player> playerSet) {
        ArrayList<Player> players = new ArrayList<>();
        for(Player p : playerSet) {
            players.add(p);
        }
        return players;
    }

    // EFFECTS: Return the list of players on the team1
    public static ArrayList<String> getPlayerNames(ArrayList<Player> players) {
        ArrayList<String> playerNames = new ArrayList<>();
        for(Player p : players) {
            playerNames.add(p.getPlayerName());
        }
        return playerNames;
    }

    public static ArrayList<String> getPlayerNamesAndPositions(ArrayList<Player> players) {
        ArrayList<String> playerTitles = new ArrayList<>();
        for(Player p : players) {
            String title = getPlayerNameAndPosition(p);
            playerTitles.add(title);
        }
        return playerTitles;
    }




}
