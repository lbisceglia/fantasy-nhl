package models;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import exceptions.InvalidTeamException;
import interfaces.Saveable;

import java.io.*;
import java.lang.reflect.Type;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static managers.ReadWebPage.savePath;

// TODO: Get it to implement loadable again (or move all that functionality over to the UI)
public class League implements Serializable, Saveable {
    public static final int MIN_PARTICIPANTS = 4;
    public static final int MAX_PARTICIPANTS = 4;
    private List<Team> teams;
    private Set<Player> availablePlayers;
//    private FantasyManager fantasyManager;

    // EFFECTS: Constructs a league with the list of available players
    public League(Set<Player> availablePlayers) {
        teams = new ArrayList<>();
        this.availablePlayers = availablePlayers;
    }


    // EFFECTS: Returns a list of teams in this league
    public List<Team> getTeams() {
        return teams;
    }


    // EFFECTS: Returns a list of available players in this league
    public Set<Player> getAvailablePlayers() {
        return availablePlayers;
    }


//    // EFFECTS: Returns the league's fantasy manager
//    public FantasyManager getFantasyManager() {
//        return fantasyManager;
//    }


    // MODIFIES: this
    // EFFECTS: Adds a new team to the league if it is valid
    //          Throws an InvalidTeamException if not valid
    public void addTeam(Team team) throws InvalidTeamException {
        checkIfTeamIsUnique(team);
        teams.add(team);
    }


    // MODIFIES: this
    // EFFECTS: Removes the team from the fantasy league
    //          Adds the team's players back to the available player pool
    public void removeTeam(Team team) {
        if (teams.contains(team)) {
            for (Player p : team.getPlayers()) {
                p.removeObserver(team);
            }
            teams.remove(team);
        }
    }


    // EFFECTS: Does nothing if the team is unique
    //          Throws an InvalidTeamException if the given team name is not unique in the league
    public void checkIfTeamIsUnique(Team team) throws InvalidTeamException {
        if (!teams.contains(team)) {
            // do nothing
        } else {
            throw new InvalidTeamException("Sorry, a team with that name already exists.");
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
//            ArrayList<player> availablePlayers2 = (ArrayList<player>) oi.readObject();
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
//            Type setType = new TypeToken<league>() {
//            }.getType();
//            league restoredLeague = gson.fromJson(jsonReader, league.class);
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
            System.out.println("Error initializing league." + "\n" + "Creating new league!");

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

            System.out.println("player initialization complete!");

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


