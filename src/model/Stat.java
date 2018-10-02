package model;

import model.Player.Player;

import java.io.Serializable;
import java.time.LocalDate;

public class Stat implements Serializable{
    private LocalDate timestamp;
    private Player player;
    private String eventType;

    public Stat(LocalDate timestamp, Player player, String eventType){
        this.timestamp = timestamp;
        this.player = player;
        this.eventType = eventType;
    }

    // getters

    // EFFECTS: Returns the Date and Time of the stat
    public LocalDate getTimestamp() {
        return timestamp;
    }

    // EFFECTS: Returns the Player performing the stat
    public Player getPlayer() {
        return this.player;
    }

    // EFFECTS: Returns what type of stat it is (e.g. goal, assist, hit)
    public String getEventType() {
        return this.eventType;
    }
}
