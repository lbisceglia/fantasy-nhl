package model.League;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import model.Loadable;
import model.Player.Goalie;
import model.Player.Player;
import model.Player.Position;
import model.Player.Skater;
import model.Saveable;
import model.Team.Team;
import model.exceptions.DuplicateMatchException;
import model.exceptions.NoMatchException;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class League implements Loadable, Saveable, Serializable {
    private static final String savePath = "C:/Users/Lorenzo Bisceglia/Google Drive/1 - School/1 - BCS/CPSC 210/Project/projectw1_team29/src";

    Map<Team, ArrayList<Player>> teamPlayerMap;
    ArrayList<Player> availablePlayers;

    // EFFECTS: Constructs a league with the availableSkaters and availableGoalies
    public League(Map<Team, ArrayList<Player>> teamPlayerMap, ArrayList<Player> availablePlayers) {
        this.teamPlayerMap = teamPlayerMap;
        this.availablePlayers = availablePlayers;
    }

    // EFFECTS: Returns a list of teams in this league
    public Set<Team> getTeams() {
        return teamPlayerMap.keySet();
    }

    // EFFECTS: Returns the number of Teams in the league
    public int size() {
        return teamPlayerMap.size();
    }

    // EFFECTS: Returns the HashMap of teams in this league
    public Player getPlayerOnATeam(String playerName, Team team) {
        ArrayList<Player> players = teamPlayerMap.get(team);
        return playerLookup(playerName, players);
    }

    // EFFECTS: Returns a list of team names in this league
    public ArrayList<String> getTeamNames() {
        ArrayList<String> teamNames = new ArrayList<>();
        for (Team t : getTeams()) {
            String team = t.getTeamName();
            teamNames.add(team);
        }
        return teamNames;
    }

    // EFFECTS: Returns a list of the players available in this league
    public ArrayList<Player> getAvailablePlayers() {
        return availablePlayers;
    }

    // EFFECTS: Returns a list of the players names available in this league
    public ArrayList<String> getAvailablePlayerNames() {
        ArrayList<String> availablePlayerNames = new ArrayList<>();
        for (Player p : getAvailablePlayers()) {
            availablePlayerNames.add(p.getPlayerName());
        }
        return availablePlayerNames;
    }


    // TODO: write tests for this method
    // EFFECTS: Returns a list of the players names available in this league
    public ArrayList<String> getAvailableSkaterNames() {
        ArrayList<String> availableSkaterNames = new ArrayList<>();
        for (Player p : getAvailablePlayers()) {
            if (p instanceof Skater) {
                availableSkaterNames.add(p.getPlayerName());
            }
        }
        return availableSkaterNames;
    }


    // EFFECTS: Returns a list of the players names available in this league
    public ArrayList<String> getAvailableGoalieNames() {
        ArrayList<String> availableGoalieNames = new ArrayList<>();
        for (Player p : getAvailablePlayers()) {
            if (p instanceof Goalie) {
                availableGoalieNames.add(p.getPlayerName());
            }
        }
        return availableGoalieNames;
    }


    // EFFECTS: Returns true if the team already exists in this league
    //          Returns false if the team does not already exist in this league
    public boolean containsTeam(Team team) {
        return teamPlayerMap.containsKey(team);
    }

    // TODO: USE THIS EXAMPLE WITH TA
    // EFFECTS: Returns true if the team name already exists in this league
    //          Returns false if the name is unique in this league
    public boolean containsTeamName(String teamName) {
        for (Team t : getTeams()) {
            if (teamName.equals(t.getTeamName())) {
                return true;
            }
        }
        return false;
    }


    // EFFECTS: Returns true if the player name matches an available player in this league
    //          Returns false otherwise
    public boolean containsAvailablePlayerName(String playerName) {
        return (getAvailablePlayerNames().contains(playerName));
    }


    // MODIFIES: this
    // EFFECTS: Creates a new Team adds it to the league if the name isn't already taken
    //          Does nothing if name is already taken
    public void addTeam(String teamName) throws DuplicateMatchException {
        if (!containsTeamName(teamName)) {
            Team team = new Team(teamName);
            ArrayList<Player> players = new ArrayList<>();
            teamPlayerMap.put(team, players);
        } else {
            throw new DuplicateMatchException();
        }
    }


    // MODIFIES: this, TODO: ADD WHAT ELSE IT MODIFIES
    // EFFECTS: Adds a player to a Team and removes them from the League available players list
    public void addPlayerToFantasyTeam(String teamName, String playerName) throws NoMatchException {
        if (containsTeamName(teamName)) {
            if (containsAvailablePlayerName(playerName)) {
                // The player name is valid; Player is added to the team, removed from available players
                Team team = teamLookup(teamName);
                Player player = playerLookup(playerName, availablePlayers);

                ArrayList<Player> players = teamPlayerMap.get(team);
                players.add(player);
                availablePlayers.remove(player);
            }
        } else {
            throw new NoMatchException();
        }
    }


    // EFFECTS: Returns the player with the corresponding name
    //          Returns a null value if not found
    public Player playerLookup(String playerName, ArrayList<Player> players) {
        for (Player p : players) {
            if (p.getPlayerName().equals(playerName)) {
                return p;
            }
        }
        return null;
    }


    // EFFECTS: Returns the team with the corresponding team name
    public Team teamLookup(String teamName) {
        for (Team t : getTeams()) {
            if (t.getTeamName().equals(teamName)) {
                return t;
            }
        }
        return null;
    }


    // MODIFIES: this
    // EFFECTS: Removes Player from the list if they are on the list
    //          Does nothing if Player is not on the list
    private void removeAvailablePlayer(String playerName) {
        if (containsAvailablePlayerName(playerName)) {
            availablePlayers.remove(playerLookup(playerName, availablePlayers));
        }
    }


    // MODIFIES: this
    // EFFECTS: removes Team from the fantasy league, adds its Players back to the availablePlayers list
    public void removeTeam(Team team) {
        if (teamPlayerMap.containsKey(team)) {
            ArrayList<Player> players = teamPlayerMap.get(team);
            for (Player p : players) {
                players.remove(p);
                availablePlayers.add(p);
            }
            teamPlayerMap.remove(team);
        }
    }


    // EFFECTS: Prints the team name and roster for every team in the league
    public void printTeamsAndPlayers() {
        for (Team t : getTeams()) {
            ArrayList<Player> players = new ArrayList<>();
            System.out.println("Team: " + t.getTeamName() + ", Roster: " + getPlayerNamesAndPositions(t));
        }
    }


    // EFFECTS: Returns the list of player names and positions on the team
    public ArrayList<String> getPlayerNamesAndPositions(Team team) {
        ArrayList<String> roster = new ArrayList<>();
        ArrayList<Player> players = teamPlayerMap.get(team);

        for (Player p : players) {
            String player = p.getPlayerName();
            Position position = p.getPlayerPosition();
            String title = player + " (" + position + ")";
            roster.add(title);
        }
        return roster;
    }


    @Override
    //Modeled after Object Stream tutorial, 2018-10-01 [https://www.mkyong.com/java/how-to-read-and-write-java-object-to-a-file/]
    // TODO: add specification and tests for this method
    public void save() {
        try {
            FileOutputStream f = new FileOutputStream(new File("fantasyLeague.txt"));
            ObjectOutputStream o = new ObjectOutputStream(f);

            // Write objects to file
            o.writeObject(this.availablePlayers);

            o.close();
            f.close();

        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        } catch (IOException e) {
            System.out.println("Error initializing stream");
        }
    }

    @Override
    //Modeled after Object Stream tutorial, 2018-10-01 [https://www.mkyong.com/java/how-to-read-and-write-java-object-to-a-file/]
    // TODO: add specification and tests for this method
    public void load() {
        try {
            FileInputStream fi = new FileInputStream(new File("fantasyLeague.txt"));
            ObjectInputStream oi = new ObjectInputStream(fi);

            // Read objects
            ArrayList<Player> availablePlayers = (ArrayList<Player>) oi.readObject();
//            this.participants = participants1;
//            this.availableSkaters = availableSkaters1;
//            this.availableGoalies = availableGoalies1;

            oi.close();
            fi.close();

        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        } catch (IOException e) {
            System.out.println("Error initializing stream");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    // TODO: add specification and tests for this method
    public static League loadLeague() {
        try (Reader reader = new FileReader(savePath + "/league.json")) {
            JsonReader jsonReader = new JsonReader(reader);
            GsonBuilder gsonBuilder = new GsonBuilder();
            Gson gson = gsonBuilder.create();

            League restoredLeague = gson.fromJson(jsonReader, League.class);

            return restoredLeague;
        } catch (FileNotFoundException e) {
            Map<Team, ArrayList<Player>> teamPlayerMap = new HashMap<>();
            ArrayList<Player> availablePlayers = new ArrayList<>();
            return new League(teamPlayerMap, availablePlayers);
        } catch (IOException e) {
            System.out.println("Error initializing stream");
            e.printStackTrace();

            Map<Team, ArrayList<Player>> teamPlayerMap = new HashMap<>();
            ArrayList<Player> availablePlayers = new ArrayList<>();
            return new League(teamPlayerMap, availablePlayers);
        }
    }


}


