package model.Player;

import model.Observers.Subject;
import model.Stat.GameStat;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;

public class Player extends Subject implements Serializable {
    private int playerID;
    private String name;
    private Position position;
//    private Observer team;
    private ArrayList<GameStat> stats;
//    private StatManager statManager;

    public enum Position {
        C, RW, LW, D, G
    }

    // EFFECTS: Constructs a player with the given name and position
    //          Player has no assigned team1 or stats
    public Player(int playerID, String name, Position position) {
        this.playerID = playerID;
        this.name = name;
        this.position = position;
//        team = null;
        stats = new ArrayList<>();
////        statManager = new StatManager(this);
    }

    // EFFECTS: Return the player's id number
    public int getPlayerID() {
        return playerID;
    }

    // EFFECTS: Return the player's name
    public String getPlayerName() {
        return name;
    }

    // EFFECTS: Return the player's position
    public Position getPosition() {
        return position;
    }

        // EFFECTS: Return the player's stats
    public ArrayList<GameStat> getStats() {
        return stats;
    }


//    // EFFECTS: Sets the player's team to the given team
//    //          Removes them from their old team if necessary
//    public void setTeam(Team team) {
//        if (this.team == null) {
//            this.team = team;
//            this.team.addPlayer(this);
//        } else if (!this.team.equals(team)) {
//            Team previousTeam = this.team;
//            previousTeam.getPlayers().remove(this);
//            this.team = team;
//            if (!(team == null)) {
//                this.team.addPlayer(this);
//            }
//        }
//    }


    // Adds a Stat to the player's list
    // Triggers an update to notify the player's observers of the stat
    public void addStat(GameStat gameStat) {
        if(!stats.contains(gameStat)) {
            stats.add(gameStat);
            notifyObservers(gameStat);
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
