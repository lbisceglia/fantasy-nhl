package model.Stat;

import java.util.HashMap;
import java.util.Map;

import static model.Stat.GameStat.StatType.*;

public class FantasyPointAllocator {
    private final static double POINTS_PER_GOAL = 6;
    private final static double POINTS_PER_ASSIST = 4;
    private static final double POINTS_PER_SAVE = 0.6;
    private static final double POINTS_PER_WIN = 5;

    private Map<GameStat.StatType, Double> pointsPerStat;

    public FantasyPointAllocator() {
        pointsPerStat = new HashMap<>();
        pointsPerStat.put(goals, POINTS_PER_GOAL);
        pointsPerStat.put(assists, POINTS_PER_ASSIST);
        pointsPerStat.put(saves, POINTS_PER_SAVE);
        pointsPerStat.put(wins, POINTS_PER_WIN);
    }

    // EFFECTS: Returns the points-per-stat lookup table
    public Map<GameStat.StatType, Double> getPointsPerStat() {
        return pointsPerStat;
    }
}
