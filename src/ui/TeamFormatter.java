package ui;

import model.Player.Goalie;
import model.Player.Player;
import model.Player.Skater;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import static ui.PlayerFormatter.getPlayerNameAndPosition;

public class TeamFormatter {

    public static Set<Skater> filterSkaters(Set<Player> players) {
        Set<Skater> skaters = new HashSet<>();
        for (Player p : players) {
            if (p instanceof Skater) {
                Skater s = (Skater) p;
                skaters.add(s);
            }
        }
        return skaters;
    }

    public static Set<Goalie> filterGoalies(Set<Player> players) {
        Set<Goalie> goalies = new HashSet<>();
        for (Player p : players) {
            if (p instanceof Goalie) {
                Goalie g = (Goalie) p;
                goalies.add(g);
            }
        }
        return goalies;
    }

    public static ArrayList<Player> convertPlayerSetToList(Set<Player> playerSet) {
        ArrayList<Player> players = new ArrayList<>();
        for(Player p : playerSet) {
            players.add(p);
        }
        return players;
    }

    // EFFECTS: Return the list of players on the team
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
