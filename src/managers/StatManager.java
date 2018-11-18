package managers;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import models.Player;
import models.Stat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.URL;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class StatManager  implements Serializable {

    private final Player player;
    private Set<Stat> stats;

    public StatManager(Player player) {
        this.player = player;
        stats = new HashSet<>();
    }

    public Set<Stat> getStats() {
        return stats;
    }

    public Player getPlayer() {
        return player;
    }

//    public boolean isPlayerOfInterest(player player) {
//        return this.player.equals(player);
//    }
//
//
//    public double getOverallFantasyPoints() {
//        double totalPoints = 0;
//        for(Stat stat : stats) {
//            totalPoints += stat.getOverallFantasyPoints();
//        }
//        return totalPoints;
//    }
//
//    public void addStatsForMultipleGames() {
//        String baseGameID = "201702";
//        for (int i = 1; i <= 50; i++) {
//            String suffix = Integer.toString(i);
//            String gameID = baseGameID + suffix;
//
//        }
//    }

    // REQUIRES: Requires a list of valid Game Primary Keys from NHL.com
    // EFFECTS:
    public void addGameStats(List<Integer> gameIDs) throws IOException {

        BufferedReader br = null;

        try {
            for (Integer gameID : gameIDs) {

                final String baseURL1 = "https://statsapi.web.nhl.com/api/v1/game/";
                final String game = Integer.toString(gameID);
                final String baseURL2 = "/boxscore";
                String theURL = baseURL1 + game + baseURL2;

                URL url = new URL(theURL);
                br = new BufferedReader(new InputStreamReader(url.openStream()));

                String line;

                StringBuilder sb = new StringBuilder();

                while ((line = br.readLine()) != null) {

                    sb.append(line);
                    sb.append(System.lineSeparator());
                }

                final String json = sb.toString();
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

                    for (Object o : awayRoster) {
                        if (o.equals(playerID)) {
                            JsonObject player = awayPlayers.get("ID" + playerID).getAsJsonObject();
                            JsonObject stats = player.get("stats").getAsJsonObject();
                            addStats(stats);
                            break;
                        }
                    }

                    for (Object o : homeRoster) {
                        if (o.toString().equals(playerID)) {
                            JsonObject player = homePlayers.get("ID" + playerID).getAsJsonObject();
                            JsonObject stats = player.get("stats").getAsJsonObject();
                            addStats(stats);
                            break;
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

    private void addStats(JsonObject object) {
        if(player.getPosition().equals(Player.Position.G)) {
            addGoalieStats(object);
        } else {
            addSkaterStats(object);
        }
    }

    private void addGoalieStats(JsonObject object) {
        JsonObject goalieStats = object.get("goalieStats").getAsJsonObject();
        int saveCount = goalieStats.get("saves").getAsInt();
        int winCount;
        if(goalieStats.get("decision").getAsString().equals("W")){
            winCount = 1;
        } else {
            winCount = 0;
        }
        LocalDate date = LocalDate.of(2017, 02, 01);
//        Stat saveSummary = new Stat(2017021244, player, saves, saveCount);
//        Stat winSummary = new Stat(2017021244, player, wins, winCount);
//        stats.add(saveSummary);
//        stats.add(winSummary);
    }

    private void addSkaterStats(JsonObject object) {
        JsonObject skaterStats = object.get("skaterStats").getAsJsonObject();
        int goalCount = skaterStats.get("goals").getAsInt();
        int assistCount = skaterStats.get("assists").getAsInt();
        LocalDate date = LocalDate.of(2017, 02, 01);
//        Stat goalSummary = new Stat(2017021244, player, goals, goalCount);
//        Stat assistSummary = new Stat(2017021244, player, assists, assistCount);
//        stats.add(goalSummary);
//        stats.add(assistSummary);
    }





    //    // EFFECTS: Returns the skater's total goals scored in the active season
//    public int getTotalGoals() {
//        return this.totalGoals;
//    }
//
//    // EFFECTS: Returns the skater's total assists scored in the active season
//    public int getTotalAssists() {
//        return this.totalAssists;
//    }
//
//    // MODIFIES: this
//    // EFFECTS: Sets the skater's total goals scored in the active season if the number is non-negative
//    public void setTotalGoals(int goals) throws InvalidStatException {
//        String statType = "Goals";
//        if(isNaturalNumberStat(statType, goals)) {
//            totalGoals = goals;
//        }
//    }
//
//    // MODIFIES: this
//    // EFFECTS: Sets the skater's total assists scored in the active season if the number is non-negative
//    public void setTotalAssists(int assists) throws InvalidStatException {
//        String statType = "Assists";
//        if(isNaturalNumberStat(statType, assists)) {
//            totalAssists = assists;
//        }
//    }


    //      // EFFECTS: Return the Goalie's save percentage in the active season
//    public double getSavePercentage() {
//        return this.savePercentage;
//    }
//
//    // EFFECTS: Return the Goalie's goals against average in the active season
//    public double getGoalsAgainstAverage() {
//        return this.goalsAgainstAverage;
//    }
//
//    // setters
//
//    // MODIFIES: this
//    // EFFECTS: Sets save percentage in the active season if the number is a valid percentage
//    public void setSavePercentage(double svP) throws InvalidStatException {
//        String statType = "Save Percentage";
//        if (isValidPercentageStat(statType, svP)) {
//            this.savePercentage = svP;
//        }
//    }
//
//    // MODIFIES: this
//    // EFFECTS: Sets goals against average for the active season if the number is non-negative
//    public void setGoalsAgainstAverage(double gAA) throws InvalidStatException {
//        String statType = "Goals Against Average";
//        if (isNonNegativeStat(statType, gAA)) {
//            this.goalsAgainstAverage = gAA;
//        }
//    }




}
