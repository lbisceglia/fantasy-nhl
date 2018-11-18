package managers;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import exceptions.ImpossibleDraftException;
import exceptions.InvalidFantasyWeekException;
import models.League;
import models.Player;
import models.Stat;
import models.Team;

import java.io.*;
import java.lang.reflect.Type;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static managers.DraftManager.DraftType.Regular;
import static models.Stat.StatType.*;

public class FantasyManager {
    private static final String savePath = "C:/Users/Lorenzo Bisceglia/Google Drive/1 - School/1 - BCS/CPSC 210/Project/projectw1_team29/src";
    private static final double POINTS_PER_GOAL = 6;
    private static final double POINTS_PER_ASSIST = 4;
    private static final double POINTS_PER_SAVE = 0.6;
    private static final double POINTS_PER_WIN = 5;
    public static final int FANTASY_WEEKS = 24;

    private static final List<LocalDate> weekCutoffs = initializeWeekCutoffs();
    private boolean drafted;

    private DraftManager draftManager;
    private League league;
    private int currentFantasyWeek;

    // EFFECTS: Constructs a Fantasy Manager with the given league starting at week 0
    //          No players have been drafted yet
    public FantasyManager(League league) {
        this.league = league;
        currentFantasyWeek = 0;
        draftManager = new DraftManager(league.getTeams(), league.getAvailablePlayers(), Regular);
        drafted = false;
    }


    //EFFECTS: Returns the current fantasy week
    public int getCurrentFantasyWeek() {
        return currentFantasyWeek;
    }

    public List<LocalDate> getWeekCutoffs() {
        return weekCutoffs;
    }

    public DraftManager getDraftManager() {
        return draftManager;
    }

    public boolean isDrafted() {
        return drafted;
    }

    public void setDrafted() {
        drafted = true;
    }

    // EFFECTS: Sets current fantasy week to the given week if it valid
    public void setCurrentFantasyWeek(int week) throws InvalidFantasyWeekException {
        checkValidFantasyWeek(week);
        this.currentFantasyWeek = week;
    }

    private void checkValidFantasyWeek(int week) throws InvalidFantasyWeekException {
        checkFantasyWeekInRange(week);
        checkScheduleNotMovingBackwards(week);
    }

    // EFFECTS: Checks that the given fantasy week is valid
    private void checkFantasyWeekInRange(int week) throws InvalidFantasyWeekException {
        if (1 > week || week > FANTASY_WEEKS) {
            throw new InvalidFantasyWeekException();
        }
    }

    private void checkScheduleNotMovingBackwards(int week) throws InvalidFantasyWeekException {
        if (week < currentFantasyWeek) {
            throw new InvalidFantasyWeekException();
        }
    }

    public static void calculateFantasyPoints(Stat stat) {
        Stat.StatType statType = stat.getStatType();
        double fantasyPoints = 0;
        double amount = stat.getAmount();

        if (statType.equals(goals)) {
            fantasyPoints += amount * POINTS_PER_GOAL;
        } else if (statType.equals(assists)) {
            fantasyPoints += amount * POINTS_PER_ASSIST;
        } else if (statType.equals(saves)) {
            fantasyPoints += amount * POINTS_PER_SAVE;
        } else if (statType.equals(wins)) {
            fantasyPoints += amount * POINTS_PER_WIN;
        }
        stat.setFantasyPoints(fantasyPoints);
    }

    // EFFECTS: advances the fantasy week by one week if not in the final week
    public void advanceFantasyWeekByOne() throws InvalidFantasyWeekException {
        int nextWeek = ++currentFantasyWeek;
        setCurrentFantasyWeek(nextWeek);
    }


//    private void beginFantasyCompetition() throws ImpossibleDraftException {
//        int size = league.getTeams().size();
//        if (MIN_PARTICIPANTS <= size && size <= MAX_PARTICIPANTS) {
//            try {
//                draftManager.createDraftList();
//                setCurrentFantasyWeek(1);
//            } catch (InvalidFantasyWeekException e) {
//                System.out.println("Unable to set the fantasy week to 1");
//            }
//        }
//    }

    public List<Team> selectDraftOrder() throws ImpossibleDraftException {
        return draftManager.createDraftList();
    }


    //TODO: get initializeWeekCutoffs to parse LocalDates more directly
    public static List<LocalDate> initializeWeekCutoffs() {
        try (Reader reader = new FileReader(savePath + "/fantasyWeekCutoffs.json")) {
            JsonReader jsonReader = new JsonReader(reader);
            Gson gson = new GsonBuilder()
                    .setDateFormat("yyyy-MM-dd")
                    .create();

            Type setType = new TypeToken<List<String>>() {
            }.getType();

            // TODO: Fix how LocalDates get parsed from JSON file
            List<String> fantasyWeekStrings = gson.fromJson(jsonReader, setType);

            List<LocalDate> fantasyWeekCutoffs = new ArrayList<>();

            for (String s : fantasyWeekStrings) {
                LocalDate localDate = LocalDate.parse(s);
                fantasyWeekCutoffs.add(localDate);
            }

            return fantasyWeekCutoffs;

        } catch (FileNotFoundException e) {
            System.out.println("Fantasy dates not found.");
            e.printStackTrace();

        } catch (IOException e) {
            System.out.println("Error initializing fantasy dates.");
            e.printStackTrace();
        }
        return null;
    }

