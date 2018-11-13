package model;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import model.Stat.GameStat;

import java.io.*;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FantasyWeekManager implements Serializable {
    private static final String savePath = "C:/Users/Lorenzo Bisceglia/Google Drive/1 - School/1 - BCS/CPSC 210/Project/projectw1_team29/src";
    private final static double POINTS_PER_GOAL = 6;
    private final static double POINTS_PER_ASSIST = 4;
    private static final double POINTS_PER_SAVE = 0.6;
    private static final double POINTS_PER_WIN = 5;
    public static final int FANTASY_WEEKS = 24;
//    public static List<LocalDate> weekCutoffs = initializeWeekCutoffs();

    Map<Integer, List<GameStat>> weeklyFantasyPoints;

    // EFFECTS: Creates a FantasyManager with the correct number of Fantasy Weeks
    public FantasyWeekManager() {
        weeklyFantasyPoints = new HashMap<>();
        for (int i = 1; i <= FANTASY_WEEKS; i++) {
            weeklyFantasyPoints.put(i, new ArrayList<>());
        }
    }

    public void addStat(GameStat gameStat) {
        int week = gameStat.getFantasyWeek();

        if (1 <= week && week <= FANTASY_WEEKS) {
            List<GameStat> stats = weeklyFantasyPoints.get(week);

            if (!stats.contains(gameStat)) {
                stats.add(gameStat);
            }
        }
    }

    // EFFECTS: Returns the Total FantasyPoints earned in the given week
    public double getWeeksFantasyPoints(int week) {
        int fantasyPoints = 0;
        if (1 <= week && week <= FANTASY_WEEKS) {
            List<GameStat> weeksGameStats = weeklyFantasyPoints.get(week);
            for (GameStat g : weeksGameStats) {
                fantasyPoints += g.getFantasyPoints();
            }
        }
        return fantasyPoints;
    }

    // EFFECTS: Returns the total FantasyPoints earned to date
    public double getOverallFantasyPoints() {
        int fantasyPoints = 0;
        for (int i = 1; i <= FANTASY_WEEKS; i++) {
            getWeeksFantasyPoints(i);
        }
        return fantasyPoints;
    }

    public void calculateFantasyPoints(GameStat gameStat) {
        GameStat.StatType stat = gameStat.getStatType();
        double fantasyPoints = 0;
        double amount = gameStat.getAmount();

        if (stat.equals(GameStat.StatType.goals)) {
            fantasyPoints += amount * POINTS_PER_GOAL;
        } else if (stat.equals(GameStat.StatType.assists)) {
            fantasyPoints += amount * POINTS_PER_ASSIST;
        } else if (stat.equals(GameStat.StatType.saves)) {
            fantasyPoints += amount * POINTS_PER_SAVE;
        } else if (stat.equals(GameStat.StatType.wins)) {
            fantasyPoints += amount * POINTS_PER_WIN;
        }
        gameStat.setFantasyPoints(fantasyPoints);
    }

    public static List<LocalDate> initializeWeekCutoffs() {
        try (Reader reader = new FileReader(savePath + "/availablePlayers.json")) {
            JsonReader jsonReader = new JsonReader(reader);
            Gson gson = new Gson();

            Type setType = new TypeToken<List<LocalDate>>() {
            }.getType();

            System.out.println("Initializing fantasy weeks...");

            List<LocalDate> fantasyWeekCutoffs = gson.fromJson(jsonReader, setType);

            System.out.println("Initialization complete!");

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

}
