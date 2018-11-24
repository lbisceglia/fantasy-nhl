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

public class FantasyManager implements Serializable {
    private static final String savePath = "C:/Users/Lorenzo Bisceglia/Google Drive/1 - School/1 - BCS/CPSC 210/Project/projectw1_team29/src";
    private static final double POINTS_PER_GOAL = 6;
    private static final double POINTS_PER_ASSIST = 4;
    private static final double POINTS_PER_SAVE = 0.6;
    private static final double POINTS_PER_WIN = 5;
    public static final int FANTASY_WEEKS = 24;
    public static final String CURRENT_SEASON = "20182019";

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
    public int currentWeek() {
        return currentFantasyWeek;
    }

    public League getLeague() {
        return league;
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

    public int size() {
        return league.getTeams().size();
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

    public void checkFantasyWeekHasFullyElapsed(int week) throws InvalidFantasyWeekException {
        int nextWeek = week++;
        checkFantasyWeekInRange(nextWeek);
        LocalDate endDate = getWeekCutoffs().get(nextWeek);
        LocalDate today = LocalDate.now();
        if (!today.isAfter(endDate)) {
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


    public List<Team> selectDraftOrder() throws ImpossibleDraftException {
        return draftManager.createDraftList();
    }


    //TODO: get initializeWeekCutoffs to parse LocalDates more directly
    public static List<LocalDate> initializeWeekCutoffs() {
        try (Reader reader = new java.io.FileReader(savePath + "/fantasyWeekCutoffs.json")) {
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


    public void addPlayerStats(Player p, Team t) throws IOException {
        BufferedReader br = null;

        final String baseURL1 = "https://statsapi.web.nhl.com/api/v1/people/";
        final String baseURL2 = "/?hydrate=stats(splits=gameLog)&season=";

        try {
            String pID = Integer.toString(p.getPlayerID());

            String theURL = baseURL1 + pID + baseURL2 + CURRENT_SEASON;
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
            JsonObject jo = jp.parse(json).getAsJsonObject();

            JsonObject people = jo.get("people").getAsJsonArray().get(0).getAsJsonObject();
            JsonArray stats = people.get("stats").getAsJsonArray();
            JsonArray games = stats.get(0).getAsJsonObject().get("splits").getAsJsonArray();

            for (int i = 0; i < games.size(); i++) {
                JsonObject game = games.get(i).getAsJsonObject();
                LocalDate date = LocalDate.parse(game.get("date").getAsString());
                JsonObject stat = game.get("stat").getAsJsonObject();
                String gameID = game.get("game").getAsJsonObject().get("gamePk").getAsString();
                addStats(gameID, p, date, stat);
            }
        } finally {
            if (br != null) {
                br.close();
            }
        }

    }


    private void addStats(String gameID, Player player, LocalDate date, JsonObject object) {
        int gID = Integer.valueOf(gameID);
        int week = calculateFantasyWeek(date);
        if (player.getPosition().equals(Player.Position.G)) {
            addGoalieStats(gID, player, week, object);
        } else {
            addSkaterStats(gID, player, week, object);
        }
    }

    private void addGoalieStats(int gameID, Player player, int week, JsonObject object) {
        int saveCount = object.get("saves").getAsInt();
        if (saveCount > 0) {
            new Stat(gameID, week, player, saves, saveCount);
        }
        if (object.has("decision") && object.get("decision").getAsString().equals("W")) {
            new Stat(gameID, week, player, wins, 1);
        }
    }

    private void addSkaterStats(int gameID, Player player, int week, JsonObject object) {
//        JsonObject skaterStats = object.get("skaterStats").getAsJsonObject();
//        int goalCount = skaterStats.get("goals").getAsInt();
//        int assistCount = skaterStats.get("assists").getAsInt();
        int goalCount = object.get("goals").getAsInt();
        int assistCount = object.get("assists").getAsInt();
        if (goalCount > 0) {
            new Stat(gameID, week, player, goals, goalCount);
        }
        if (assistCount > 0) {
            new Stat(gameID, week, player, assists, assistCount);
        }
    }

    private int calculateFantasyWeek(LocalDate date) {
        int i = 0;
        for (LocalDate d : weekCutoffs) {
            if (date.isAfter(d)) {
                i++;
            }
        }
        return i;
    }
}

