package models;

import abstractions.Subject;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Player extends Subject implements Serializable {
    private int playerID;
    private String name;
    private Position position;
    private Set<Stat> stats;


    // Center, Right Wing, Left Wing, Defence, Goalie
    public enum Position {
        C, RW, LW, D, G
    }

    // EFFECTS: Constructs a player with the given id, name, position, and base stats
    public Player(int playerID, String name, Position position) {
        this.playerID = playerID;
        this.name = name;
        this.position = position;
        stats = new HashSet<>();
    }

    // EFFECTS: Returns the player's NHL.com id number
    public int getPlayerID() {
        return playerID;
    }

    // EFFECTS: Returns the player's name
    public String getPlayerName() {
        return name;
    }

    // EFFECTS: Returns the player's position
    public Position getPosition() {
        return position;
    }

    // EFFECTS: Return the player's stats
    public Set<Stat> getStats() {
        return stats;
    }

    // MODIFIES: this, observers
    // EFFECTS: Adds a stat to the player's list
    //          Triggers a push update to notify observers
    public void addStat(Stat stat) {
        if(!stats.contains(stat)) {
            stats.add(stat);
            notifyObservers(stat);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return playerID == player.playerID &&
                Objects.equals(name, player.name);
    }

    @Override
    public int hashCode() {

        return Objects.hash(playerID, name);
    }
}
