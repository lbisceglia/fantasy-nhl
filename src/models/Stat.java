package models;

import java.io.Serializable;
import java.util.Objects;

import static managers.FantasyManager.calculateFantasyPoints;

public class Stat implements Serializable {
    private final int gameID;
    private final Player player;
    private final StatType statType;
    private final double amount;
    private double fantasyPoints;
    private int fantasyWeek;

    public enum StatType {
        goals, assists, saves, wins,
    }

    // MODIFIES, this, player, player's observers
    // EFFECTS: Creates a stat with the given date, ID, player, stat type, and amount
    //          Calculates the points and fantasy week in which it was earned
    //          Adds itself to the player's stats
    public Stat(int gameID, int fantasyWeek, Player player, StatType statType, double amount) {
        this.gameID = gameID;
        this.player = player;
        this.statType = statType;
        this.amount = amount;
        this.fantasyPoints = 0;
        this.fantasyWeek = fantasyWeek;
        calculateFantasyPoints(this);
        this.player.addStat(this);
    }


    // EFFECTS: Returns the NHL.com gameID primary key during which the stat took place
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


    // EFFECTS: Returns the fantasy points the stat earned
    public double getFantasyPoints() {
        return fantasyPoints;
    }


    // EFFECTS: Returns the fantasy points earned for the stat
    public int getFantasyWeek() {
        return fantasyWeek;
    }

    // MODIFIES: this
    // EFFECTS: Sets the fantasy points earned for the stat
    public void setFantasyPoints(double points) {
        this.fantasyPoints = points;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Stat stat = (Stat) o;
        return gameID == stat.gameID &&
                Objects.equals(player, stat.player) &&
                statType == stat.statType;
    }

    @Override
    public int hashCode() {

        return Objects.hash(gameID, player, statType);
    }
}
