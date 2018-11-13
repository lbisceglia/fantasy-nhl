package model.League;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import model.Player.Player;
import model.Saveable;
import model.Team.Team;
import model.Team.TeamManager;

import java.io.*;
import java.lang.reflect.Type;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class League implements Saveable, Serializable {
    private static final String savePath = "C:/Users/Lorenzo Bisceglia/Google Drive/1 - School/1 - BCS/CPSC 210/Project/projectw1_team29/src";

    private final UUID leagueID;
    private TeamManager teamManager;
    private Set<Player> availablePlayers;

    // EFFECTS: Constructs a league with the list of available players
    public League(Set<Player> availablePlayers) {
        leagueID = UUID.randomUUID();
        teamManager = new TeamManager(this);
        this.availablePlayers = availablePlayers;
    }

    // EFFECTS: Returns the league the the team1 is located in
    public UUID getLeagueID() {
        return leagueID;
    }

    // EFFECTS: Returns a list of teams in this league
    public Set<Team> getTeams() {
        return teamManager.getTeams();
    }

    // EFFECTS: Returns a list of available players in this league
    public Set<Player> getAvailablePlayers() {
        return availablePlayers;
    }

    // EFFECTS: Returns the lookup table of team1 and players in the league
    public TeamManager getTeamManager() {
        return teamManager;
    }

    // MODIFIES: this
    // EFFECTS: Creates a new Team with the given name and adds it to the league if the name isn't already taken
    //          Does nothing if name is already taken
    public void addTeam(Team team) {
        teamManager.addTeam(team);
    }

    // MODIFIES: this
    // EFFECTS: removes Team from the fantasy league and adds its Players back to the availablePlayers list
    public void removeTeam(Team team) {
        teamManager.removeTeam(team);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        League league = (League) o;
        return Objects.equals(leagueID, league.leagueID);
    }

    @Override
    public int hashCode() {

        return Objects.hash(leagueID);
    }

    @Override
    //Modeled after Object Stream tutorial, 2018-10-01 [https://www.mkyong.com/java/how-to-read-and-write-java-object-to-a-file/]
    // TODO: add specification and tests for this method
    public void save() {
        try {
            FileOutputStream f = new FileOutputStream(new File("fantasyLeague.txt"));
            ObjectOutputStream o = new ObjectOutputStream(f);

            // Write objects to file
            o.writeObject(this);

            o.close();
            f.close();

        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error initializing stream");
        }
    }

//    @Override
//    //Modeled after Object Stream tutorial, 2018-10-01 [https://www.mkyong.com/java/how-to-read-and-write-java-object-to-a-file/]
//    // TODO: add specification and tests for this method
//    public void load() {
//        try {
//            FileInputStream fi = new FileInputStream(new File("fantasyLeague.txt"));
//            ObjectInputStream oi = new ObjectInputStream(fi);
//
//            // Read objects
//            ArrayList<Player> availablePlayers2 = (ArrayList<Player>) oi.readObject();
////            this.participants = participants1;
////            this.availableSkaters = availableSkaters1;
////            this.availableGoalies = availableGoalies1;
//
//            oi.close();
//            fi.close();
//
//        } catch (FileNotFoundException e) {
//            System.out.println("File not found");
//        } catch (IOException e) {
//            System.out.println("Error initializing stream");
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }
//    }

    //Modeled after Object Stream tutorial, 2018-10-01 [https://www.mkyong.com/java/how-to-read-and-write-java-object-to-a-file/]
    // TODO: add specification and tests for this method
    public static League loadLeague() {
        try {
//        try (Reader reader = new FileReader(savePath + "/availablePlayerssss.json")) {
//            JsonReader jsonReader = new JsonReader(reader);
//            GsonBuilder gsonBuilder = new GsonBuilder();
//            Gson gson = gsonBuilder.create();
//
//            Type setType = new TypeToken<League>() {
//            }.getType();
//            League restoredLeague = gson.fromJson(jsonReader, League.class);
//            return restoredLeague;

            FileInputStream fi = new FileInputStream(new File("fantasyLeague.txt"));
            ObjectInputStream oi = new ObjectInputStream(fi);

            League restoredLeague = (League) oi.readObject();

            oi.close();
            fi.close();

            return restoredLeague;

        } catch (FileNotFoundException e) {
            System.out.println("No saved league found. Creating a new league...");

            Set<Player> availablePlayers = loadAvailablePlayers();
            return new League(availablePlayers);

        } catch (IOException e) {
            System.out.println("Error initializing league.");
            System.out.println("Creating a new league!");

            Set<Player> availablePlayers = loadAvailablePlayers();
            return new League(availablePlayers);

        } catch (ClassNotFoundException e) {
            System.out.println("Class not found.");
            e.printStackTrace();

            Set<Player> availablePlayers = loadAvailablePlayers();
            return new League(availablePlayers);
        }
    }

    // EFFECTS: loads a list of popular fantasy NHL Players from NHL API
    public static Set<Player> loadAvailablePlayers() {
        try (Reader reader = new FileReader(savePath + "/availablePlayers.json")) {
            JsonReader jsonReader = new JsonReader(reader);
            Gson gson = new Gson();

            Type setType = new TypeToken<List<Integer>>() {
            }.getType();

            List<Integer> playerIDs = gson.fromJson(jsonReader, setType);

            Set<Player> availablePlayers = new HashSet<>();

            System.out.println("Initializing available players...");

            for (int id : playerIDs) {
                try {
                    Player p = loadAvailablePlayer(id);
                    availablePlayers.add(p);
                    System.out.println(p.getPlayerName() + " (" + p.getPosition() + ") is now available.");
                } catch (IOException e) {
                    System.out.println("The player you're trying to add is unavailable.");
                }
            }

            System.out.println("Player initialization complete!");

            return availablePlayers;

        } catch (FileNotFoundException e) {
            System.out.println("Available players pool not found.");
            Set<Player> availablePlayers = new HashSet<>();
            return availablePlayers;

        } catch (IOException e) {
            System.out.println("Error initializing players.");
            e.printStackTrace();

            Set<Player> availablePlayers = new HashSet<>();
            return availablePlayers;
        }
    }

    // Modelled after the P10 tutorial on edX
    // EFFECTS: Returns a list of
    public static Player loadAvailablePlayer(int playerID) throws IOException {

        BufferedReader br = null;

        try {
            String baseURL = "https://statsapi.web.nhl.com/api/v1/people/";
            String suffix = Integer.toString(playerID);
            String theURL = baseURL + suffix;

            URL url = new URL(theURL);
            br = new BufferedReader(new InputStreamReader(url.openStream()));

            String line;

            StringBuilder sb = new StringBuilder();

            while ((line = br.readLine()) != null) {

                sb.append(line);
                sb.append(System.lineSeparator());
            }

            final String json = sb.toString();

            JsonParser jp = new JsonParser();
            JsonElement je = jp.parse(json);
            JsonObject jo = je.getAsJsonObject();
            JsonObject person = jo.get("people").getAsJsonArray().get(0).getAsJsonObject();
            JsonObject primaryPosition = person.get("primaryPosition").getAsJsonObject();

            int id = person.get("id").getAsInt();
            String name = person.get("fullName").getAsString();

            String p = primaryPosition.get("abbreviation").getAsString();
            Player.Position position = Player.Position.valueOf(p);

            Player player = new Player(id, name, position);

            return player;

        } finally {
            if (br != null) {
                br.close();
            }
        }
    }

    // EFFECTS: Returns the games that will take place within the week following the given date
    public static List<Integer> getWeeklyGameIDs(LocalDate startDate, LocalDate endDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        String startString = startDate.format(formatter);
        String endString = endDate.format(formatter);

        BufferedReader br = null;

        try {
            final String baseURL1 = "https://statsapi.web.nhl.com/api/v1/schedule?startDate=";
            final String baseURL2 = "&endDate=";
            String theURL = baseURL1 + startString + baseURL2 + endString;

            URL url = new URL(theURL);
            br = new BufferedReader(new InputStreamReader(url.openStream()));

            String line;

            StringBuilder sb = new StringBuilder();

            while ((line = br.readLine()) != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
            }

            final String json = sb.toString();

            JsonParser jp = new JsonParser();
            JsonElement je = jp.parse(json);
            JsonObject jo = je.getAsJsonObject();
            JsonArray dates = jo.get("dates").getAsJsonArray();

            ArrayList<Integer> gameIDs = new ArrayList<>();

            for (int i = 0; i < dates.size(); i++) {
                JsonObject date = dates.get(i).getAsJsonObject();
                JsonArray games = date.get("games").getAsJsonArray();
                for (int j = 0; j < games.size(); j++) {
                    JsonObject obj = games.get(j).getAsJsonObject();
                    int gameID = obj.get("gamePk").getAsInt();
                    gameIDs.add(gameID);
                }
            }
            return gameIDs;
        } catch (IOException e) {
            System.out.println("Issue retrieving game ID.");
            e.printStackTrace();
        }
        return new ArrayList<>();
    }
}


