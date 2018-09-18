package model;

public class NhlPlayer {
    // Fantasy Points conversion rate for goals and assists
    private static final int GOAL_FP = 10;
    private static final int ASSIST_FP = 5;

    String name;
    String nhlTeam;
    String position;
    int totalGoals;
    int totalAssists;
    int totalFantasyPoints;
    int thisWeekGoals;
    int thisWeekAssists;
    int thisWeekFantasyPoints;

    public NhlPlayer(String name, String nhlTeam, String position, int totalGoals, int totalAssists){
        this.name = name;
        this.nhlTeam = nhlTeam;
        this.position = position;
        this.totalGoals = totalGoals;
        this.totalAssists = totalAssists;
        thisWeekGoals = 0;
        thisWeekAssists = 0;
    }

    private int calculatePoints() {
        return GOAL_FP * this.totalGoals + ASSIST_FP * this.totalAssists;
    }
}