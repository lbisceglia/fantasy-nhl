package model.League;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import model.Loadable;
import model.Player.Goalie;
import model.Player.Player;
import model.Player.Skater;
import model.Saveable;
import model.Team.GoalieList;
import model.Team.SkaterList;
import model.Team.Team;
import model.exceptions.DuplicateMatchException;
import model.exceptions.NoMatchException;

import java.io.*;
import java.util.ArrayList;

public class League implements TeamList, Loadable, Saveable, Serializable {
    private static final String savePath = "C:/Users/Lorenzo Bisceglia/Google Drive/1 - School/1 - BCS/CPSC 210/Project/projectw1_team29/src";

    ArrayList<Team> participants;
    //TODO: Change these back to SkaterList, GoalieList when I figure out the Json
    SkaterList availableSkaters;
    GoalieList availableGoalies;

    // EFFECTS: Constructs a league with the availableSkaters and availableGoalies
    // TODO: Change parameter types back to SkaterList, GoalieList when I figure out the Json
    public League(SkaterList availableSkaters, GoalieList availableGoalies) {
        participants = new ArrayList();
        this.availableSkaters = availableSkaters;
        this.availableGoalies = availableGoalies;
    }

    @Override
    // EFFECTS: Returns a list of teams in this league
    public ArrayList<Team> getTeams() {
        return participants;
    }

    // EFFECTS: Returns a list of team names in this league
    @Override
    public ArrayList<String> getTeamNames() {
        ArrayList<String> participants = new ArrayList<>();
        for (Team t : this.participants) {
            String team = t.getTeamName();
            participants.add(team);
        }
        return participants;
    }

    // EFFECTS: Returns a list of the skaters available in this league
    public ArrayList<Skater> getAvailableSkaters() {
        return availableSkaters.getSkaters();
    }

    // EFFECTS: Returns a list of the skaters available in this league
    public ArrayList<Goalie> getAvailableGoalies() {
        return availableGoalies.getGoalies();
    }

    // EFFECTS: Returns a list of the players available in this league
    public ArrayList<Player> getAvailablePlayers() {
        ArrayList<Player> availablePlayers = new ArrayList<>();
        availablePlayers.addAll(getAvailableSkaters());
        availablePlayers.addAll(getAvailableGoalies());
        return availablePlayers;
    }

    // EFFECTS: Returns a list of the players names available in this league
    public ArrayList<String> getAvailablePlayerNames() {
        ArrayList<String> availablePlayerNames = new ArrayList<>();
        availablePlayerNames.addAll(getAvailableSkaterNames());
        availablePlayerNames.addAll(getAvailableGoalieNames());
        return availablePlayerNames;
    }

    // TODO: write tests for this method
    // EFFECTS: Returns a list of the players names available in this league
    public ArrayList<String> getAvailableSkaterNames() {
        ArrayList<String> availableSkaterNames = new ArrayList<>();
        for (Skater s : availableSkaters.getSkaters()) {
            availableSkaterNames.add(s.getPlayerName());
        }
        return availableSkaterNames;
    }

    // EFFECTS: Returns a list of the players names available in this league
    public ArrayList<String> getAvailableGoalieNames() {
        ArrayList<String> availableGoalieNames = new ArrayList<>();
        for (Goalie g : availableGoalies.getGoalies()) {
            availableGoalieNames.add(g.getPlayerName());
        }
        return availableGoalieNames;
    }


    @Override
    // EFFECTS: Returns true if the team already exists in this league
    //          Returns false if the team does not already exist in this league
    public boolean containsTeam(Team team) {
        return participants.contains(team);
    }

    @Override
    // EFFECTS: Returns true if the team name already exists in this league
    //          Returns false if the name is unqiue in this league
    public boolean containsTeamName(String teamName) {
        for (Team t : this.participants) {
            if (teamName.equals(t.getTeamName())) {
                return true;
            }
        }
        return false;
    }


    // EFFECTS: Returns true if the player name matches an available player in this league
    //          Returns false otherwise
    public boolean containsAvailablePlayerName(String playerName) {
        return (availableSkaters.containsPlayerName(playerName) ||
                availableGoalies.containsPlayerName(playerName));
    }


    @Override
    // MODIFIES: this
    // EFFECTS: Creates a new Team adds it to the league if the name isn't already taken
    //          Does nothing if name is already taken
    public void addTeam(String teamName) throws DuplicateMatchException {
        boolean b = this.containsTeamName(teamName);
        if (!b) {
            Team team = new Team(teamName);
            participants.add(team);
        } else {
            throw new DuplicateMatchException();
        }
    }


