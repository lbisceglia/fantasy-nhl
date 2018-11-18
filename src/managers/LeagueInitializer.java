package managers;


import abstractions.FileParser;
import abstractions.WebPageParser;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import models.League;
import models.Player;

import java.io.*;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class LeagueInitializer extends FileParser {
    private WebPageParser webPageParser;

    public LeagueInitializer(League league) {
        webPageParser = new WebPageParser();
        loadAvailablePlayers(league);
    }

    // EFFECTS: loads a list of popular fantasy NHL Players from NHL API
    public void loadAvailablePlayers(League league) {
        try (Reader reader = new java.io.FileReader(savePath + "/availablePlayers.json")) {
            JsonReader jsonReader = new JsonReader(reader);
            Gson gson = new Gson();

            Type setType = new TypeToken<List<Integer>>() {
            }.getType();

            List<Integer> playerIDs = gson.fromJson(jsonReader, setType);

            Set<Player> availablePlayers = new HashSet<>();

            System.out.println("Initializing available players...");

            int i =0;
            for (int id : playerIDs) {
                try {
                    Player p = loadAvailablePlayer(id);
                    availablePlayers.add(p);
                    i++;
                } catch (IOException e) {
                    System.out.println("The player you're trying to add is unavailable.");
                }
            }

            System.out.println("\n" + "Player initialization complete!" +
                    "\n" + i + " players were added to the player pool.");

            league.setAvailablePlayers(availablePlayers);

        } catch (FileNotFoundException e) {
            System.out.println("Available players pool not found.");
            Set<Player> availablePlayers = new HashSet<>();
            league.setAvailablePlayers(availablePlayers);

        } catch (IOException e) {
            System.out.println("Error initializing players.");

            Set<Player> availablePlayers = new HashSet<>();
            league.setAvailablePlayers(availablePlayers);
        }
    }

    // Modelled after the P10 tutorial on edX
    // EFFECTS: Returns a list of
    public Player loadAvailablePlayer(int playerID) throws IOException {

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



}
