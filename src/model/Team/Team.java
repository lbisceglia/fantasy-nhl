package model.Team;

import model.FantasyWeekManager;
import model.League.League;
import model.Observers.Observer;
import model.Player.Player;
import model.Stat.GameStat;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;

import static java.lang.StrictMath.round;

public class Team implements Serializable, Observer {
    private String teamName;
    private int fantasyPoints;
    private ArrayList<Player> players;
    //    private PlayerManager playerManager;
    private FantasyWeekManager fantasyWeekManager;
    private final League league;

    // EFFECTS: Constructs an empty team with the given name and base statManager
    public Team(String teamName) {
        this.teamName = teamName;
        fantasyPoints = 0;
        players = new ArrayList<>();
//        playerManager = new PlayerManager(this);
        fantasyWeekManager = new FantasyWeekManager();
        league = null;
    }

    // EFFECTS: Returns the team's name
    public String getTeamName() {
        return teamName;
    }

    // EFFECTS: Returns the team's fantasy points
    public int getFantasyPoints() {
        return fantasyPoints;
    }

    // EFFECTS: Return the list of playerManager on the team
    public ArrayList<Player> getPlayers() {
        return players;
    }

//    // EFFECTS: returns the leage the team is in
//    public League getLeague() {
//        return league;
//    }

    // EFFECTS: Sets the team's name to the given non-empty string
    //          Does nothing if the string is empty
    public void setTeamName(String teamName) {
        if (!teamName.equals("")) {
            this.teamName = teamName;
        }
    }

    // EFFECTS: adds a player to the team and subcribes to that player for updates
    public void addPlayer(Player player) {
        if (!players.contains(player)) {
            players.add(player);
            player.addObserver(this);
        }
    }

    // EFFECTS: adds the player from playerManager and removes this team from the player's team
    public void removePlayer(Player player) {
        players.remove(player);
        player.removeObserver(this);
    }

    public void addStat(GameStat gameStat) {
        fantasyWeekManager.addStat(gameStat);
    }

    @Override
    // EFFECTS: Calculates the stat's fantasy points and adds it to the team's fantasy week
    public void update(GameStat gameStat) {
        fantasyWeekManager.calculateFantasyPoints(gameStat);
        addStat(gameStat);

        double points = gameStat.getFantasyPoints();
        System.out.println("Update: " + teamName + " has earned another " + round(points) + " fantasy points!");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Team team = (Team) o;
        return Objects.equals(teamName, team.teamName);
    }

    @Override
    public int hashCode() {

        return Objects.hash(teamName);
    }
}