    // MODIFIES: this, TODO: ADD WHAT ELSE IT MODIFIES
    // EFFECTS: Adds a player to a Team and removes them from the League available players list
    public void addPlayerToFantasyTeam(String teamName, String playerName) throws NoMatchException {
        if (this.containsTeamName(teamName)) {
            if (this.containsAvailablePlayerName(playerName)) {
                // The player name is valid and player added to the team, removed from available players
                if (availableGoalies.containsPlayerName(playerName)) {
                    this.teamLookup(teamName).addGoalie(availableGoalies.goalieLookup(playerName));
                    this.removeAvailableGoalie(playerName);
                } else {
                    this.teamLookup(teamName).addSkater(availableSkaters.skaterLookup(playerName));
                    this.removeAvailableSkater(playerName);
                }
            } else {
                throw new NoMatchException();
            }
        }
    }

    @Override
    // EFFECTS: Returns the team with the corresponding team name
    public Team teamLookup(String teamName) {
        for (Team t : this.participants) {
            if (t.getTeamName().equals(teamName)) {
                return t;
            }
        }
        return null;
    }


    // MODIFIES: this
    // EFFECTS: Removes skater from the list if they are on the list
    //          Does nothing if skater is not on the list
    private void removeAvailableSkater(String playerName) {
        if (this.containsAvailablePlayerName(playerName)) {
            availableSkaters.removeSkater(availableSkaters.skaterLookup(playerName));
        }
    }

    // MODIFIES: this
    // EFFECTS: Removes goalie from the list if they are on the list
    //          Does nothing if goalie is not on the list
    private void removeAvailableGoalie(String playerName) {
        if (this.containsAvailablePlayerName(playerName)) {
            availableGoalies.removeGoalie(availableGoalies.goalieLookup(playerName));
        }
    }

    @Override
    // MODIFIES: this
    // EFFECTS: removes team from the fantasy league, removes its players
    //          adds its skaters back to the available skaters, goalies back to available goalies
    public void removeTeam(Team team) {
        participants.remove(team);
        for (Skater s : team.getSkaters()) {
            team.removeSkater(s);
            availableSkaters.addSkater(s);
        }
        for (Goalie g : team.getGoalies()) {
            team.removeGoalie(g);
        }
        participants.remove(team);
    }

    // EFFECTS: Prints the team name and roster for every team in the league
    public void printTeamsAndPlayers() {
        for (Team t : participants) {
            t.printTeamAndPlayers();
        }
    }


    @Override
    //Modeled after Object Stream tutorial, 2018-10-01 [https://www.mkyong.com/java/how-to-read-and-write-java-object-to-a-file/]
    // TODO: add specification and tests for this method
    public void save() {
        try {
            FileOutputStream f = new FileOutputStream(new File("fantasyLeague.txt"));
            ObjectOutputStream o = new ObjectOutputStream(f);

            // Write objects to file
            o.writeObject(this.participants);
            o.writeObject(this.availableSkaters);
            o.writeObject(this.availableGoalies);


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
            ArrayList<Team> participants1 = (ArrayList<Team>) oi.readObject();
            //TODO: Change back to SkaterList, GoalieList when Json Figured out
            SkaterList availableSkaters1 = (Team) oi.readObject();
            GoalieList availableGoalies1 = (Team) oi.readObject();

            this.participants = participants1;
            this.availableSkaters = availableSkaters1;
            this.availableGoalies = availableGoalies1;

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

    public static League loadLeague() {
        try (Reader reader = new FileReader(savePath + "/league.json")) {
            JsonReader jsonReader = new JsonReader(reader);
            GsonBuilder gsonBuilder = new GsonBuilder();
            Gson gson = gsonBuilder.create();

            League restoredLeague = gson.fromJson(jsonReader, League.class);

            return restoredLeague;
        } catch (FileNotFoundException e) {
            SkaterList availableSkaters = new Team("Available Players");
            GoalieList availableGoalies = new Team("Available Goalies");
            return new League(availableSkaters, availableGoalies);
        } catch (IOException e) {
            System.out.println("Error initializing stream");
            e.printStackTrace();
            SkaterList availableSkaters = new Team("Available Players");
            GoalieList availableGoalies = new Team("Available Goalies");
            return new League(availableSkaters, availableGoalies);
        }
    }
}


