package model.Stat;

import model.Player.Player;

import java.io.Serializable;
import java.time.LocalDate;

public class GameStat implements Serializable {
    private final LocalDate date;
    private final int gameID;
    private final Player player;
    private final StatType statType;
    private final double amount;
    private double fantasyPoints;
    private int fantasyWeek;

    public enum StatType {
        goals, assists, saves, savePercentage, plusMinus, penaltyMinutes, powerplayPoints, shotsOnGoal, wins, goalsAgainst, shutouts
    }

    public GameStat(LocalDate date, int gameID, Player player, StatType statType, double amount) {
        this.date = date;
        this.gameID = gameID;
        this.player = player;
        this.statType = statType;
        this.amount = amount;
        this.fantasyPoints = 0;
        this.fantasyWeek = 0;

        this.player.addStat(this);
    }

    // EFFECTS: Returns the Date and Time of the stat
    public LocalDate getDate() {
        return date;
    }

    // EFFECTS: Returns the NHL.com gameID of when the stat occurred
    public int getGameID() {
        return gameID;
    }

    // EFFECTS: Returns the player performing the stat
    public Player getPlayer() {
        return player;
    }

    // EFFECTS: Returns what type of stat it is (e.g. goal, assist, save)
    public StatType getStatType() {
        return statType;
    }

    // EFFECTS: Returns how many times the stat occurred in the game
    public double getAmount() {
        return amount;
    }

    // EFFECTS: Returns the fantasy points earned for the stat
    public double getFantasyPoints() {
        return fantasyPoints;
    }

    // EFFECTS: Returns the fantasy points earned for the stat
    public int getFantasyWeek() {
        return fantasyWeek;
    }

    // EFFECTS: Sets the fantasy points earned for the stat
    public void setFantasyPoints(double points) {
        this.fantasyPoints = points;
    }
}