    public List<String> getGameIDs(LocalDate startDate, LocalDate endDate) throws IOException {
        BufferedReader br = null;

        ArrayList<String> gameIDs = new ArrayList<>();

        try {
            final String baseURL1 = "https://statsapi.web.nhl.com/api/v1/schedule";
            final String dateRange = "?startDate=" + startDate.toString() + "&endDate=" + endDate.toString();
            String theURL = baseURL1 + dateRange;

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

            for (int i = 0; i < dates.size(); i++) {
                JsonObject date = dates.get(i).getAsJsonObject();
                JsonArray games = date.get("games").getAsJsonArray();

                for (int j = 0; j < games.size(); j++) {
                    JsonObject game = games.get(j).getAsJsonObject();
                    String gamePk = game.get("gamePk").getAsString();
                    gameIDs.add(gamePk);
                }
            }

        } finally {
            if (br != null) {
                br.close();
            }

        }
        return gameIDs;
    }

    public List<Player> getAllActivePlayers() {
        List<Player> allPlayers = new ArrayList<>();
        List<Team> teams = league.getTeams();
        for (Team t : teams) {
            allPlayers.addAll(t.getPlayers());
        }
        return allPlayers;
    }

    // REQUIRES: Requires a list of valid Game Primary Keys from NHL.com
    // EFFECTS:
    public void addGameStats(List<String> gameIDs) throws IOException {

        BufferedReader br = null;

        try {
            for (String gameID : gameIDs) {

                final String baseURL1 = "https://statsapi.web.nhl.com/api/v1/game/";
                final String baseURL2 = "/boxscore";
                String theURL = baseURL1 + gameID + baseURL2;

                URL url = new URL(theURL);
                br = new BufferedReader(new InputStreamReader(url.openStream()));

                String line;

                StringBuilder sb = new StringBuilder();

                while ((line = br.readLine()) != null) {

                    sb.append(line);
                    sb.append(System.lineSeparator());
                }

                final String json = sb.toString();

                for (Player player : getAllActivePlayers()) {
                    String playerID = Integer.toString(player.getPlayerID());

                    if (json.toLowerCase().contains(playerID.toLowerCase())) {

                        JsonParser jp = new JsonParser();
                        JsonElement je = jp.parse(json);
                        JsonObject jo = je.getAsJsonObject();
                        JsonObject teams = jo.get("teams").getAsJsonObject();
                        JsonObject awayTeam = teams.get("away").getAsJsonObject();
                        JsonObject homeTeam = teams.get("home").getAsJsonObject();
                        JsonObject awayPlayers = awayTeam.get("players").getAsJsonObject();
                        JsonObject homePlayers = homeTeam.get("players").getAsJsonObject();

                        JsonArray awaySkaters = awayTeam.get("skaters").getAsJsonArray();
                        JsonArray awayGoalies = awayTeam.get("goalies").getAsJsonArray();
                        JsonArray awayRoster = new JsonArray();
                        awayRoster.addAll(awaySkaters);
                        awayRoster.addAll(awayGoalies);

                        JsonArray homeSkaters = homeTeam.get("skaters").getAsJsonArray();
                        JsonArray homeGoalies = homeTeam.get("goalies").getAsJsonArray();
                        JsonArray homeRoster = new JsonArray();
                        homeRoster.addAll(homeSkaters);
                        homeRoster.addAll(homeGoalies);

                        for (int i = 0; i < awayRoster.size(); i++) {
                            String pID = awayRoster.get(i).getAsString();

                            if (pID.equals(playerID)) {
                                JsonObject p = awayPlayers.get("ID" + playerID).getAsJsonObject();
                                JsonObject stats = p.get("stats").getAsJsonObject();
                                if (stats.has("goalieStats") || stats.has("skaterStats")) {
                                    addStats(gameID, player, stats);
                                    break;
                                }
                            }
                        }

                        for (int i = 0; i < homeRoster.size(); i++) {
                            String pID = homeRoster.get(i).getAsString();

                            if (pID.equals(playerID)) {
                                JsonObject p = homePlayers.get("ID" + playerID).getAsJsonObject();
                                JsonObject stats = p.get("stats").getAsJsonObject();
                                if (stats.has("goalieStats") || stats.has("skaterStats")) {
                                    addStats(gameID, player, stats);
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        } finally {
            if (br != null) {
                br.close();
            }
        }
    }

    private void addStats(String gameID, Player player, JsonObject object) {
        int gID = Integer.valueOf(gameID);
        if (player.getPosition().equals(Player.Position.G)) {
            addGoalieStats(gID, player, object);
        } else {
            addSkaterStats(gID, player, object);
        }
    }

    private void addGoalieStats(int gameID, Player player, JsonObject object) {
        JsonObject goalieStats = object.get("goalieStats").getAsJsonObject();
        int saveCount = goalieStats.get("saves").getAsInt();
        if (saveCount > 0) {
            new Stat(gameID, getCurrentFantasyWeek(), player, saves, saveCount);
        }
        if (goalieStats.get("decision").getAsString().equals("W")) {
            new Stat(gameID, getCurrentFantasyWeek(), player, wins, 1);
        }
    }

    private void addSkaterStats(int gameID, Player player, JsonObject object) {
        JsonObject skaterStats = object.get("skaterStats").getAsJsonObject();
        int goalCount = skaterStats.get("goals").getAsInt();
        int assistCount = skaterStats.get("assists").getAsInt();
        if (goalCount > 0) {
            new Stat(gameID, getCurrentFantasyWeek(), player, goals, goalCount);
        }
        if (assistCount > 0) {
            new Stat(gameID, getCurrentFantasyWeek(), player, assists, assistCount);
        }
    }
}
