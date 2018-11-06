package model.League;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import model.Loadable;
import model.Player.Player;
import model.Saveable;
import model.Team.Team;

import java.io.*;
import java.util.*;

public class League implements Loadable, Saveable, Serializable {
    private static final String savePath = "C:/Users/Lorenzo Bisceglia/Google Drive/1 - School/1 - BCS/CPSC 210/Project/projectw1_team29/src";

    private final UUID leagueid;
    private Set<Team> teams;
    private Set<Player> availablePlayers;

    // EFFECTS: Constructs a league with the availableSkaters and availableGoalies
    public League(Set<Player> availablePlayers) {
        leagueid = UUID.randomUUID();
        teams = new HashSet<>();
        this.availablePlayers = availablePlayers;
    }


    public UUID getLeagueid() {
        return leagueid;
    }

    // EFFECTS: Returns a list of teams in this league
    public Set<Team> getTeams() {
        return teams;
    }

    // EFFECTS: Returns a list of available players in this league
    public Set<Player> getAvailablePlayers() {
        return availablePlayers;
    }


    // MODIFIES: this
    // EFFECTS: Creates a new Team adds it to the league if the name isn't already taken
    //          Does nothing if name is already taken
    public void addTeam(Team team) {
        if (!containsTeam(team)) {
            teams.add(team);
            team.setLeague(this);
        }
    }

    // MODIFIES: this
    // EFFECTS: removes Team from the fantasy league, adds its Players back to the availablePlayers list
    public void removeTeam(Team team) {
        if (containsTeam(team)) {
            Set<Player> players = team.getPlayers();
            for (Player p : players) {
                players.remove(p);
                availablePlayers.add(p);
            }
            teams.remove(team);
        }
    }


    public boolean containsTeam(Team team) {
        return teams.contains(team);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        League league = (League) o;
        return Objects.equals(leagueid, league.leagueid);
    }

    @Override
    public int hashCode() {

        return Objects.hash(leagueid);
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
            ArrayList<Player> availablePlayers2 = (ArrayList<Player>) oi.readObject();
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
            Set<Player> availablePlayers2 = new HashSet<>();
            return new League(availablePlayers2);
        } catch (IOException e) {
            System.out.println("Error initializing stream");
            e.printStackTrace();

            Map<Team, ArrayList<Player>> teamPlayerMap = new HashMap<>();
            Set<Player> availablePlayers2 = new HashSet<>();
            return new League(availablePlayers2);
        }
    }

}


