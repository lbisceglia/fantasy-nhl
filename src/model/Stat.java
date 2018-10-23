package model;

import model.Player.Player;

import java.io.Serializable;

public abstract class Stat implements Serializable {
    //    protected LocalDate timestamp;
    protected int weekNumber;
    protected Player player;
    protected String eventType;

    public Stat(int weekNumber, Player player, String eventType) {
        //        this.timestamp = timestamp;
        this.weekNumber = weekNumber;
        this.player = player;
        this.eventType = eventType;
    }

    // getters

    // EFFECTS: Returns the Date and Time of the stat
//    public LocalDate getTimestamp() {
//        return timestamp;
//    }

    // EFFECTS: Returns the fantasy week when the event occurred;
    public int getWeekNumber() {
        return weekNumber;
    }

    // EFFECTS: Returns the Player performing the stat
    public Player getPlayer() {
        return player;
    }

    // EFFECTS: Returns what type of stat it is (e.g. goal, assist, hit)
    public String getEventType() {
        return eventType;
    }
}
