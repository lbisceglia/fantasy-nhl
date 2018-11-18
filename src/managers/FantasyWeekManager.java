package managers;

import models.Stat;
import models.Team;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static managers.FantasyManager.FANTASY_WEEKS;

public class FantasyWeekManager implements Serializable {
    Map<Integer, List<Stat>> weeklyFantasyPoints;

    // EFFECTS: Creates a FantasyManager with the correct number of Fantasy Weeks
    public FantasyWeekManager() {
        weeklyFantasyPoints = new HashMap<>();
        for (int i = 1; i <= FANTASY_WEEKS; i++) {
            weeklyFantasyPoints.put(i, new ArrayList<>());
        }
    }

    // MODIFIES: this
    // EFFECTS: Adds a stat to the week in which it occurred
    public void addStat(Stat stat) {
        int week = stat.getFantasyWeek();

        if (1 <= week && week <= FANTASY_WEEKS) {
            List<Stat> stats = weeklyFantasyPoints.get(week);

            if (!stats.contains(stat)) {
                stats.add(stat);
            }
        }
    }

    // EFFECTS: Returns the Total FantasyPoints earned in the given week
    public double getWeeksFantasyPoints(int week) {
        int fantasyPoints = 0;
        if (1 <= week && week <= FANTASY_WEEKS) {
            List<Stat> weeksStats = weeklyFantasyPoints.get(week);
            for (Stat g : weeksStats) {
                fantasyPoints += g.getFantasyPoints();
            }
        }
        return fantasyPoints;
    }


    // EFFECTS: Sends user an update notification
    public void updateUser(Team team, Stat stat) {
        double points = stat.getFantasyPoints();
        System.out.println("Update: " + team.getTeamName() + " has earned another " + Math.round(points) + " fantasy points!");
    }

//    // EFFECTS: Returns the total FantasyPoints earned to date
//    public double getOverallFantasyPoints() {
//        int fantasyPoints = 0;
//        for (int i = 1; i <= FANTASY_WEEKS; i++) {
//            getWeeksFantasyPoints(i);
//        }
//        return fantasyPoints;
//    }

}
